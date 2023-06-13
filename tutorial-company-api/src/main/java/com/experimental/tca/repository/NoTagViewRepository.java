package com.experimental.tca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.experimental.tca.model.NoTagView;

@Repository
public interface NoTagViewRepository extends JpaRepository<NoTagView, String>{

}
