package com.unabl4.calc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorPostTest {
    @Autowired
    private MockMvc mockMvc;

    // ---

    // classic problem
    @Test
    public void pointOnePlusPointTwo() throws Exception {
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "0.1");
        m.put("num2", "0.2");
        m.put("op", "sum");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "10");
        m.put("num2", "2");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "5");
        m.put("num2", "0.5");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "5");
        m.put("num2", "4");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "6.0");
        m.put("num2", "3.0");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "6.0");
        m.put("num2", "0.0");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "6");
        m.put("num2", "0.0");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
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
        HashMap<String, String> m = new HashMap<>();
        m.put("num1", "6.0");
        m.put("num2", "0");
        m.put("op", "div");

        MockHttpServletRequestBuilder req = post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(m)); // ?

        MvcResult json = mockMvc.perform(req)
                .andExpect(status().isOk()) // = 200
                .andReturn();

        DocumentContext parsedJson = JsonPath.parse(json.getResponse().getContentAsString());
        String jsonResult = parsedJson.read("$.result").toString();
        Boolean jsonSuccess = parsedJson.read("$.success");
        assertEquals("Division by zero is not allowed", jsonResult);
        assertEquals(false, jsonSuccess);
    }
}
