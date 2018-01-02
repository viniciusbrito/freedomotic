package com.freedomotic.plugins.devices.atualiza;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.bson.types.ObjectId;

import java.util.List;

import com.freedomotic.plugins.devices.atualiza.Device;

public class DeviceDAO {

    final Morphia morphia;
    final Datastore datastore;

    public DeviceDAO () {

        this.morphia = new Morphia();
        this.morphia.mapPackage("com.freedomotic.plugins.devices.atualiza");

        this.datastore = this.morphia.createDatastore(new MongoClient(), "devicemanager");
        this.datastore.ensureIndexes();
    }

    public List<Device> getAll() {

        Query<Device> query = this.datastore.createQuery(Device.class);
        return query.asList();
    }

    public Device save(Device device) {

        this.datastore.save(device);
        Device savedDevice = this.findById(device.getId());
        return savedDevice;

    }

    public Device findById(ObjectId id) {

        List<Device> lt = this.datastore.find(Device.class).filter("id", id).asList();
        if(lt.isEmpty())
            return null;
        Device dv = (Device) lt.get(0);
        return dv;
    }

    public boolean delete(Device dv) {
        return this.datastore.delete(dv).wasAcknowledged()? true : false;
    }

    public void destroy() {
        this.datastore.getDB().dropDatabase();
    }

    public boolean equals(Device dv) {

        Device dbDevice = this.findById(dv.getId());
        if((dbDevice != null) && (dbDevice.isStatus() != dv.isStatus())) {
            return true;
        }
        return false;

    }
}
