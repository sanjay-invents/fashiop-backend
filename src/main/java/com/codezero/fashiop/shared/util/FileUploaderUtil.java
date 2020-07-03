package com.codezero.fashiop.shared.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.codezero.fashiop.shared.model.Constants.FILE_STORAGE_ABSOLUTE_PATH_BASE;
import static com.codezero.fashiop.shared.model.Constants.FILE_STORAGE_SERVER_PATH_BASE;

public class FileUploaderUtil {

    /**
     * This method is used to upload file to the defined path
     * @param multipartFile is the file sent from the view
     * @param fileName Filename that is to be saved as with current timestamp
     * @return the file name.
     */
    public static String uploadImage(MultipartFile multipartFile, String fileName, String uploadFolderName){
        String path = FILE_STORAGE_ABSOLUTE_PATH_BASE + "/" + uploadFolderName;

        File uploadDirectory = new File(path);
        if(!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
        }
        String name = fileName.toLowerCase() + System.currentTimeMillis() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        Path targetLocation = Paths.get(path).toAbsolutePath().normalize().resolve(name);
        try {
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FILE_STORAGE_SERVER_PATH_BASE + "/" + uploadFolderName + "/" + name;
    }

    public static boolean deleteImage(String uploadFolderName, String fileName) {
        String splittedFilename[] = fileName.split("/");
        String path = FILE_STORAGE_ABSOLUTE_PATH_BASE + "/" + uploadFolderName + "/" + splittedFilename[5];
        File fileToDelete = new File(path);
        return fileToDelete.delete();
    }

}
