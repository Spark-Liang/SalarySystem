package com.lzh.salarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzh.salarysystem.domain.entity.TimeCard;
import com.lzh.salarysystem.domain.entity.TimeCard.TimeCardKey;

public interface TimeCardRepository extends JpaRepository<TimeCard, TimeCardKey>{

}
