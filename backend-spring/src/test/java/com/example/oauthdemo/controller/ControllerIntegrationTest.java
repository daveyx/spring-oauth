package com.example.oauthdemo.controller;

import com.example.oauthdemo.OAuthControllerIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${security.oauth.endpoint}")
    private String oauthTokenEndpoint;

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;


    @Test
    public void test_publicHome() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api1/public-resource")).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        String actual = mockHttpServletResponse.getContentAsString();

        assertEquals(new JSONObject(Controller.getPublicContent()).toString(), new JSONObject(actual).toString());
    }

    @Test
    public void test_resource_notAuthenticated() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api1/resource")).andReturn();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void test_resource_authenticated() throws Exception {
        JSONObject token = new JSONObject(OAuthControllerIntegrationTest.getToken(mockMvc, oauthTokenEndpoint, clientId, clientSecret));

        MvcResult mvcResult = mockMvc.perform(get("/api1/resource")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.getString("access_token")))
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        Map<String, String> actual = objectMapper.readValue(mockHttpServletResponse.getContentAsString(), Map.class);

        Map<String, String> expected = Controller.getSecuredContent(1);

        assertEquals(expected.get("content"), actual.get("content"));
    }

}
