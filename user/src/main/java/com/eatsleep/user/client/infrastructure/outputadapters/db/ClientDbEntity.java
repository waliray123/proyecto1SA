package com.eatsleep.user.client.infrastructure.outputadapters.db;

import com.eatsleep.user.common.utils.PasswordUtils;
import com.eatsleep.user.client.domain.Client;
import com.eatsleep.user.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class ClientDbEntity {
    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    
    public Client toDomainModel() {

        return Client.builder()
                .id(UUID.fromString(this.getId()))
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .phone(this.getPhone())
                .type(this.getType())
                .expirationDate(this.getExpirationDate())
                .role(this.role)
                .build();
    }
    
    
    public static ClientDbEntity from(Client client) {
        if (client == null) {
            return null;
        }

        ClientDbEntity clientDbEntity = new ClientDbEntity();
        clientDbEntity.setId(client.getId().toString());
        clientDbEntity.setEmail(client.getEmail());
        clientDbEntity.setPassword(PasswordUtils.encryptPassword(client.getPassword()));
        clientDbEntity.setName(client.getName());
        clientDbEntity.setPhone(client.getPhone());
        clientDbEntity.setType(client.getType());
        clientDbEntity.setExpirationDate(client.getExpirationDate() != null ? client.getExpirationDate() : null);
        clientDbEntity.setRole(client.getRole() != null ? client.getRole() : Role.CUSTOMER);

        return clientDbEntity;
    }
    
}
