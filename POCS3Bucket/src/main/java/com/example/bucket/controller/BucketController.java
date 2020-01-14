package com.example.bucket.controller;

import com.example.bucket.service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AWSS3Service s3client;

    @Autowired
    BucketController(AWSS3Service amazonClient) {
        this.s3client = amazonClient;
    }

   /* @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.s3client.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.s3client.deleteFileFromS3Bucket(fileUrl);
    }*/
}
