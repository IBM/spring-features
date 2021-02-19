//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.addressbook;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.service.addressbook.model.ModelPerson;
import org.service.addressbook.rest.AddressbookRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@EnableHystrix
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@CrossOrigin(origins = "*")
@Api(value = "service-addressbook", description = "All operations for the service-addressbook.")
public class Application {

    @Autowired
    private AddressbookRestController restAdressbook;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    Sampler sampler() {
        return span -> true;
    }

    @ApiOperation(value = "Get a addressbook with a number of people. This request gets the complete list of people.", response = ModelPerson.class)
    @RequestMapping(value = "/getAddressbookMultiple/{peopleCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelPerson> getAddressBookMultiple(@ApiParam(value = "This is a description for peopleCount.", required = true) @PathVariable("peopleCount") int peopleCount) {
        return restAdressbook.getAddressBookMultiple(peopleCount);
    }

    @ApiOperation(value = "Get a addressbook with a number of people. This request requests each person.", response = ModelPerson.class)
    @RequestMapping(value = "/getAddressbookSingle/{peopleCount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelPerson> getAddressBookSingle(@ApiParam(value = "This is a description for peopleCount.", required = true) @PathVariable("peopleCount") int peopleCount) {
        return restAdressbook.getAddressBookSingle(peopleCount);
    }
}