package com.example.HealthX;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationServerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Flavius", password = "password")
    void generateTokenWithClientCredentials() throws Exception {

        // POST catre /oauth2/token cu client_credentials grant type
        MvcResult result = mockMvc.perform(post("/oauth2/token")
                        .with(httpBasic("client-id", "secret"))
                        .param("grant_type", "client_credentials")
                        .param("scope", "PROJECT"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String accessToken = JsonPath.read(responseBody, "$.access_token");

        assertThat(accessToken).isNotBlank();
    }

    @Test
    @WithMockUser(username = "Flavius", password = "password")
    public void generateTokenWithPasswordGrant() throws Exception {
        MvcResult authorizeUserResult = mockMvc.perform(post("/oauth2/authorize")
                        .param("client_id", "client-id")
                        .param("response_type", "code")
                        .param("scope", "PROJECT")
                        .param("redirect_uri", "http://localhost:8080/oauth2/code/client-id"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirectUrl = authorizeUserResult.getResponse().getRedirectedUrl();
        assertThat(redirectUrl).isNotBlank();

        String code = redirectUrl.split("code=")[1].split("&")[0];
        assertThat(code).isNotBlank();

        MvcResult result = mockMvc.perform(post("/oauth2/token")
                .with(httpBasic("client-id", "secret"))
                        .param("grant_type", "authorization_code")
                        .param("code", code)
                        .param("redirect_uri", "http://localhost:8080/oauth2/code/client-id"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String accessToken = JsonPath.read(responseBody, "$.access_token");

        assertThat(accessToken).isNotBlank();
    }

}
