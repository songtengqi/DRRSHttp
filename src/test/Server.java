package test;

import javax.xml.ws.Endpoint;

public class Server {

    public static void main(String[] args) {
        System.out.println("Server is ready...");
        Endpoint.publish("http://localhost:8080/calculator",
                new CalculatorImpl());
        
    }
}
