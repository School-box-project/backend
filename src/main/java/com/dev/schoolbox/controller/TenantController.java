package com.dev.schoolbox.controller;

import com.dev.schoolbox.model.TenantModel;
import com.dev.schoolbox.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (útil em ambiente de desenvolvimento)
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // 🔹 Listar todos os tenants
    @GetMapping
    public ResponseEntity<List<TenantModel>> listarTenants() {
        List<TenantModel> tenants = tenantService.listarTenants();
        return ResponseEntity.ok(tenants);
    }

    // 🔹 Buscar tenant por ID
    @GetMapping("/{id}")
    public ResponseEntity<TenantModel> buscarPorId(@PathVariable UUID id) {
        Optional<TenantModel> tenant = tenantService.buscarPorId(id);
        return tenant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Criar novo tenant
    @PostMapping
    public ResponseEntity<TenantModel> criarTenant(@RequestBody TenantModel tenant) {
        TenantModel novoTenant = tenantService.criarTenant(tenant);
        return ResponseEntity.ok(novoTenant);
    }

    // 🔹 Atualizar tenant existente
    @PutMapping("/{id}")
    public ResponseEntity<TenantModel> atualizarTenant(@PathVariable UUID id, @RequestBody TenantModel tenantAtualizado) {
        Optional<TenantModel> tenant = tenantService.atualizarTenant(id, tenantAtualizado);
        return tenant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Deletar tenant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTenant(@PathVariable UUID id) {
        boolean deletado = tenantService.deletarTenant(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // 🔹 Alterar status (ativo/inativo)
    @PatchMapping("/{id}/status")
    public ResponseEntity<TenantModel> alterarStatus(@PathVariable UUID id, @RequestParam boolean ativo) {
        Optional<TenantModel> tenant = tenantService.alterarStatus(id, ativo);
        return tenant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
