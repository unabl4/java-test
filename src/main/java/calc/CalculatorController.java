package calc;

import calc.services.CalculatorService;
import calc.services.CalculatorService.OperationNotSupported;
import calc.services.CalculatorService.ZeroDivisionError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@Validated // necessary
public class CalculatorController {
    @Autowired
    CalculatorService calcService;  // dependency injection

    private static final String OP_REGEX = "^sum|sub|prod|mul|div$";    // supported operations

    @GetMapping("/calc")
    public ResponseEntity<String> index(@RequestParam double num1,    // alias
                        @RequestParam double num2,
                        @RequestParam @NotBlank @Pattern(regexp = OP_REGEX) String op) {

        try {
            double result = calcService.calculate(num1, num2, op);
            return ResponseEntity.ok(Double.toString(result));
        } catch(OperationNotSupported e) {
            return ResponseEntity.ok("Operation is not supported");
        } catch(ArithmeticException | ZeroDivisionError ex) {    // just in case
            return ResponseEntity.ok("Division by zero is not allowed");
        }
    }
}
