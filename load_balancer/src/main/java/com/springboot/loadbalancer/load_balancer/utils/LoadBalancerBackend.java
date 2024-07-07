package com.springboot.loadbalancer.load_balancer.utils;

import java.util.ArrayList;
import java.util.List;

public class LoadBalancerBackend {
    private static List<String> backendServers = new ArrayList<>();
    private static int serverCounter = 0;
    static { //static so that when the program is run, the backend servers are automatically listed
        backendServers.add("IP1");
        backendServers.add("IP1");
    }

    public static String getBackendServer() {
        if(serverCounter == backendServers.size()) {
            serverCounter = 0;
        }
        return backendServers.get(serverCounter++);
    }

}
