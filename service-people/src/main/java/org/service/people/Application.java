//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.people;

import io.swagger.annotations.Api;
import org.service.people.model.ModelPerson;
import org.service.people.rest.PeopleRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableDiscoveryClient
@RestController
@EnableCircuitBreaker
@EnableHystrix
@SpringBootApplication
@Api(value = "service-people", description = "All operations for the service-people.")
public class Application {

    @Autowired
    private PeopleRestController restPeople;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RequestMapping(value = "/getPeopleMultiple/{peopleCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelPerson> getPeopleMultiple(@PathVariable("peopleCount") int peopleCount) {
        return restPeople.getPeopleMultiple(peopleCount);
    }

    @RequestMapping(value = "/getPeopleSingle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelPerson getPeopleSingle() {
        return restPeople.getPeopleSingle();
    }

    @RequestMapping(value = "/getException", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getException() throws Exception {
        return restPeople.getException();
    }
}