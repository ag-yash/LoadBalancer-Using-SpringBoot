package com.springboot.loadbalancer.load_balancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadBalancerApplication {
	//REMEMBER - We are the Client here. Load Balancer is the middle man. 
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8081); //Load Balancer listens on port 8081
		System.out.println("Load Balancer is running on port 8081");
		while(true) { //Always listening to incoming request (TCP connection)
			Socket socket = serverSocket.accept(); //Establishing the connection
			System.out.println("TCP connection established with client" + socket.toString());
			handlesocket(socket); //Handling all the requests
		}
	}
	public static void handlesocket(Socket socket) {
		ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket);
		Thread clientHandlerThread = new Thread(clientSocketHandler);
		clientHandlerThread.start();
	}

}
