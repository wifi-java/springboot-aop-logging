package com.example.logging.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {
  @Autowired
  private WebApplicationContext applicationContext;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void mockMvcSetUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
  }

  @Order(1)
  @Test
  @DisplayName("테스트")
  public void test() throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("name", "테스터");
    jsonObject.put("test", "1234");

    mockMvc.perform(post("/test")
                    .content(jsonObject.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
  }
}