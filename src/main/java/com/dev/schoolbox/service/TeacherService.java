package com.dev.schoolbox.service;

import com.dev.schoolbox.model.ClassModel;
import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.model.TeacherModel;
import com.dev.schoolbox.repository.ClassRepository;
import com.dev.schoolbox.repository.TeacherRepository;
import com.dev.schoolbox.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<TeacherModel> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<TeacherModel> getTeacherById(UUID id) {
        return teacherRepository.findById(id);
    }

    public TeacherModel createTeacher(TeacherModel teacher) {
        // Verifica se o tenant existe
        if (teacher.getTenant() != null && teacher.getTenant().getId() != null) {
            TenantModel tenant = tenantRepository.findById(teacher.getTenant().getId())
                    .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
            teacher.setTenant(tenant);
        } else {
            throw new RuntimeException("Tenant é obrigatório");
        }

        // Verifica se a classe existe
        if (teacher.getCurrent_class() != null && teacher.getCurrent_class().getId() != null) {
            ClassModel classModel = classRepository.findById(teacher.getCurrent_class().getId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            teacher.setCurrent_class(classModel);
        } else {
            throw new RuntimeException("Turma é obrigatória");
        }

        // Salva o estudante
        return teacherRepository.save(teacher);
    }

    public TeacherModel updateTeacher(UUID id, TeacherModel updatedTeacher) {
        return teacherRepository.findById(id).map(teacher -> {
            teacher.setName(updatedTeacher.getName());
            teacher.setEmail(updatedTeacher.getEmail());
            teacher.setPassword(updatedTeacher.getPassword());
            teacher.setActive(updatedTeacher.getActive());
            teacher.setDate_of_birth(updatedTeacher.getDate_of_birth());
            teacher.setPhone(updatedTeacher.getPhone());
            teacher.setDisciplines(updatedTeacher.getDisciplines());
            teacher.setGender(updatedTeacher.getGender());
            teacher.setAddress(updatedTeacher.getAddress());

            // Atualiza Tenant existente
            if (updatedTeacher.getTenant() != null && updatedTeacher.getTenant().getId() != null) {
                TenantModel tenant = tenantRepository.findById(updatedTeacher.getTenant().getId())
                        .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
                teacher.setTenant(tenant);
            }

            // Atualiza Class existente
            if (updatedTeacher.getCurrent_class() != null && updatedTeacher.getCurrent_class().getId() != null) {
                ClassModel classModel = classRepository.findById(updatedTeacher.getCurrent_class().getId())
                        .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
                teacher.setCurrent_class(classModel);
            }

            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    public void deleteTeacher(UUID id) {
        teacherRepository.deleteById(id);
    }

}
