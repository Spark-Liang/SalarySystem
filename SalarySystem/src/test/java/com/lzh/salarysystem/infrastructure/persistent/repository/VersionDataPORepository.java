package com.lzh.salarysystem.infrastructure.persistent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lzh.salarysystem.infrastructure.persistent.po.VersionDataPO;

@Repository
public interface VersionDataPORepository extends JpaRepository<VersionDataPO, Long>{

}
