package com.example.video.service.file;


import com.example.video.entities.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
;

public interface FileStorageService {

    UploadFile storeImage(final MultipartFile file) throws Exception;
    UploadFile storeVideo(final MultipartFile thumbFile, final MultipartFile videoFile) throws Exception;
    Resource loadFileAsResource(final String fileName)throws Exception;
}
