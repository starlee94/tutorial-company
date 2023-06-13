package com.experimental.tca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.experimental.tca.model.RmAccess;

@Repository
public interface RmAccessRepository extends JpaRepository<RmAccess, Integer>{
	
	@Query(value = "SELECT entry_additions(?1, ?2)", nativeQuery = true)
	int addAccessRoomCount(int tagId, int roomId);
}
