package com.dev.schoolbox.controller;

import com.dev.schoolbox.model.ClassModel;
import com.dev.schoolbox.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassModel>> getAll() {
        return ResponseEntity.ok(classService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassModel> getById(@PathVariable UUID id) {
        Optional<ClassModel> classModel = classService.findById(id);
        return classModel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassModel> create(@RequestBody ClassModel classModel) {
        ClassModel saved = classService.save(classModel);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassModel> update(@PathVariable UUID id, @RequestBody ClassModel updatedClass) {
        Optional<ClassModel> updated = classService.update(id, updatedClass);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boolean deleted = classService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
