package com.ureca.userhouse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "my_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String member_name;
    private int member_age;
    private String member_tel;
    private double weight;
    private double height;
    private String reason;

    @ManyToOne
    @JsonBackReference //순환 참조 문제를 방지하기 위해 삽입 
    @JoinColumn(name = "manager_mid") 
    private Manager manager;
}
