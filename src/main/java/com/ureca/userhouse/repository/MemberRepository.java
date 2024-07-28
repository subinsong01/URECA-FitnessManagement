package com.ureca.userhouse.repository;

import com.ureca.userhouse.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
