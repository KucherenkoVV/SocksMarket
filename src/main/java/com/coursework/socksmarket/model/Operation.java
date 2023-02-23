package com.coursework.socksmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Operation {

    @NotEmpty
    private OperationType operationType;

    private LocalDateTime time;

    public Operation(OperationType operationType) {
        this.operationType = operationType;
        this.time = LocalDateTime.now();
    }
}
