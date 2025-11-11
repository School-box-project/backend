package com.dev.schoolbox.repository;

import com.dev.schoolbox.model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantModel, UUID> {
}
