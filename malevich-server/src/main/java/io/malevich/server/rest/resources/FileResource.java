package io.malevich.server.rest.resources;

import io.malevich.server.domain.FileEntity;
import io.malevich.server.domain.LobStorageEntity;
import io.malevich.server.services.file.FileService;
import io.malevich.server.services.lobstorage.LobStorageService;
import io.malevich.server.transfer.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/files")
public class FileResource {

    @Autowired
    private FileService fileService;

    @Autowired
    private LobStorageService lobStorageService;

    @Autowired
    private ModelMapper modelMapper;

    private FileEntity uploadFileInternal(MultipartFile file) {
        FileEntity fileEntity = new FileEntity();
        try {
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity = fileService.save(fileEntity);
            fileEntity.setUrl("files/downloadFile/" + fileEntity.getId());
            fileEntity = fileService.save(fileEntity);

            byte[] bytes = file.getBytes();
            LobStorageEntity lobStorageEntity = new LobStorageEntity();
            lobStorageEntity.setContent(bytes);
            lobStorageEntity.setFile(fileEntity);
            lobStorageService.save(lobStorageEntity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEntity;
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FileDto uploadFile(@RequestParam("file") MultipartFile file) {
        return convertToDto(uploadFileInternal(file));
    }


    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<FileDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> convertToDto(uploadFileInternal(file)))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        LobStorageEntity resource = lobStorageService.findByFileId(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile().getFileName() + "\"")
                .body(resource.getContent());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<FileDto> list() {
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
