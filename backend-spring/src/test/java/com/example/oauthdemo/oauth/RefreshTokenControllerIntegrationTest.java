package com.example.oauthdemo.oauth;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:security-test.properties")
public class RefreshTokenControllerIntegrationTest {

    @Value("${security.accessTokenValidity}")
    private int accessTokenValidity;

    @Value("${security.oauth.endpoint}")
    private String oauthTokenEndpoint;

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Autowired
    private MockMvc mockMvc;


	@Test
	public void test_accessTokenValidity_overwrite() {
		assertEquals(1, accessTokenValidity);
	}

	@Test
	public void test_expiredAuthToken() throws Exception {
        JSONObject token = AccessTokenControllerIntegrationTest.getToken(mockMvc, oauthTokenEndpoint, clientId, clientSecret);

        Thread.sleep(1000);

        MvcResult mvcResult = mockMvc.perform(get("/api/resource")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + token.getString(OAuth2AccessToken.ACCESS_TOKEN)))
                .andExpect(status().isUnauthorized()).andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        JSONObject actual = new JSONObject(mockHttpServletResponse.getContentAsString());

        assertTrue(actual.has("error"));
        assertTrue(actual.has("error_description"));
        assertEquals("invalid_token", actual.getString("error"));
        assertTrue(actual.getString("error_description").startsWith("Access token expired"));
	}

	@Test
	public void test_newAuthTokenValid() throws Exception {
        JSONObject token = AccessTokenControllerIntegrationTest.getToken(mockMvc, oauthTokenEndpoint, clientId, clientSecret);
        JSONObject newToken = refreshToken(token);

        assertNotEquals(token.get(OAuth2AccessToken.ACCESS_TOKEN), newToken.get(OAuth2AccessToken.ACCESS_TOKEN));

        mockMvc.perform(get("/api/resource")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + " " + newToken.getString(OAuth2AccessToken.ACCESS_TOKEN)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private JSONObject refreshToken(JSONObject token) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", OAuth2AccessToken.REFRESH_TOKEN);
        params.add(OAuth2AccessToken.REFRESH_TOKEN, token.getString(OAuth2AccessToken.REFRESH_TOKEN));

        ResultActions resultActions = mockMvc.perform(
                post(oauthTokenEndpoint)
                        .params(params)
                        .with(httpBasic(clientId, clientSecret))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        return new JSONObject(resultActions.andReturn().getResponse().getContentAsString());
    }

}

