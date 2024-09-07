package com.eatsleep.user.client.domain;

import com.eatsleep.user.common.DomainEntity;
import com.eatsleep.user.security.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Client {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String type;
    private LocalDateTime expirationDate;
    private Role role;
}
