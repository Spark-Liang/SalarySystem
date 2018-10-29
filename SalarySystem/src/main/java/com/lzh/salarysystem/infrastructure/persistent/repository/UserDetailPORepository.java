package com.lzh.salarysystem.infrastructure.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzh.salarysystem.infrastructure.persistent.po.entity.UserDetailPO;

public interface UserDetailPORepository extends JpaRepository<UserDetailPO, Long>{

	UserDetailPO findByUsername(String name);
}
