package com.example.video.repository;

import com.example.video.entities.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<UploadFile, Integer> {
    UploadFile findUploadFileByOriginUrl(String url);
}
