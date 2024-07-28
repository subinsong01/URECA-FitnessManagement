package com.ureca.userhouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String title;
    private String eventDescription;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    @ManyToOne
    @JoinColumn(name = "manager_mid")
    private Manager manager;

}
