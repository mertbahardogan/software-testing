package com.software.testing.integration.base;

import static com.software.testing.core.constant.ErrorConstant.E_USER_ALREADY_REGISTERED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@EnableConfigurationProperties
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest extends TestFactory {

    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();

    protected <T> T performPostRequestExpectedSuccess(String path, Object object, Class<T> responseType)
            throws Exception {
        MvcResult mvcResult = getResultActions(path, object)
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return convertStringToClass(mvcResult.getResponse().getContentAsString(), responseType);
    }

    protected <T> T performPostRequestExpectedControllerException(String path, Object object, Class<T> responseType)
            throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorMessage", notNullValue()))
                .andExpect(jsonPath("$.errorMessage", is(E_USER_ALREADY_REGISTERED)))
                .andReturn();
        return convertStringToClass(mvcResult.getResponse().getContentAsString(), responseType);
    }

    private ResultActions getResultActions(String path, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(object)));
    }

    private <T> T convertStringToClass(String jsonString, Class<T> responseType) {
        try {
            return mapper.readValue(jsonString, responseType);
        } catch (JsonProcessingException e) {
            System.out.println("Error has occurred while converting string to the class: " + e);
        }
        return null;
    }
}