package com.freedomotic.plugins.devices.atualiza;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Embedded;

import com.freedomotic.plugins.devices.atualiza.Feature;

import java.util.List;


@Entity(value="devices", noClassnameStored=true)
public class Device {

    @Id
    private ObjectId id;
    private String name;
    private boolean status;
    @Embedded
    private List<Feature> features;
    private String xml;

    public Device() {
        super();
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public void addFeature(Feature feature) {
        this.features.add(feature);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + this.id + '"' +
                ", \"name\":\"" + this.name + '"' +
                ", \"features\":" + this.features.toString() +
                ", \"status\":" + this.status +
                ", \"xml\":\"" + this.replace(this.xml) + "\"}";
    }

    private String replace(String str) {

        return str.replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }
}
