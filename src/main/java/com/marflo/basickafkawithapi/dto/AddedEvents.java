package com.marflo.basickafkawithapi.dto;

import java.util.HashSet;
import java.util.Set;

public class AddedEvents {

    private static Set<String> objectA = new HashSet<>();
    private static Set<String> objectB = new HashSet<>();
    private static Set<String> objectC = new HashSet<>();
    private static AddedEvents instance = null;

    protected AddedEvents() {
        // Exists only to defeat instantiation.
    }

    public static AddedEvents getInstance() {
        if(instance == null) {
            instance = new AddedEvents();
        }
        return instance;
    }

    public void addStringToObjectA(String objectAEntry) {
        objectA.add(objectAEntry);
    }

    public void removeStringFromObjectA(String objectAEntry) {
        objectA.remove(objectAEntry);
    }

    public static Set<String> getObjectA() {
        return objectA;
    }

    public void addStringToObjectB(String objectBEntry) {
        objectB.add(objectBEntry);
    }

    public void removeStringFromObjectB(String objectBEntry) {
        objectB.remove(objectBEntry);
    }

    public static Set<String> getObjectB() {
        return objectB;
    }

    public void addStringToObjectC(String objectCEntry) {
        objectC.add(objectCEntry);
    }

    public void removeStringFromObjectC(String objectCEntry) {
        objectC.remove(objectCEntry);
    }

    public static Set<String> getObjectC() {
        return objectC;
    }
}
