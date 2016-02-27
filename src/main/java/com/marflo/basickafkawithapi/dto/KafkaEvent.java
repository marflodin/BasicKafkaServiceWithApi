package com.marflo.basickafkawithapi.dto;

public class KafkaEvent {
    private Boolean isRemoved;
    private String objectA;
    private String objectB;
    private String objectC;

    public KafkaEvent(Boolean isRemoved, String objectA, String objectB, String objectC) {
        this.isRemoved = isRemoved;
        this.objectA = objectA;
        this.objectB = objectB;
        this.objectC = objectC;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public String getObjectA() {
        return objectA;
    }

    public String getObjectB() {
        return objectB;
    }

    public String getObjectC() {
        return objectC;
    }
}
