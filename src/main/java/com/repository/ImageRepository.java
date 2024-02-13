package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ProductImage;
@Repository
public interface ImageRepository extends JpaRepository<ProductImage,Long>{

}
