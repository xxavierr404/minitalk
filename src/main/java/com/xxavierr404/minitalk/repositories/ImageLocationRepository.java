package com.xxavierr404.minitalk.repositories;

import com.xxavierr404.minitalk.model.ImageLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageLocationRepository extends JpaRepository<ImageLocation, Long> {
}
