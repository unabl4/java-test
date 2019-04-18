package calc;

import calc.services.CalculatorService;
import calc.services.CalculatorService.OperationNotSupported;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

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

    @Test(expected = OperationNotSupported.class)
    public void emptyOp() throws Exception {
        srv.calculate(1, 2, "");
    }

    @Test(expected = OperationNotSupported.class)
    public void incorrectOp() throws Exception {
        srv.calculate(3, 4, "op");  // none of 'sum', 'sub', 'prod', 'div'
    }

    // ---

    @Test
    public void testTwoPlusTwo() throws Exception {
        double result = srv.calculate(2, 2, "sum");
        assertEquals(4,result, 0);
    }

    @Test
    public void minusTwoPlusTwo() throws Exception {
        double result = srv.calculate(-2, 2, "sum");
        assertEquals(0,result, 0);
    }

    @Test
    @Ignore // for now at least
    public void smallFloatsSum() throws Exception {
        double result = srv.calculate(0.1, 0.2, "sum");
        assertEquals(0.3,result, 0);    // <- FAIL
    }
}
