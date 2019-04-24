package com.unabl4.calc.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

public class CalculationRequest {
    public static final String OP_REGEX = "^sum|sub|prod|mul|div$";    // supported operations

    @NotNull
    private BigDecimal num1;
    @NotNull
    private BigDecimal num2;
    @NotNull
    @NotBlank
    @Pattern(regexp = OP_REGEX)
    private String op;

    CalculationRequest(BigDecimal num1, BigDecimal num2, String op) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
    }

    // ---

    public BigDecimal getNum1() {
        return num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public String getOp() {
        return op;
    }
}
