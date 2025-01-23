package org.example.expert.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UriWhiteList {
    AUTH("/auth"),
    HEALTH("/health"),
    BULK("/users/bulk");

    private final String prefix;

    public static boolean isWhiteList(String uri) {
        return Arrays.stream(values())
            .map(UriWhiteList::getPrefix)
            .anyMatch(uri::startsWith);
    }
}
