package com.dev.schoolbox.service;

import com.dev.schoolbox.model.ActivityModel;
import com.dev.schoolbox.repository.ActivityRepository;
import com.dev.schoolbox.model.ClassModel;
import com.dev.schoolbox.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ClassRepository classRepository;

    public ActivityModel create(ActivityModel activity, UUID classId) {
        Optional<ClassModel> classModel = classRepository.findById(classId);

        if (classModel.isEmpty()) {
            throw new RuntimeException("Class não encontrada com ID: " + classId);
        }

        activity.setClassModel(classModel.get());
        return activityRepository.save(activity);
    }

    public List<ActivityModel> findAll() {
        return activityRepository.findAll();
    }

    public Optional<ActivityModel> findById(UUID id) {
        return activityRepository.findById(id);
    }

    public ActivityModel update(UUID id, ActivityModel newActivity) {
        return activityRepository.findById(id).map(activity -> {
            activity.setName(newActivity.getName());
            activity.setDescription(newActivity.getDescription());
            activity.setWeight(newActivity.getWeight());
            activity.setStart_date(newActivity.getStart_date());
            activity.setEnd_date(newActivity.getEnd_date());
            activity.setClassModel(newActivity.getClassModel());
            return activityRepository.save(activity);
        }).orElseThrow(() -> new RuntimeException("Activity não encontrada com ID: " + id));
    }

    public void delete(UUID id) {
        if (!activityRepository.existsById(id)) {
            throw new RuntimeException("Activity não encontrada com ID: " + id);
        }
        activityRepository.deleteById(id);
    }
}
