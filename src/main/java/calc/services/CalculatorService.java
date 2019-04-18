package calc.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    // calculator operation not supported
    public class OperationNotSupported extends Exception {}
    public class ZeroDivisionError extends Exception {}

    // ---

    public double calculate(double a, double b, String op) throws OperationNotSupported, ZeroDivisionError {
        switch(op) {
            case "sum":
                return a+b;
            case "sub":
                return a-b;
            case "prod":
            case "mul": // mul
                return a*b;
            case "div":
                if(b == 0) {
                    throw new ZeroDivisionError();
                }
                return a/b;
            default:
                // something that we do not support
                throw new OperationNotSupported();
        }
    }
}
