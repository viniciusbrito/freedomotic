package com.freedomotic.plugins.devices.atualiza;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Embedded;

import com.freedomotic.plugins.devices.atualiza.Feature;
import com.freedomotic.plugins.devices.atualiza.Status;

import java.util.List;


@Entity(value="devices", noClassnameStored=true)
public class Device {

    @Id
    private Long id;
    private String name;
    private String location;
    @Embedded
    private List<Status> status;
    @Embedded
    private List<Feature> features;
    private String xml;

    public Device() {
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
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
                ", \"location\":\"" + this.location + '"' +
                ", \"features\":" + this.features.toString() +
                ", \"status\":" + this.status.toString() +
                ", \"xml\":\"" + this.replace(this.xml) + "\"}";
    }

    private String replace(String str) {

        return str.replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }
}
