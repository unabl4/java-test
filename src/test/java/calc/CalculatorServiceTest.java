package calc;

import calc.services.CalculatorService;
import calc.services.CalculatorService.OperationNotSupported;
import calc.services.CalculatorService.ZeroDivisionError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CalculatorServiceTest {
    private CalculatorService srv;

    @Mock
    CalculatorService calcService;  // component under test

    @Before
    public void prepareService() {
        this.srv = new CalculatorService(); // ?
    }

    // ---

    @Test(expected = OperationNotSupported.class)   // exception to be thrown
    public void emptyOp() throws Exception {
        srv.calculate(BigDecimal.valueOf(1), BigDecimal.valueOf(2), "");
    }

    @Test(expected = OperationNotSupported.class)   // exception to be thrown
    public void incorrectOp() throws Exception {
        srv.calculate(BigDecimal.valueOf(3), BigDecimal.valueOf(4), "op");  // none of 'sum', 'sub', 'prod', 'div'
    }

    @Test(expected = OperationNotSupported.class)   // exception to be thrown
    public void caseSensitiveOp() throws Exception {
        srv.calculate(BigDecimal.valueOf(3), BigDecimal.valueOf(4), "Sum");  // and not 'sum'
    }

    // ---

    @Test
    public void testTwoPlusTwo() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(2), BigDecimal.valueOf(2), "sum");
        assertEquals(4, result.doubleValue(), 0);
    }

    @Test
    public void minusTwoPlusTwo() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(-2), BigDecimal.valueOf(2), "sum");
        assertEquals(0, result.doubleValue(), 0);
    }

    @Test
    public void fivePlusZeroTwo() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(0.2), "sum");
        assertEquals(5.2, result.doubleValue(), 0);
    }

    @Test
    public void fiveMinusTwo() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(2), "sub");
        assertEquals(3, result.doubleValue(), 0);
    }

    @Test
    public void fiveMinusZeroTwo() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(0.2), "sub");
        assertEquals(4.8, result.doubleValue(), 0);
    }

    // tricky part
    @Test
    public void smallFloatsSum() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), "sum"); // 0.1 + 0.2 != 0.3 (float)
        assertEquals(0.3, result.doubleValue(), 0);
    }

    @Test
    public void smallFloatsSub() throws Exception {
        BigDecimal result = srv.calculate(BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.1), "sub"); // 0.3 - 0.1 != 0.2 (float)
        assertEquals(0.2, result.doubleValue(), 0);
    }

    @Test
    public void smallFloatsProd() throws Exception { // or 'prod'
        BigDecimal result = srv.calculate(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), "prod"); // 0.1 * 0.2 != 0.02 (float)
        assertEquals(0.02, result.doubleValue(), 0);
    }

    @Test
    public void smallFloatsProdMul() throws Exception { // or 'prod'
        BigDecimal result = srv.calculate(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), "mul"); // 0.1 * 0.2 != 0.02 (float)
        assertEquals(0.02, result.doubleValue(), 0);
    }

    @Test
    public void smallFloatsDiv() throws Exception { // or 'prod'
        BigDecimal result = srv.calculate(BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.1), "div"); // 0.3 / 0.1 != 3 (float)
        assertEquals(3, result.doubleValue(), 0);
    }

    // ---

    @Test(expected = ZeroDivisionError.class)   // exception to be thrown
    public void divisionByZeroIntDivisor() throws Exception {
        srv.calculate(BigDecimal.valueOf(1.5), BigDecimal.valueOf(0), "div");
    }

    @Test(expected = ZeroDivisionError.class)   // exception to be thrown
    public void divisionByZeroDoubleDivisor() throws Exception {
        srv.calculate(BigDecimal.valueOf(1.5), BigDecimal.valueOf(0.0), "div");
    }

    @Test(expected = ZeroDivisionError.class)   // exception to be thrown
    public void divisionByZeroIntDivisorIntDividend() throws Exception {
        srv.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(0), "div");
    }

    @Test(expected = ZeroDivisionError.class)   // exception to be thrown
    public void divisionByZeroDoubleDivisorIntDividend() throws Exception {
        srv.calculate(BigDecimal.valueOf(5), BigDecimal.valueOf(0.0), "div");
    }
}
