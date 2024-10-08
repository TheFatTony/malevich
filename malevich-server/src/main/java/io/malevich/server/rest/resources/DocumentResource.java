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

    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    @GetMapping("/typeList/{userType}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentTypeDto> typeList(@PathVariable("userType") String userType) {
        return this.documentTypeService.findByUserType(userType).stream().map(allData -> convertToDto(allData))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentDto> list() {
        return this.documentService.findDocs()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody DocumentDto documentDto) {
        this.documentService.save(convertToEntity(documentDto));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_TRADER','ROLE_GALLERY')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.documentService.delete(id);
        return ResponseEntity.ok().build();
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
