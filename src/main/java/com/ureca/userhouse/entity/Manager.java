package com.ureca.userhouse.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
	
	//cascade: 모든 작업 전파를 위함
	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Member> members;


}
