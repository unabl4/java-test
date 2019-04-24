package com.unabl4.hello;

import com.unabl4.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloGet() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hello"))
                .andExpect(status().isOk()) // = 200
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("Hello World!", content);  // exact match
    }

    @Test
    public void testHelloPost() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/hello")).andReturn();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), result.getResponse().getStatus());
    }
}
