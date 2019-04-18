package calc.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorService {
    // calculator operation not supported
    public class OperationNotSupported extends Exception {}
    public class ZeroDivisionError extends Exception {}

    // ---

    public BigDecimal calculate(BigDecimal a, BigDecimal b, String op) throws OperationNotSupported, ZeroDivisionError {
        switch(op) {
            case "sum":
                return a.add(b);
            case "sub":
                return a.subtract(b);
            case "prod":
            case "mul": // mul
                return a.multiply(b);
            case "div":
                if(b.compareTo(BigDecimal.ZERO) == 0) { // 'equals' cannot be used because of scale comparison
                    throw new ZeroDivisionError();
                }
                return a.divide(b);
            default:
                // something that we do not support
                throw new OperationNotSupported();
        }
    }
}
