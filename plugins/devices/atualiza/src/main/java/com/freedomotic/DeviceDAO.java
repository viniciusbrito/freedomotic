package com.freedomotic.plugins.devices.atualiza;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.bson.types.ObjectId;
import com.freedomotic.plugins.devices.atualiza.DeviceID;
import java.util.List;

import com.freedomotic.plugins.devices.atualiza.Device;
import com.freedomotic.plugins.devices.atualiza.Status;

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

        if(device.getId() == null) {
            Long id = generateID(device);
            device.setId(id);
        }
        this.datastore.save(device);
        return device;

    }

    public Device findById(Long id) {

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
        if(dbDevice != null) {

            List<Status> s1 = dbDevice.getStatus();
            List<Status> s2 = dv.getStatus();

            for (int i=0; i<s1.size(); i++) {
                if(s1.get(i).isStatus() != s2.get(i).isStatus()) {
                    return true;
                }
            }
        }
        return false;

    }

    protected Long generateID(Device dv) {

        String collName = this.datastore.getCollection(getClass()).getName();
        Query<DeviceID> query = this.datastore.find(DeviceID.class, "_id", collName);
        UpdateOperations<DeviceID> update = this.datastore.createUpdateOperations(DeviceID.class).inc("counter");
        DeviceID counter = this.datastore.findAndModify(query, update);
        if (counter == null) {
            counter = new DeviceID(collName);
            this.datastore.save(counter);
        }
        return counter.getCounter();

    }
}
