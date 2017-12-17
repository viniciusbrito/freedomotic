package com.freedomotic.plugins.devices.atualiza.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;

import com.freedomotic.plugins.devices.atualiza.Device;
import com.freedomotic.plugins.devices.atualiza.DeviceDAO;
import com.freedomotic.plugins.devices.atualiza.Atualiza;

import com.freedomotic.events.GenericEvent;


import java.util.List;

@Path("/")
public class Resource {

    private final Atualiza parent;

    public Resource(Atualiza parent) {
        this.parent = parent;
    }

    /*Show All*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevices() {

        DeviceDAO dvDAO = new DeviceDAO();
        List<Device> dispositivos = dvDAO.getAll();
        return Response.status(201).entity(dispositivos.toString()).build();
    }

    /*Show a device*/
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevice(@PathParam("id") ObjectId id) {

        DeviceDAO dvDAO = new DeviceDAO();
        Device dv = dvDAO.findById(id);
        return Response.status(201).entity(dv.toString()).build();
    }

    /*Create/Save/Update a device*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDevice(Device device) {

        DeviceDAO dvDAO = new DeviceDAO();
        Device dbDevice = dvDAO.findById(device.getId());

        if((dbDevice != null) && (dbDevice.isStatus() != device.isStatus())) {
            //Disparar evento
            GenericEvent event = new GenericEvent(this);
            event.setDestination("app.event.sensor.messages.atualiza");
            event.addProperty("id",  device.getId().toString());
            event.addProperty("message", "The device "+device.getName()+" has been updated!");
            this.parent.getBusService().send(event);

        }

        Device savedDevice = dvDAO.save(device);

        /*String result = "{\"id\":\"" + savedDevice.getId()+"\"}";*/
        return Response.status(201).entity(savedDevice.toString()).build();

    }

    /*Create/Save/Update a list of devices*/
    @POST
    @Path("/devices")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDevices(List<Device> devices) {

        DeviceDAO dvDAO = new DeviceDAO();
        devices.forEach(device->dvDAO.save(device));
        return Response.status(201).entity(devices.toString()).build();

    }

    /*Remove a device*/
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response remove(@PathParam("id") ObjectId id) {

        DeviceDAO dvDAO = new DeviceDAO();
        Device dv = dvDAO.findById(id);

        int code = 404;
        String str = "Device Not Found";

        if(dv != null) {

            if(dvDAO.delete(dv)) {
                code = 202;
                str = "{\"id\" : \"" + id.toString() + "\"}";
            }
        }

        return Response.status(code).entity(str).build();
    }
}