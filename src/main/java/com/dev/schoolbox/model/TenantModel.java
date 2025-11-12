package com.dev.schoolbox.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Tenant")
public class TenantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // 🔹 Identificação básica
    private String name;                // Nome da instituição
    private String legalName;           // Nome jurídico (ex: Colégio Viva Saber LTDA)
    private String cnpj;                // Cadastro Nacional da Pessoa Jurídica (opcional se for exterior)
    private String documentNumber;      // Pode ser CNPJ ou outro tipo de documento

    // 🔹 Contato e informações gerais
    private String emailContact;
    private String phoneContact;
    private String websiteUrl;

    // 🔹 Endereço (novo relacionamento)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressModel address;

    // 🔹 Configuração do ambiente
    private String timezone;            // Ex: America/Sao_Paulo
    private String locale;              // Ex: pt_BR
    private String currency;            // Ex: BRL
    private boolean active;             // Status do tenant (ativo/inativo)

    // 🔹 Tipo de instituição (pública ou privada)
    private String schoolType;          // Ex: "public" ou "private"

    // 🔹 Customização de tema (para identidade visual da instituição)
    private String themeColorPrimary;
    private String themeColorSecondary;
    private String logoUrl;             // Link do logo
    private String faviconUrl;          // Ícone do painel

    // 🔹 Plano e limites
    private String planType;            // Ex: free, standard, premium
    private Integer maxUsers;           // Limite de usuários permitidos
    private Integer maxStorageMb;       // Limite de armazenamento
    private LocalDateTime planStartDate;
    private LocalDateTime planEndDate;

    // 🔹 Integração e domínios
    private String subdomain;           // Ex: vivasaber.smartclass.com
    private String externalApiKey;      // Chave para APIs externas
    private String externalLmsId;       // ID se integrar com Moodle, Google Classroom etc.

}
