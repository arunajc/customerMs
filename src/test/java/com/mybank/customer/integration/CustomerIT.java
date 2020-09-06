package com.mybank.customer.integration;

import com.mybank.customer.Application;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.path.json.config.JsonPathConfig;
import kafka.utils.Json;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CustomerIT {
    @LocalServerPort
    private int randomPort;

    private String baseUrl;

    @Before
    public void setUp(){
        baseUrl = "http://localhost:"+ randomPort;
    }

    @BeforeClass
    public static void initClass(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;
        RestAssured.config = RestAssured.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
                .redirect(RedirectConfig.redirectConfig().followRedirects(false));
    }


}
