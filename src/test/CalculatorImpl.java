package test;
import javax.jws.WebService;

@WebService(endpointInterface = "test.Calculator")
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int multi(int a, int b) {
        return a*b;
    }

    public int minus(int a, int b){
        return a-b;
    }
}
