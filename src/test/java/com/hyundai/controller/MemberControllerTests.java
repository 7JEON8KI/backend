package com.hyundai.controller;

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:**/*-context.xml")
@Log4j
public class MemberControllerTests {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    //@Ignore
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/get")
                        .param("id", "b401f866-165b-4575-8af0-6e0fa0629e12"))
                        .andReturn()
                        .getModelAndView()
                        .getModelMap();

    }

    @Ignore
    @Test
    public void testGetUserList() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/getList"))
                        .andReturn()
                        .getModelAndView()
                        .getModelMap()
        );
    }

    //@Ignore
    @Test
    public void testUpdateUser() throws Exception {
        String resultPage =
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/user/update")
                                .param("id", "b401f866-165b-4575-8af0-6e0fa0629e12")
                                .param("nickname", "테스트")
                                .param("imgUrl", "www.www"))
                        .andReturn()
                        .getModelAndView().getViewName();
        log.info(resultPage);
    }
}
