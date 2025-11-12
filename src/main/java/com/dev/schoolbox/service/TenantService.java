package com.dev.schoolbox.service;

import com.dev.schoolbox.model.AddressModel;
import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantModel> listarTenants() {
        return tenantRepository.findAll();
    }

    public Optional<TenantModel> buscarPorId(UUID id) {
        return tenantRepository.findById(id);
    }

    @Transactional
    public TenantModel criarTenant(TenantModel tenant) {
        tenant.setActive(true);
        return tenantRepository.save(tenant);
    }

    @Transactional
    public Optional<TenantModel> atualizarTenant(UUID id, TenantModel tenantAtualizado) {
        return tenantRepository.findById(id).map(tenant -> {
            tenant.setName(tenantAtualizado.getName());
            tenant.setLegalName(tenantAtualizado.getLegalName());
            tenant.setCnpj(tenantAtualizado.getCnpj());
            tenant.setDocumentNumber(tenantAtualizado.getDocumentNumber());
            tenant.setEmailContact(tenantAtualizado.getEmailContact());
            tenant.setPhoneContact(tenantAtualizado.getPhoneContact());
            tenant.setWebsiteUrl(tenantAtualizado.getWebsiteUrl());
            tenant.setTimezone(tenantAtualizado.getTimezone());
            tenant.setLocale(tenantAtualizado.getLocale());
            tenant.setCurrency(tenantAtualizado.getCurrency());
            tenant.setActive(tenantAtualizado.isActive());
            tenant.setSchoolType(tenantAtualizado.getSchoolType());
            tenant.setThemeColorPrimary(tenantAtualizado.getThemeColorPrimary());
            tenant.setThemeColorSecondary(tenantAtualizado.getThemeColorSecondary());
            tenant.setLogoUrl(tenantAtualizado.getLogoUrl());
            tenant.setFaviconUrl(tenantAtualizado.getFaviconUrl());
            tenant.setPlanType(tenantAtualizado.getPlanType());
            tenant.setMaxUsers(tenantAtualizado.getMaxUsers());
            tenant.setMaxStorageMb(tenantAtualizado.getMaxStorageMb());
            tenant.setPlanStartDate(tenantAtualizado.getPlanStartDate());
            tenant.setPlanEndDate(tenantAtualizado.getPlanEndDate());
            tenant.setSubdomain(tenantAtualizado.getSubdomain());
            tenant.setExternalApiKey(tenantAtualizado.getExternalApiKey());
            tenant.setExternalLmsId(tenantAtualizado.getExternalLmsId());

            // 🔹 Atualiza ou cria o endereço
            AddressModel novoEndereco = tenantAtualizado.getAddress();
            if (novoEndereco != null) {
                tenant.setAddress(novoEndereco);
            }

            return tenantRepository.save(tenant);
        });
    }

    @Transactional
    public boolean deletarTenant(UUID id) {
        return tenantRepository.findById(id).map(tenant -> {
            tenantRepository.delete(tenant);
            return true;
        }).orElse(false);
    }

    @Transactional
    public Optional<TenantModel> alterarStatus(UUID id, boolean ativo) {
        return tenantRepository.findById(id).map(tenant -> {
            tenant.setActive(ativo);
            return tenantRepository.save(tenant);
        });
    }
}
