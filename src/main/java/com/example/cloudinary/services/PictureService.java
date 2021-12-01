package com.example.cloudinary.services;

import com.example.cloudinary.entities.Picture;
import com.example.cloudinary.entities.bindings.PictureAddBindingModel;
import com.example.cloudinary.repositories.PictureRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PictureService {

    private PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    public PictureService(PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public void addPicture(PictureAddBindingModel pictureAddBindingModel) throws IOException {
        CloudinaryImage cloudinaryImage = cloudinaryService.upload(pictureAddBindingModel.picture);
        Picture picture = new Picture();
        picture.setPublicId(cloudinaryImage.getPublicId());
        picture.setTitle(pictureAddBindingModel.title);
        picture.setUrl(cloudinaryImage.getUrl());
        pictureRepository.save(picture);
    }

    public List<Picture> getAll() {
        return pictureRepository.findAll();
    }

    public void deleteByPublicId(String publicId){
        pictureRepository.deleteByPublicId(publicId);
    }

    public void deleteInCloudinary(String publicId){
        cloudinaryService.destroy(publicId);
    }
}
