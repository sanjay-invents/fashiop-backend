package com.codezero.fashiop.shared.util;

import com.codezero.fashiop.users.exception.UserException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploaderUtil {

    private static final String ROOT_PATH = "src/main/resources/uploads/";

    /**
     * This method is used to upload file to the defined path
     * @param uploadPath is used to define the path where the file is to be uploaded.
     * @param multipartFile is the file sent from the view
     * @param fileName Filename that is to be saved as with current timestamp
     * @return the file name.
     */
    public static String uploadImage(String uploadPath, MultipartFile multipartFile, String fileName){

        String name = fileName.toLowerCase() + System.currentTimeMillis() + "."
                + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (multipartFile.isEmpty()) {
            throw new UserException(HttpStatus.CONFLICT.value(), "Please select a file to upload");
        }
        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(ROOT_PATH + uploadPath + name);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

}
