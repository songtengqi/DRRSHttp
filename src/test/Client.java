package test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/calculator?wsdl");
        QName qName = new QName("http://test/","CalculatorImplService");
        Service service = Service.create(url,qName);
        Calculator port = service.getPort(Calculator.class);
        System.out.println(port.add(1,2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("int");
        Integer integer = scanner.nextInt();
        System.out.println(integer);




    }
}
