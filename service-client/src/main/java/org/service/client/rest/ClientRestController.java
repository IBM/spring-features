//
// Copyright 2020- IBM Inc. All rights reserved
// SPDX-License-Identifier: Apache2.0
//
package org.service.client.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ClientRestController {

    private static final Logger LOG = Logger.getLogger(ClientRestController.class.getName());
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    public ClientRestController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping("/")
    public String getClientPage(Model model) {
        // Get the gateway service
        List<ServiceInstance> instances = discoveryClient.getInstances("service-gateway");
        URI instance = instances.get(0).getUri();
        model.addAttribute("gatewayservice", instance);

        LOG.log(Level.INFO, "Service Gateway URL: " + instance);

        return "webpage";
    }

    @RequestMapping("/getServiceInfo/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}