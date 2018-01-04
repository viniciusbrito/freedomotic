package com.freedomotic.plugins.devices.atualiza.api;

import javax.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import com.freedomotic.plugins.devices.atualiza.Atualiza;
import com.freedomotic.plugins.devices.atualiza.api.Resource;
import java.util.EnumSet;

public class WebServer {

    private Server server;
    private final Atualiza parent;

    public WebServer(Atualiza parent) {

        this.parent = parent;
        server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/api/v1/devices");
        handler.setAllowNullPathInfo(true);
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig())), "/*");
        server.setHandler(handler);


        FilterHolder filterHolder = handler.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        filterHolder.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

    }

    public void start()
    {
        try {
            this.server.start();
            this.server.join();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    public void stop()
    {
        try {
            this.server.stop();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    private ResourceConfig resourceConfig() {
        // manually injecting dependencies (clock) to Jersey resource classes
        return new ResourceConfig().register(new Resource(this.parent));
    }

}
