package com.order.management.payment.bank;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public class BankControllerTest {

    RestTemplate restTemplate;
    ResponseEntity response;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).httpsPort(8443));

    @Before
    public void setup() throws Exception {
        restTemplate = new RestTemplate();
        response = null;
    }

    @Test
    public void givenWireMockAdminEndpoint_whenGetWithoutParams_thenVerifyRequest() {

        RestTemplate restTemplate = new RestTemplate();
        stubFor(get(urlEqualTo("/customers"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)
                        .withBody("mappings")));

        response = restTemplate.getForEntity("http://localhost:8089/customers", String.class);

        Assertions.assertThat(response.getBody()).isEqualTo("mappings");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenWireMockEndpoint_whenGetWithoutParams_thenVerifyRequest() {
        stubFor(get(urlEqualTo("/customers/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)
                        .withBody("test")));

        response = restTemplate.getForEntity("http://localhost:8089/customers/1", String.class);

        Assertions.assertThat(response.getBody()).isEqualTo("test");
        Assertions.assertThat(response.getStatusCode().equals(HttpStatus.OK));

        verify(getRequestedFor(urlMatching("/customers/.*")));
    }
}
