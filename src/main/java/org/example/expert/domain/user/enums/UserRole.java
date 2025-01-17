package org.example.expert.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String role;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UserRole"));
    }

    public static List<GrantedAuthority> getAuthority() {
        return Arrays.stream(UserRole.values())
            .map(role -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toList());
    }
}
