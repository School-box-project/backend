package com.dev.schoolbox.model;

import com.dev.schoolbox.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Teacher")
public class TeacherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDate date_of_birth;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false)
    private String disciplines;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressModel address;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantModel tenant;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private ClassModel current_class;
}
