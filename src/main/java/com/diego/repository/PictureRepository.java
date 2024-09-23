package com.diego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
