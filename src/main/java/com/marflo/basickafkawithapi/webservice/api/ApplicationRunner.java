package com.marflo.basickafkawithapi.webservice.api;

import com.marflo.basickafkawithapi.kafkaconsumer.KafkaEventConsumer;
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
        Thread thread = new Thread(new KafkaEventConsumer("group1", "testTopic"));
        thread.start();
        final ApplicationEndpoint endpoint = new ApplicationEndpoint();
        environment.jersey().register(endpoint);
    }
}
