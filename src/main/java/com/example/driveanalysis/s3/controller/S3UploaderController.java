package com.example.driveanalysis.s3.controller;

import com.example.driveanalysis.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class S3UploaderController {
    private final S3UploaderService s3UploaderService;

    @GetMapping("/image")
    public String image() {
        return "s3/image-upload";
    }

    @GetMapping("/video")
    public String video() {
        return "s3/video-upload";
    }


    @PostMapping("/image-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String imageUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "hongseungpyomedia", "image");
    }

    @PostMapping("/video-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String videoUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3UploaderService.upload(multipartFile, "hongseungpyomedia", "video");
    }
}