package com.experimental.tca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.experimental.tca.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	@Query("select t from Token t inner join EmpAcc e on t.empAcc.id = e.id where e.id = :userId and (t.revoked = false)")
	List<Token> findAllValidTokenByUser(Integer userId);
	
	Optional<Token> findByToken(String token);
}
