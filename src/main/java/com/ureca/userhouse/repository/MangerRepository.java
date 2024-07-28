package com.ureca.userhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ureca.userhouse.entity.Manager;

public interface MangerRepository extends JpaRepository<Manager,String> {
	
}
