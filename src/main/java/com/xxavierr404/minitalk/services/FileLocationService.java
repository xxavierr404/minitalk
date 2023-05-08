package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.model.ImageLocation;
import com.xxavierr404.minitalk.repositories.FileSystemRepository;
import com.xxavierr404.minitalk.repositories.ImageLocationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;

@Service
@RequiredArgsConstructor
public
class FileLocationService {
    private final FileSystemRepository fileSystemRepository;
    private final ImageLocationRepository imageDbRepository;

    public Long save(byte[] bytes) throws Exception {
        String location = fileSystemRepository.save(bytes);
        var imageLoc = new ImageLocation();
        imageLoc.setLink(location);
        return imageDbRepository
                .save(imageLoc)
                .getId();
    }

    public FileSystemResource find(Long imageId) {
        var image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLink());
    }
}