package test;
import javax.jws.WebService;
@WebService
public interface Calculator {
    public int add(int a, int b);
    public int multi(int a, int b);

}
