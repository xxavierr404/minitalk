package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.services.FileLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
class ImageController {
    private final FileLocationService fileLocationService;

    @PostMapping
    private Long uploadImage(@RequestParam MultipartFile image) throws Exception {
        return fileLocationService.save(image.getBytes());
    }

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    private FileSystemResource downloadImage(@PathVariable Long imageId) {
        return fileLocationService.find(imageId);
    }
}