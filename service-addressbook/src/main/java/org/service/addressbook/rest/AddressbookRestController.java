//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.addressbook.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.service.addressbook.model.ModelPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AddressbookRestController {

    private final Logger LOG = Logger.getLogger(AddressbookRestController.class.getName());
    @Autowired
    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;

    public AddressbookRestController(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "getAddressBookAlternative", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
    })
    public List<ModelPerson> getAddressBookMultiple(int peopleCount) {
        // Get the people service
        List<ServiceInstance> instances = discoveryClient.getInstances("service-gateway");

        URI instance = instances.get(0).getUri();
        LOG.log(Level.INFO, "### Service Gateway URL: " + instance);

        String peopleURL = instance + "/api/people/getPeopleMultiple/" + peopleCount;
        LOG.log(Level.INFO, "### Service AddressBookMultiple Request: " + peopleURL);

        // Get info from other service
        ModelPerson[] listResult = restTemplate.getForObject(peopleURL, ModelPerson[].class);
        LOG.log(Level.INFO, "### Service AddressBookMultiple Response: " + listResult.length);
        return Arrays.asList(listResult);
    }

    @HystrixCommand(fallbackMethod = "getAddressBookAlternative", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
    })
    public List<ModelPerson> getAddressBookSingle(int peopleCount) {
        // Get the people service
        List<ServiceInstance> instances = discoveryClient.getInstances("service-gateway");

        URI instance = instances.get(0).getUri();
        LOG.log(Level.INFO, "### Service Gateway URL: " + instance);

        String peopleURL = instance + "/api/people/getPeopleSingle/";
        LOG.log(Level.INFO, "### Service getAddressBookSingle Request: " + peopleURL + ". peopleCount = " + peopleCount);

        // Generate a list of persons
        List<ModelPerson> listResult = new ArrayList<>();

        // Get info from other service
        for (int i = 0; i < peopleCount; i++) {
            listResult.add(restTemplate.getForObject(peopleURL, ModelPerson.class));
        }
        // Get info from other service
        LOG.log(Level.INFO, "### Service getAddressBookSingle Response: " + listResult.size());

        return listResult;
    }

    public List<ModelPerson> getAddressBookAlternative(int peopleCount) {
        LOG.log(Level.INFO, "### Circuit Breaker. Service people is not availabl. Alternative method is called.");
        List<ModelPerson> listPerson = new ArrayList<>();
        ModelPerson modelPerson = new ModelPerson();
        modelPerson.setFirstname("Lars");
        modelPerson.setLastname("Probst");
        listPerson.add(modelPerson);
        return listPerson;
    }
}