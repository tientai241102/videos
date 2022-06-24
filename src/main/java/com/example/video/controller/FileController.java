package com.example.video.controller;


import com.example.video.entities.UploadFile;
import com.example.video.entities.response.BaseResponse;
import com.example.video.service.file.FileStorageService;
import com.example.video.util.MultipartFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/api/")
@Transactional
public class FileController extends BaseController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("v1/file/upload-image")
    public ResponseEntity<?> uploadImage1(@RequestParam("file") final MultipartFile file) {
        try {
            if (file == null) {
                throw new Exception("required_fields");
            }
            if (file.getSize() > 1024 * 1024 * 20) {
                throw new Exception("Dung lượng file quá lớn, vui lòng chọn file nhỏ hơn 20MB");
            }
            UploadFile uploadFile = fileStorageService.storeImage(file);
            return ResponseEntity.ok(new BaseResponse(uploadFile));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }



    @PostMapping("v1/file/upload-video")
    public ResponseEntity<?> uploadVideo1(@RequestParam(value = "thumbFile",required = false) final MultipartFile thumbFile,
                                          @RequestParam("videoFile") final MultipartFile videoFile) {
        try {
            if ( videoFile == null) {
                throw new Exception("required_fields");
            }
//            if(videoFile.getSize() > 1024*1024*20 || thumbFile.getSize() > 1024*1024*20) {
//                throw new Exception("Dung lượng file quá lớn, vui lòng chọn file nhỏ hơn 20MB");
//            }
            UploadFile uploadFile = fileStorageService.storeVideo(thumbFile, videoFile);
            return ResponseEntity.ok(new BaseResponse(uploadFile));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }

    @GetMapping("images/{fileName:.+}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable final String fileName) throws Exception {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }


    @GetMapping("video/{fileName:.+}")
    public void streamVideo(HttpServletResponse response, HttpServletRequest request, @PathVariable final String fileName) throws Exception {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String path = resource.getFile().getPath();
        try {
            MultipartFileSender.fromFile(new File(path))
                    .with(request)
                    .with(response)
                    .serveResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

