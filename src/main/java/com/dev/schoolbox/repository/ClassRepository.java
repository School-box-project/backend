package com.dev.schoolbox.repository;

import com.dev.schoolbox.model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<ClassModel, UUID>{
}
