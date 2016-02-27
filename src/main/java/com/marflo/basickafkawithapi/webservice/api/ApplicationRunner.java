package com.marflo.basickafkawithapi.webservice.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ApplicationRunner extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApplicationRunner().run("server");
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    }

    @Override
    public void run(ApplicationConfiguration configuration,
                    Environment environment) {
        final ApplicationEndpoint endpoint = new ApplicationEndpoint();
        environment.jersey().register(endpoint);
    }
}
