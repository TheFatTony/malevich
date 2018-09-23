package io.malevich.server.rest.resources;

import io.malevich.server.entity.FileEntity;
import io.malevich.server.entity.LobStorageEntity;
import io.malevich.server.services.file.FileService;
import io.malevich.server.services.lobstorage.LobStorageService;
import io.malevich.server.transfer.FileDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/files")
public class FileResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    @Autowired
    private LobStorageService lobStorageService;

    @Autowired
    private ModelMapper modelMapper;

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


    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<FileDto> list() {
        this.logger.info("list()");
        List<FileEntity> allEntries = this.fileService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

    private FileDto convertToDto(FileEntity files) {
        FileDto filesDto = modelMapper.map(files, FileDto.class);
        return filesDto;
    }

    private FileEntity convertToEntity(FileDto filesDto) {
        FileEntity files = modelMapper.map(filesDto, FileEntity.class);
        return files;
    }

}
