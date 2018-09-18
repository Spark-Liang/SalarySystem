package com.lzh.salarysystem.repository;

import com.lzh.salarysystem.entity.TimeCard;
import com.lzh.salarysystem.entity.TimeCard.TimeCardKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCardRepository extends JpaRepository<TimeCard, TimeCardKey>{

}
