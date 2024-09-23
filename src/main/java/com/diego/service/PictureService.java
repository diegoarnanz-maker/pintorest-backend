package com.diego.service;

import java.util.List;

import com.diego.model.Picture;
import com.diego.model.User;

public interface PictureService {

    public Picture createPicture( Picture picture, User user );
    public Picture findPictureById( Long id ) throws Exception;
    public void deletePicture( Long id ) throws Exception;
    public Picture updatePicture (Picture picture, Long id) throws Exception;
    public List<Picture> findAllPictures();
    public Picture likePicture( Long PictureId, User user ) throws Exception;

}
  