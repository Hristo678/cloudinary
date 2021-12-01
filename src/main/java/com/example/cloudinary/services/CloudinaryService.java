package com.example.cloudinary.services;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        Map<String , String> upload = cloudinary.uploader().upload(tempFile, Map.of());
        String url = upload.getOrDefault(URL, "https://www.kenyons.com/wp-content/uploads/2017/04/default-image-800x600.jpg");
        String publicId = upload.getOrDefault(PUBLIC_ID, "");

        CloudinaryImage cloudinaryImage = new CloudinaryImage();
        cloudinaryImage.setUrl(url);
        cloudinaryImage.setPublicId(publicId);
        tempFile.delete();
        return cloudinaryImage;

    }

    public boolean destroy(String publicId){
        try {
            cloudinary.uploader().destroy(publicId, Map.of());
            return true;
        } catch (IOException e) {
           return false;
        }

    }
}
