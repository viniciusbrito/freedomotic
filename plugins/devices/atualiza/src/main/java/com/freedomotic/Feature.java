package com.freedomotic.plugins.devices.atualiza;

public class Feature {

    private String name;
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return '{' +
                "\"name\":\"" + this.name + '"' +
                '}';
    }
}
