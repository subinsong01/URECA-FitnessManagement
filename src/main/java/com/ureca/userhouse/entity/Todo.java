package com.ureca.userhouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="todo")
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	
	private String content;
	private boolean completed;

	@ManyToOne
    @JsonBackReference //순환 참조 문제를 방지하기 위해 삽입 
    @JoinColumn(name = "manager_mid") 
    private Manager manager;
}
