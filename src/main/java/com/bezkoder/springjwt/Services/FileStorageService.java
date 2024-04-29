package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.exeption.FileStorageException;
import com.bezkoder.springjwt.exeption.MyFileNotFoundException;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.prooperty.FileStorageProperties;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service

public class FileStorageService {


    @Autowired
    private UserRepository userRepository;

   private final Path fileStorageLocation;


    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public String storeImageUser(long id, MultipartFile file) {
        User j = userRepository.findById(id).get();

        String filor = StringUtils.cleanPath(file.getOriginalFilename());
        int position = filor.indexOf(".");
        String ext = filor.substring(position, filor.length());

        String fileName = j.getFirstname() + j.getId().toString() + ext;
        j.setImage(fileName);
        userRepository.save(j);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            //Path targetLocation = this.fileStorageLocation.getFileName(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath;
            Resource resource = null;

                filePath = this.fileStorageLocation.resolve(fileName).normalize();
                resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


}
