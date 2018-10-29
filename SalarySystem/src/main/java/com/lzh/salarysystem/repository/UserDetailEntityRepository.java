package com.lzh.salarysystem.repository;

import org.springframework.stereotype.Repository;

import com.lzh.salarysystem.domain.entity.UserDetailEntity;
import com.lzh.salarysystem.domain.entity.UserDetailEntity.UserDetailEntityPOExtractor;

@Repository
public interface UserDetailEntityRepository {
	
	void save(UserDetailEntity entity, UserDetailEntityPOExtractor extractor);
	
	boolean isUsernameDuplicated(String username);
}
