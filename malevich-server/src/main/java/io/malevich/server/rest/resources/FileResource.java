package io.malevich.server.rest.resources;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.yinyang.core.server.domain.FileEntity;
import com.yinyang.core.server.domain.LobStorageEntity;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.rest.RestResource;
import com.yinyang.core.server.services.auth.AuthService;
import com.yinyang.core.server.services.file.FileService;
import com.yinyang.core.server.services.lobstorage.LobStorageService;
import com.yinyang.core.server.transfer.FileDto;
import io.malevich.server.aws.S3Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping(value = "/files")
public class FileResource extends RestResource<FileDto, FileEntity> {

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LobStorageService lobStorageService;

    @Autowired
    private S3Wrapper s3Wrapper;

    @Value("${use.amazon.aws}")
    private boolean useAWS;

    public FileResource() {
        super(FileDto.class, FileEntity.class);
    }

    private String getFileKey(FileEntity fileEntity){
        return fileEntity.getId() + "_" + fileEntity.getFileName();
    }

    private FileEntity uploadFileInternal(MultipartFile file, UserEntity userEntity) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setUser(userEntity);
        fileEntity = fileService.save(fileEntity);
        fileEntity.setUrl("files/downloadFile/" + fileEntity.getId());
        fileEntity = fileService.save(fileEntity);

        if (useAWS) {
            try {
                PutObjectResult uploadResult = s3Wrapper.upload(file.getInputStream(), getFileKey(fileEntity));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                byte[] bytes = file.getBytes();
                LobStorageEntity lobStorageEntity = new LobStorageEntity();
                lobStorageEntity.setContent(bytes);
                lobStorageEntity.setFile(fileEntity);
                lobStorageService.save(lobStorageEntity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileEntity;
    }


    @RequestMapping(value = "/uploadPrivateFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FileDto uploadPrivateFile(@RequestParam("file") MultipartFile file) {
        UserEntity userEntity = authService.getUserEntity();
        return convertToDto(uploadFileInternal(file, userEntity));
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FileDto uploadFile(@RequestParam("file") MultipartFile file) {
        return convertToDto(uploadFileInternal(file, null));
    }


    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<FileDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> convertToDto(uploadFileInternal(file, null)))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {

        FileEntity fileEntity = fileService.find(fileId);

        if(fileEntity.getUser() != null){
            UserEntity userEntity = authService.getUserEntity();

            if(!fileEntity.getUser().equals(userEntity))
                throw new AccessDeniedException("You do not have enough permissions to access this file");
        }

        if (useAWS) {
            try {

                ResponseEntity<byte[]> items = s3Wrapper.download(getFileKey(fileEntity));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header("content-disposition", "attachment; filename = \"" + fileEntity.getFileName() + "\"")
                        .body(items.getBody());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        LobStorageEntity resource = lobStorageService.findByFileId(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile().getFileName() + "\"")
                .body(resource.getContent());
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<FileDto> list() {
        List<FileEntity> allEntries = this.fileService.findAll();
        return allEntries.stream().map(allEntry -> convertToDto(allEntry)).collect(Collectors.toList());
    }

}
