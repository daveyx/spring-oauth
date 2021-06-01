package com.example.oauthdemo.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void test_publicHome() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api1/public-resource")).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String actual = mockHttpServletResponse.getContentAsString();

        assertEquals(new JSONObject(Controller.getPublicContent()).toString(), new JSONObject(actual).toString());
    }

}
