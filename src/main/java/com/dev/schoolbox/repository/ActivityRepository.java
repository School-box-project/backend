package com.dev.schoolbox.repository;

import com.dev.schoolbox.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel, UUID> {
}
