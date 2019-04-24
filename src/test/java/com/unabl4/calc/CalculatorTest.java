package com.unabl4.calc;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.unabl4.Application;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})    // 'classes' is important
@AutoConfigureMockMvc
public class CalculatorTest {
    @Autowired
    private MockMvc mockMvc;

    // ---

    // classic problem
    @Test
    public void pointOnePlusPointTwo() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=0.1&num2=0.2&op=sum"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("0.3", jsonResult);
        assertEquals(true, jsonSuccess);
    }

    @Test
    public void tenDividedByTwo() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=10&num2=2&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("5", jsonResult);
        assertEquals(true, jsonSuccess);
    }

    @Test
    public void fiveDividedByPointFive() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=5&num2=0.5&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("10", jsonResult);
        assertEquals(true, jsonSuccess);
    }

    @Test
    public void fiveDividedByFour() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=5&num2=4&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("1.25", jsonResult);
        assertEquals(true, jsonSuccess);
    }

    @Test
    public void sixDividedByTwoBothDoubles() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=6.0&num2=3.0&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("2", jsonResult);
        assertEquals(true, jsonSuccess);
    }

    // division by zero
    @Test
    public void sixDividedByZeroBothDoubles() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=6.0&num2=0.0&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("Division by zero is not allowed", jsonResult);
        assertEquals(false, jsonSuccess);
    }

    // division by zero
    @Test
    public void sixDividedByZeroDivisorIsDouble() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=6&num2=0.0&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("Division by zero is not allowed", jsonResult);
        assertEquals(false, jsonSuccess);
    }

    // division by zero
    @Test
    public void sixDividedByZeroDividendIsDouble() throws Exception {
        MvcResult json = mockMvc.perform(get("/calc?num1=6.0&num2=0&op=div"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("Division by zero is not allowed", jsonResult);
        assertEquals(false, jsonSuccess);
    }
}
