package com.dev.schoolbox.controller;

import com.dev.schoolbox.model.ActivityModel;
import com.dev.schoolbox.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/{classId}")
    public ResponseEntity<ActivityModel> create(
            @PathVariable UUID classId,
            @RequestBody ActivityModel activity
    ) {
        return ResponseEntity.ok(activityService.create(activity, classId));
    }

    @GetMapping
    public ResponseEntity<List<ActivityModel>> getAll() {
        return ResponseEntity.ok(activityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityModel> getById(@PathVariable UUID id) {
        return activityService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityModel> update(
            @PathVariable UUID id,
            @RequestBody ActivityModel activity
    ) {
        return ResponseEntity.ok(activityService.update(id, activity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        activityService.delete(id);
        return ResponseEntity.ok("Activity deletada com sucesso!");
    }
}
