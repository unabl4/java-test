package calc;

import common.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})    // 'classes' is important
@AutoConfigureMockMvc
public class CalculatorTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void tenDividedByTwo() throws Exception {
        mockMvc.perform(get("/calc?num1=10&num2=2&op=div"))
                .andExpect(status().isOk()) // = 200
                .andExpect(jsonPath("$.result", is("5")))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void fiveDividedByPointFive() throws Exception {
        mockMvc.perform(get("/calc?num1=5&num2=0.5&op=div"))
            .andExpect(status().isOk()) // = 200
            .andExpect(jsonPath("$.result", is("10")))
            .andExpect(jsonPath("$.success", is(true)));
    }
}
