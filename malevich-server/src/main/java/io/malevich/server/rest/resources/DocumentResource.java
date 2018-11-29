package io.malevich.server.rest.resources;

import io.malevich.server.domain.DocumentEntity;
import io.malevich.server.domain.DocumentTypeEntity;
import io.malevich.server.services.document.DocumentService;
import io.malevich.server.services.documenttype.DocumentTypeService;
import io.malevich.server.transfer.DocumentDto;
import io.malevich.server.transfer.DocumentTypeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/document")
public class DocumentResource {

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('TRADER','GALLERY')")
    @GetMapping("/typeList/{userType}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentTypeDto> typeList(@PathVariable("userType") String userType) {
        return this.documentTypeService.findAll(userType).stream().map(allData -> convertToDto(allData))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('GALLERY')")
    @GetMapping("/list/gallery")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentDto> listGallery() {
        return this.documentService.findGalleryDocs().stream().map(allData -> convertToDto(allData))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('TRADER')")
    @GetMapping("/list/trader")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentDto> listTrader() {
        return this.documentService.findTraderDocs().stream().map(allData -> convertToDto(allData))
                .collect(Collectors.toList());
    }

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    public DocumentDto upload(@RequestParam("file") MultipartFile file) {
        return convertToDto(uploadDocument(file));
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody DocumentDto documentDto) {
        DocumentEntity documentEntity = this.documentService.save(convertToEntity(documentDto));
        if (documentEntity != null)
            this.documentService.userDocs(documentEntity);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('TRADER','GALLERY')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.documentService.delete(id);
        return ResponseEntity.ok().build();
    }

    private DocumentEntity uploadDocument(MultipartFile file) {
        DocumentEntity documentEntity = new DocumentEntity();
        try {
            byte[] bytes = file.getBytes();
            documentEntity.setFileName(file.getOriginalFilename());
            documentEntity.setContent(bytes);
            documentEntity.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return documentEntity;
    }

    private DocumentTypeDto convertToDto(DocumentTypeEntity entity) {
        return modelMapper.map(entity, DocumentTypeDto.class);
    }

    private DocumentDto convertToDto(DocumentEntity entity) {
        return modelMapper.map(entity, DocumentDto.class);
    }

    private DocumentEntity convertToEntity(DocumentDto dto) {
        return modelMapper.map(dto, DocumentEntity.class);
    }
}
