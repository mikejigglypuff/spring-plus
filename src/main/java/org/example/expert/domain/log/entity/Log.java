package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String requestBody;

    @Column
    private String errorMessage;

    public Log(String requestBody, String errorMessage) {
        this.requestBody = requestBody;
        this.errorMessage = errorMessage;
    }

    public static Log of(ManagerSaveRequest requestBody, String errorMessage) {
        return new Log(requestBody.toString(), errorMessage);
    }
}
