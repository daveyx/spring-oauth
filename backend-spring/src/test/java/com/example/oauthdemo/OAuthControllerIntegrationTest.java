package com.example.oauthdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OAuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${security.oauth.endpoint}")
    private String oauthTokenEndpoint;

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;


    @Test
    public void test_obtainToken() throws Exception {
        String token = getToken(mockMvc, oauthTokenEndpoint, clientId, clientSecret);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        assertNotNull(jsonParser.parseMap(token).get("access_token").toString());
    }

    public static String getToken(MockMvc mockMvc, String oauthTokenEndpoint, String clientId, String clientSecret) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", "user");
        params.add("password", "password");

        ResultActions result
                = mockMvc.perform(post(oauthTokenEndpoint)
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        return result.andReturn().getResponse().getContentAsString();
    }

}
