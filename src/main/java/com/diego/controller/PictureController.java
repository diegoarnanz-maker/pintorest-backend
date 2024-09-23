package com.diego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.model.Picture;
import com.diego.model.User;
import com.diego.service.PictureService;
import com.diego.service.UserService;

@RestController
@RequestMapping("/api")
public class PictureController {
    // @RequestMapping("api/pictures")

    @Autowired
    private PictureService pictureService;

    @Autowired
    private UserService userService;

    @PostMapping("/picture")
    public Picture createPicture(@RequestBody Picture picture, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);

        Picture createdPicture = pictureService.createPicture(picture, user);
        return createdPicture;
    }

    @GetMapping("/pictures")
    public List<Picture> getAllPicture() throws Exception {

        List<Picture> pictures = pictureService.findAllPictures();
        return pictures;
    }

    @DeleteMapping("/pictures/{pictureId}")
    public String deletePicture(@PathVariable Long pictureId) throws Exception {

        pictureService.deletePicture(pictureId);
        return "Picture deleted";
    }

    @PutMapping("/picture/{id}")
    public Picture updatePicture(@RequestBody Picture picture,
            @PathVariable Long id) throws Exception {

        Picture updatedPicture = pictureService.updatePicture(picture, id);
        return updatedPicture;
    }

    @PutMapping("/pictures/{id}/like")
    public Picture likePicture(@RequestHeader("Authorization") String jwt,
    @PathVariable Long id) throws Exception {

        User user = userService.findUserByJwt(jwt);

        Picture updatedPicture = pictureService.likePicture(id, user);
        return updatedPicture;
    }

}
