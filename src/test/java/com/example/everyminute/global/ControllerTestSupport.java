package com.example.everyminute.global;


import com.example.everyminute.global.resolver.LoginResolver;
import com.example.everyminute.global.utils.JwtUtil;
import com.example.everyminute.news.controller.NewsController;
import com.example.everyminute.news.service.NewsService;
import com.example.everyminute.school.controller.SchoolController;
import com.example.everyminute.school.service.SchoolService;
import com.example.everyminute.subscribe.controller.SubscribeController;
import com.example.everyminute.subscribe.service.SubscribeService;
import com.example.everyminute.user.controller.UserController;
import com.example.everyminute.user.entity.User;
import com.example.everyminute.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
        controllers = {
                UserController.class,
                SchoolController.class,
                SubscribeController.class,
                NewsController.class,
        }
)
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTestSupport {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @MockBean protected LoginResolver loginResolver;
    @MockBean protected JwtUtil jwtUtil;
    @MockBean protected User user;
    @MockBean protected UserService userService;
    @MockBean protected SchoolService schoolService;
    @MockBean protected SubscribeService subscribeService;
    @MockBean protected NewsService newsService;
}
