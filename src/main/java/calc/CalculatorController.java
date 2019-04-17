package calc;

import calc.services.CalculatorService;
import calc.services.CalculatorService.OperationNotSupported;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan
public class CalculatorController {
    @Autowired
    CalculatorService calcService;  // dependency injection

    @GetMapping("/calc")
    public ResponseEntity<String> index(@RequestParam(name="num1") double a,    // alias
                        @RequestParam(name="num2") double b,
                        @RequestParam(name="op") String op) {

        if(op.isEmpty()) {
            return new ResponseEntity<>("The op is missing", HttpStatus.BAD_REQUEST); // exit and stop
        }

        try {
            double result = calcService.calculate(a,b,op);
            return ResponseEntity.ok(Double.toString(result));
        } catch(OperationNotSupported e) {
            return ResponseEntity.ok("Operation is not supported");
        }
    }
}
