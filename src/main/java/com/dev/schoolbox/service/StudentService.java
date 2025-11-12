package com.dev.schoolbox.service;

import com.dev.schoolbox.model.StudentModel;
import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.model.ClassModel;
import com.dev.schoolbox.repository.StudentRepository;
import com.dev.schoolbox.repository.TenantRepository;
import com.dev.schoolbox.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<StudentModel> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<StudentModel> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public StudentModel createStudent(StudentModel student) {
        // Verifica se o tenant existe
        if (student.getTenant() != null && student.getTenant().getId() != null) {
            TenantModel tenant = tenantRepository.findById(student.getTenant().getId())
                    .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
            student.setTenant(tenant);
        } else {
            throw new RuntimeException("Tenant é obrigatório");
        }

        // Verifica se a classe existe
        if (student.getCurrent_class() != null && student.getCurrent_class().getId() != null) {
            ClassModel classModel = classRepository.findById(student.getCurrent_class().getId())
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            student.setCurrent_class(classModel);
        } else {
            throw new RuntimeException("Turma é obrigatória");
        }

        // Salva o estudante
        return studentRepository.save(student);
    }

    public StudentModel updateStudent(UUID id, StudentModel updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setPassword(updatedStudent.getPassword());
            student.setActive(updatedStudent.getActive());
            student.setDate_of_birth(updatedStudent.getDate_of_birth());
            student.setPhone(updatedStudent.getPhone());
            student.setGender(updatedStudent.getGender());
            student.setAddress(updatedStudent.getAddress());

            // Atualiza Tenant existente
            if (updatedStudent.getTenant() != null && updatedStudent.getTenant().getId() != null) {
                TenantModel tenant = tenantRepository.findById(updatedStudent.getTenant().getId())
                        .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
                student.setTenant(tenant);
            }

            // Atualiza Class existente
            if (updatedStudent.getCurrent_class() != null && updatedStudent.getCurrent_class().getId() != null) {
                ClassModel classModel = classRepository.findById(updatedStudent.getCurrent_class().getId())
                        .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
                student.setCurrent_class(classModel);
            }

            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }
}
