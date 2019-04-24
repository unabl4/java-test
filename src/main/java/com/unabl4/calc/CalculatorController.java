package com.unabl4.calc;

import com.unabl4.calc.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@RestController
@Validated // necessary
public class CalculatorController {
    @Autowired
    CalculatorService calcService;  // dependency injection

    private static final String OP_REGEX = "^sum|sub|prod|mul|div$";    // supported operations

    @GetMapping("/calc")    // no @ResponseBody annotation is necessary
    public CalculationResult index(@RequestParam BigDecimal num1,
                                   @RequestParam BigDecimal num2,
                                   @RequestParam(required=false) @NotBlank @Pattern(regexp = OP_REGEX) String op) {

        try {
            BigDecimal result = calcService.calculate(num1, num2, op);
            BigDecimal finalResult = result.stripTrailingZeros();
            // avoiding 'scientific'-looking results like '1E+1' (=10)
            if(finalResult.scale() < 0) finalResult = finalResult.setScale(0);
            return new CalculationResult(finalResult, true);
        } catch(CalculatorService.OperationNotSupported e) {
            return new CalculationResult("Operation is not supported", false);
        } catch(ArithmeticException | CalculatorService.ZeroDivisionError ex) {    // arithmetic exception just in case
            return new CalculationResult("Division by zero is not allowed", false);
        } catch(Exception ex) {
            // all other exceptions
            return new CalculationResult("Unknown error occurred", false);
        }
    }
}
