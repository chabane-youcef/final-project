package com.chabane.productserver.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {
    public static final String uploadingDir = System.getProperty("user.dir") + "/products/src/main/resources/static/images/";
//    public static final String uploadingDir = "/home/just/springProjects/supermarket/products/src/main/resources/static/images/";

    @PostMapping(value = "images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile uploadingFile) throws IOException {
        String imageName = (int)(Math.random() * 999999999) + uploadingFile.getOriginalFilename();
        File file = new File(uploadingDir + imageName);
        uploadingFile.transferTo(file);

        return imageName;
    }
}
