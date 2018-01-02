package com.freedomotic.plugins.devices.atualiza;

import com.freedomotic.api.EventTemplate;
import com.freedomotic.api.Protocol;
import com.freedomotic.app.Freedomotic;
import com.freedomotic.exceptions.UnableToExecuteException;
import com.freedomotic.reactions.Command;

import com.freedomotic.plugins.devices.atualiza.api.WebServer;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

//TODO: please rename this class!
public class Atualiza extends Protocol {

    public final Logger LOG = LoggerFactory.getLogger(Atualiza.class.getName());
    /*final int POLLING_WAIT;*/
    protected WebServer server;

    public Atualiza() {
        super("Atualiza", "/atualiza/manifest.xml");
        setPollingWait(-1);
        this.server = new WebServer(this);
    }

    @Override
    protected void onRun() {
        LOG.info("Plugin Atuliza: Inicializando servidor ");
        this.server.start();
    }

    @Override
    protected void onStart() {
        LOG.info("atualiza plugin is started");

    }

    @Override
    protected void onStop() {
        LOG.info("atualiza plugin is stopped ");
        try {
            DeviceDAO dvdao = new DeviceDAO();
            /*dvdao.destroy();*/
            this.server.stop();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void onShowGui() {
        /**
         * Uncomment the line below to add a GUI to this plugin. The GUI can be
         * started with a right-click on plugin list in the desktop front-end.
         * A GUI is useful for example to configure this plugin at runtime.
         */
        //bindGuiToPlugin(new HelloWorldGui(this));
    }

    @Override
    protected void onHideGui() {
        // Implement here what to do when the plugin GUI is closed.
        // For example you can change the plugin description.
        setDescription("My GUI is now hidden");
        // Or stop the plugin programmatically.
        // this.stop();
    }

    @Override
    // Receive commands from freedomotic.
    protected void onCommand(Command c)
            throws IOException, UnableToExecuteException {
        LOG.info("atualiza plugin receives a command called " + c.getName() + " with parameters "
                + c.getProperties().toString());
    }

    @Override
    // Receive events from freedomotic.
    protected void onEvent(EventTemplate event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean canExecute(Command c) {
        // Don't mind this method for now.
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
