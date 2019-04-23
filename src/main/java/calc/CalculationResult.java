package calc;

public class CalculationResult {
    private Object result;  // the actual outcome (value or error message)
    private Boolean success;    // is successful

    CalculationResult(Object result, Boolean success) {
        this.result = result;
        this.success = success;
    }

    // ---

    public Object getResult() {
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }
}
