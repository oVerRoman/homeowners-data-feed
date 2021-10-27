package com.simbirsoftintensiv.intensiv.controller;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.service.FileStorageService;
import com.simbirsoftintensiv.intensiv.util.Message;
import com.simbirsoftintensiv.intensiv.util.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="rest/files")
public class FileUploadController {
    static final Logger log =
            LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("")
    public ResponseEntity<Message> upload (@RequestParam("file")MultipartFile file,
                                           @AuthenticationPrincipal AuthorizedUser user,
                                           HttpServletRequest request) {
        try {
            String fileName = fileStorageService.save(file);
            return ResponseEntity.ok(new Message(fileName));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Message("Could not upload file " + file.getOriginalFilename()));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UploadFile>> files(@AuthenticationPrincipal AuthorizedUser user) {
        List<UploadFile> files = fileStorageService.load()
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileUploadController.class,
                            "getFile",
                            path.getFileName().toString()
                    ).build().toString();
                    return new UploadFile(fileName, url);
                }).collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename")String fileName) {
        Resource file = fileStorageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/init")
    public ResponseEntity<Message> init (@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails.toString().equals("[ADMIN]")) {
                fileStorageService.clear();
                fileStorageService.init();
            }
            return ResponseEntity.ok(new Message("File storage is initailize"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new Message("Error initialize file storage"));
        }
    }
}
