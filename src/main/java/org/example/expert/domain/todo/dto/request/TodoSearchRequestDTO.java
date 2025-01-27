package org.example.expert.domain.todo.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TodoSearchRequestDTO {
    private final String title;
    private final LocalDateTime startCreationTime;
    private final LocalDateTime endCreationTime;
    private final String nickname;
}
