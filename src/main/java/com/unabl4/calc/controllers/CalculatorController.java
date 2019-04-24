package com.unabl4.calc.controllers;

import com.unabl4.calc.models.CalculationRequest;
import com.unabl4.calc.models.CalculationResult;
import com.unabl4.calc.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static com.unabl4.calc.models.CalculationRequest.OP_REGEX;

@RestController
@Validated // necessary
public class CalculatorController {
    @Autowired
    CalculatorService calcService;  // dependency injection

    @GetMapping("/calculate")    // no @ResponseBody annotation is necessary
    public CalculationResult index(@RequestParam BigDecimal num1,
                                   @RequestParam BigDecimal num2,
                                   @RequestParam(required=false) @NotBlank @Pattern(regexp = OP_REGEX) String op) {
        return doCalculate(num1, num2, op);
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public CalculationResult create(@Valid @RequestBody CalculationRequest req) {
        return doCalculate(req.getNum1(), req.getNum2(), req.getOp());
    }

    // ---

    private CalculationResult doCalculate(BigDecimal num1, BigDecimal num2, String op) {
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
