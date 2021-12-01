package com.example.cloudinary.web;

import com.example.cloudinary.entities.bindings.PictureAddBindingModel;
import com.example.cloudinary.services.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;

@Controller
@RequestMapping("/pictures")
public class PictureController {

    private PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/all")
    public String allPictures(Model model){
        model.addAttribute("pictures", pictureService.getAll());
        return "all";
    }

    @GetMapping("/add")
    public String addPicture(){
        return "add";
    }

    @PostMapping("/add")
    public String addPicturePost(PictureAddBindingModel pictureAddBindingModel) throws IOException {

        pictureService.addPicture(pictureAddBindingModel);

        return "redirect:/pictures/all";
    }
    @Transactional
    @DeleteMapping("/delete")
    public String deletePicture(@RequestParam("public_id") String publicId){
        pictureService.deleteByPublicId(publicId);
        pictureService.deleteInCloudinary(publicId);
        return "redirect:/pictures/all";
    }

}
