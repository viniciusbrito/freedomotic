package com.freedomotic.plugins.devices.atualiza;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="devicesid", noClassnameStored=true)
public class DeviceID {

    @Id
    private String className;
    private Long counter = 1L;

    public DeviceID() {
    }

    public DeviceID(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
