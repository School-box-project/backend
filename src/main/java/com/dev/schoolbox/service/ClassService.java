package com.dev.schoolbox.service;

import com.dev.schoolbox.model.ClassModel;
import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.repository.ClassRepository;
import com.dev.schoolbox.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public List<ClassModel> findAll() {
        return classRepository.findAll();
    }

    public Optional<ClassModel> findById(UUID id) {
        return classRepository.findById(id);
    }

    public ClassModel save(ClassModel classModel) {
        // ✅ Garante que o tenant existe e está gerenciado pela JPA
        if (classModel.getTenant() != null && classModel.getTenant().getId() != null) {
            TenantModel tenant = tenantRepository.findById(classModel.getTenant().getId())
                    .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
            classModel.setTenant(tenant);
        }

        return classRepository.save(classModel);
    }

    public Optional<ClassModel> update(UUID id, ClassModel updatedClass) {
        return classRepository.findById(id).map(existing -> {
            existing.setName(updatedClass.getName());
            existing.setTenant(updatedClass.getTenant());
            return classRepository.save(existing);
        });
    }

    public boolean delete(UUID id) {
        return classRepository.findById(id).map(classModel -> {
            classRepository.delete(classModel);
            return true;
        }).orElse(false);
    }
}
