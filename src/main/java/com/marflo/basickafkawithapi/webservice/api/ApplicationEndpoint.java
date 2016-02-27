package com.marflo.basickafkawithapi.webservice.api;

import com.codahale.metrics.annotation.Timed;
import com.marflo.basickafkawithapi.dto.AddedEvents;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/added-events")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationEndpoint {
    final static Logger LOGGER = Logger.getLogger(ApplicationEndpoint.class.getName());
    AddedEvents events = AddedEvents.getInstance();

    @GET
    @Path("/object-a/{id}")
    @Timed
    public Boolean isInObjectAList(@PathParam("id") String id) {
        LOGGER.info("id: " + id);
        LOGGER.info("events: " + events.getObjectA());
        return events.getObjectA().contains(id);
    }

    @GET
    @Path("/object-b/{id}")
    @Timed
    public Boolean isInObjectBList(@PathParam("id") String id) {
        return events.getObjectB().contains(id);
    }

    @GET
    @Path("/object-c/{id}")
    @Timed
    public Boolean isInObjectCList(@PathParam("id") String id) {
        return events.getObjectC().contains(id);
    }
}
