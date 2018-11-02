package io.malevich.server.rest.resources;

import io.malevich.server.core.dto.DTO;
import io.malevich.server.domain.FileEntity;
import io.malevich.server.domain.LobStorageEntity;
import io.malevich.server.services.file.FileService;
import io.malevich.server.services.lobstorage.LobStorageService;
import io.malevich.server.transfer.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/files")
public class FileResource {

    @Autowired
    private FileService fileService;

    @Autowired
    private LobStorageService lobStorageService;

//	@PostMapping("/uploadFile")
//	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//		String fileName = fileService.storeFile(file);
//
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//				.path("/downloadFile/")
//				.path(fileName)
//				.toUriString();
//
//		return new UploadFileResponse(fileName, fileDownloadUri,
//				file.getContentType(), file.getSize());
//	}
//
//	@PostMapping("/uploadFiles")
//	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		return Arrays.asList(files)
//				.stream()
//				.map(file -> uploadFile(file))
//				.collect(Collectors.toList());
//	}

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) throws SQLException {
        LobStorageEntity resource = lobStorageService.findByFileId(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile().getFileName() + "\"")
                .body(resource.getContent().getBytes(1, ((int) resource.getContent().length())));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @DTO(FileDto.class)
    public List<FileEntity> list() {
        List<FileEntity> allEntries = this.fileService.findAll();
        return allEntries;
    }

}
