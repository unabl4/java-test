package calc;

public class CalculationResult {
    private String result;  // the actual outcome (value or error message)
    private Boolean success;    // is successful

    CalculationResult(String result, Boolean success) {
        this.result = result;
        this.success = success;
    }

    // ---

    public String getResult() {
        return result;
    }

    public Boolean getSuccess() {
        return success;
    }
}
