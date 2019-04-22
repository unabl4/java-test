package calc;

import calc.services.CalculatorService;
import calc.services.CalculatorService.OperationNotSupported;
import calc.services.CalculatorService.ZeroDivisionError;
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
                                   @RequestParam @NotBlank @Pattern(regexp = OP_REGEX) String op) {

        try {
            BigDecimal result = calcService.calculate(num1, num2, op);
            String finalResult = result.toPlainString().replaceAll("(?<=\\.)0+$", "");   // remove trailing zeroes
            return new CalculationResult(finalResult, true);
        } catch(OperationNotSupported e) {
            return new CalculationResult("Operation is not supported", false);
        } catch(ArithmeticException | ZeroDivisionError ex) {    // arithmetic exception just in case
            return new CalculationResult("Division by zero is not allowed", false);
        } catch(Exception ex) {
            // all other exceptions
            return new CalculationResult("Unknown error occurred", false);
        }
    }
}
