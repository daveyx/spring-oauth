package com.example.oauthdemo.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:security-test.properties")
public class RefreshTokenControllerIntegrationTest {

    @Value("${security.accessTokenValidity}")
    private int accessTokenValidity;


	@Test
	public void test_accessTokenValidity_overwrite() {
		assertEquals(1, accessTokenValidity);
	}

}

