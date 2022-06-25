package com.example.video.service.file;

import com.example.video.configuration.FileStorageProperties;
import com.example.video.entities.UploadFile;
import com.example.video.entities.constant.UploadFileType;
import com.example.video.repository.FileRepository;
import com.example.video.service.BaseService;
import com.example.video.util.Util;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
class FileStorageServiceImpl extends BaseService implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private FileRepository imageRepository;


    @Autowired
    public FileStorageServiceImpl(final FileStorageProperties fileStorageProperties) throws Exception {
        try {
            fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public UploadFile storeImage(final MultipartFile file) throws Exception {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date());
        String randomString = Util.randomAlphaNumeric(5, "");
        String orginalName = timeStamp + "_" + randomString + "_" + StringUtils.cleanPath(file.getOriginalFilename().toLowerCase());
        if (orginalName.contains("..")) {
            throw new Exception("Sorry! Filename contains invalid path sequence " + orginalName);
        }
        String type = file.getContentType();
        if ((type == null || !type.toLowerCase().startsWith("image")) && !orginalName.endsWith("jpg") && !orginalName.endsWith("jpeg") && !orginalName.endsWith("png")) {
            throw new Exception("Lỗi định dạng file");
        }
        BufferedImage bimg = ImageIO.read(file.getInputStream());
        UploadFile image = new UploadFile();
        image.setType(UploadFileType.IMAGE);
        image.setWidth(bimg.getWidth());
        image.setHeight(bimg.getHeight());
        image.setSize(file.getSize());
        image.setOriginName(file.getOriginalFilename());
        Path orginalLocation = fileStorageLocation.resolve(orginalName);
        Files.copy(new ByteArrayInputStream(file.getBytes()), orginalLocation, StandardCopyOption.REPLACE_EXISTING);
        String url = getURLBase().concat("/api/images/");
        image.setOriginUrl(url.concat(orginalName));
        image = imageRepository.save(image);
        return image;
    }

    public UploadFile storeVideo(final MultipartFile thumbFile, final MultipartFile videoFile) throws Exception {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date());
        String orginalName = timeStamp + "_" + Util.randomAlphaNumeric(5, "") + "_" + StringUtils.cleanPath(videoFile.getOriginalFilename().toLowerCase());
        if (orginalName.contains("..")) {
            throw new Exception("Sorry! Filename contains invalid path sequence " + orginalName);
        }

        String videoType = videoFile.getContentType();
        if ((videoType == null || !videoType.toLowerCase().startsWith("video")) && !orginalName.endsWith("mov") && !orginalName.endsWith("mp4")) {
            throw new Exception("Lỗi định dạng file");
        }
        Path orginalLocation = fileStorageLocation.resolve(orginalName);
        Files.copy(new ByteArrayInputStream(videoFile.getBytes()), orginalLocation, StandardCopyOption.REPLACE_EXISTING);
        String url = getURLBase();
        url = url.concat("/api/video/");
//        BufferedImage bimg = ImageIO.read(thumbFile.getInputStream());
        UploadFile image = new UploadFile();
        image.setType(UploadFileType.VIDEO_NATIVE);
//        image.setWidth(bimg.getWidth());
//        image.setHeight(bimg.getHeight());
        image.setOriginUrl(url.concat(orginalName));
        image.setSize(videoFile.getSize());
        image.setOriginName(videoFile.getOriginalFilename());
        image = imageRepository.save(image);
        return image;
    }

    public Resource loadFileAsResource(final String fileName) throws Exception {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        }
        throw new Exception("File not found " + fileName);
    }

}