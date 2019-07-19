package com.fdmgroup.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.Entities.AudioBook;

public interface CatalogueRepo extends JpaRepository<AudioBook, String> {

}
