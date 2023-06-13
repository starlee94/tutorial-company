package com.experimental.tca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.experimental.tca.model.EmpAcc;

@Repository
public interface EmpAccRepository extends JpaRepository<EmpAcc, Integer> {
	List<EmpAcc> findByUsername(String username);
}