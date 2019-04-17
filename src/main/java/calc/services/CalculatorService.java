package calc.services;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    // calculator operation not supported
    public class OperationNotSupported extends Exception {}

    // ---

    public double calculate(double a, double b, String op) throws OperationNotSupported {
        switch(op) {
            case "sum":
                return a+b;
            case "sub":
                return a-b;
            case "prod": // mul
                return a*b;
            case "div":
                return a/b; // in this case we are not afraid of division by zero
            default:
                // something that we do not support
                throw new OperationNotSupported();
        }
    }
}
