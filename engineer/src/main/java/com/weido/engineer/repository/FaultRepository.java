package com.weido.engineer.repository;

import com.weido.engineer.pojo.Fault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaultRepository extends JpaRepository<Fault,Integer> {
}
