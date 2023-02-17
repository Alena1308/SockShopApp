package sky.course3.sockshopapp.model;

import java.time.LocalDateTime;

public enum OperationType {
    IN("Приемка"), OUT("Выдача"), OFF("Списание");
    private final String operation;

    OperationType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
