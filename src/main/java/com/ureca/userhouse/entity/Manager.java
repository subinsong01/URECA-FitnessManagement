package com.ureca.userhouse.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Manager {
	@Id
	private String mid;
	private String mname, mpwd, email;
	

    @OneToMany(mappedBy = "manager")
    private Set<Member> members;

}
