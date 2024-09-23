package com.diego.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.model.Picture;
import com.diego.model.User;
import com.diego.repository.PictureRepository;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture createPicture(Picture picture, User user) {
        Picture createdPicture = new Picture();
        createdPicture.setTitle(picture.getTitle());
        createdPicture.setUser(user);
        createdPicture.setImage(picture.getImage());
        createdPicture.setDescription(picture.getDescription());
        createdPicture.setFree(picture.isFree());
        createdPicture.setCreatedAt(LocalDateTime.now());
        createdPicture.setLikes(picture.getLikes());
        return pictureRepository.save(createdPicture);
    }

    @Override
    public Picture findPictureById(Long id) throws Exception {
        Optional<Picture> opt = pictureRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new Exception("Picture not found" + id);
        }
    }

    @Override
    public void deletePicture(Long id) throws Exception {
        findPictureById(id);
        pictureRepository.deleteById(id);
    }

    @Override
    public Picture updatePicture(Picture picture, Long id) throws Exception {
        Picture oldPicture = findPictureById(id);
        if (picture.getTitle() != null) {
            oldPicture.setTitle(picture.getTitle());
        }
        if (picture.getImage() != null) {
            oldPicture.setImage(picture.getImage());
        }
        if (picture.getDescription() != null) {
            oldPicture.setDescription(picture.getDescription());
        }
        return pictureRepository.save(oldPicture);
    }

    @Override
    public List<Picture> findAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Picture likePicture(Long PictureId, User user) throws Exception {
        Picture picture = findPictureById(PictureId);

        if (picture.getLikes().contains(user.getId())) {
            picture.getLikes().remove(user.getId());
        } else {
            picture.getLikes().add(user.getId());
        }
        return pictureRepository.save(picture);
    }

}
