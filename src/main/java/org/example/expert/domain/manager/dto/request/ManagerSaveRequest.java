package org.example.expert.domain.manager.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerSaveRequest {

    @NotNull
    private Long managerUserId; // 일정 작상자가 배치하는 유저 id
}
