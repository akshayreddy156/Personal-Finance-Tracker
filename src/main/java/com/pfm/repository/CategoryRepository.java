package com.pfm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfm.model.Category;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<Category>  findByCategoryIdAndUser_UserId(long categoryId,  Long userId);
    List<Category> findByUser_UserId(Long userId);


}
