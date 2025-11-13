package com.dev.schoolbox.service;

import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.model.UserModel;
import com.dev.schoolbox.repository.UserRepository;
import com.dev.schoolbox.repository.TenantRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public UserModel createUser(UserModel user) {
        // Verifica se o tenant existe
        if (user.getTenant() != null && user.getTenant().getId() != null) {
            TenantModel tenant = tenantRepository.findById(user.getTenant().getId())
                    .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
            user.setTenant(tenant);
        } else {
            throw new RuntimeException("Tenant é obrigatório");
        }

        // Salva o estudante
        return userRepository.save(user);
    }

    public UserModel updateUser(UUID id, UserModel updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setActive(updatedUser.getActive());
            user.setPhone(updatedUser.getPhone());
            user.setAddress(updatedUser.getAddress());

            // Atualiza Tenant existente
            if (updatedUser.getTenant() != null && updatedUser.getTenant().getId() != null) {
                TenantModel tenant = tenantRepository.findById(updatedUser.getTenant().getId())
                        .orElseThrow(() -> new RuntimeException("Tenant não encontrado"));
                user.setTenant(tenant);
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User não encontrado"));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
