package com.springboot.loadbalancer.load_balancer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.springboot.loadbalancer.load_balancer.utils.LoadBalancerBackend;

public class ClientSocketHandler implements Runnable{
    private Socket socket; //TCP connection between Load Balancer and Client
    ClientSocketHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream ClientToLoadBalancerStream = socket.getInputStream(); //Client to LB 
            OutputStream LoadBalancerToClientStream = socket.getOutputStream(); //LB to Client

            String backendServerIPAddress = LoadBalancerBackend.getBackendServer(); //gets you the IP address of the backend server
            System.out.println("Redirecting request to backend server: " + backendServerIPAddress);

            //Create TCP connection b/w Load Balancer and Backend Server
            Socket socketToBackend = new Socket(backendServerIPAddress, 8080); //Example - "172.168.0.0 : 8080" 

            InputStream BackendToLoadBalancerStream = socketToBackend.getInputStream(); //Backend to LB
            OutputStream LoadBalancerToBackendStream = socketToBackend.getOutputStream(); //LB to Backend

            //Thread which handles all the data coming from the client and redirects that data to backend server
            Thread ClientDataHandler = new Thread(){
                public void run(){
                    int data;
                    try {
                        while((data = ClientToLoadBalancerStream.read()) != -1){
                            LoadBalancerToBackendStream.write(data); //redirecting data to backend server
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            ClientDataHandler.start();

            //Thread which handles all the data coming from the backend server and redirects that data to the client
            Thread BackendDataHandler = new Thread(){
                public void run(){
                    int data;
                    try {
                        while((data = BackendToLoadBalancerStream.read()) != -1){
                            LoadBalancerToClientStream.write(data); //redirecting data to client
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            BackendDataHandler.start();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
