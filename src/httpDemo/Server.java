package httpDemo;



import javax.xml.ws.Endpoint;
import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        Endpoint.publish("http://localhost:8080/DRRS",
                new DRRSImpl());
    }
}
