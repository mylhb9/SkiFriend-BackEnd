package com.ppjt10.skifriend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppjt10.skifriend.dto.SignupDto;
import com.ppjt10.skifriend.dto.UserDto;
import lombok.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private String token = "";

    private SignupDto.RequestDto user1 = SignupDto.RequestDto.builder()
            .username("beomin12")
            .nickname("버민")
            .password("asdf12!!")
            .phoneNum("01078945321")
            .build();

    private TestLoginDto user1Login = TestLoginDto.builder()
            .username("beomin12")
            .password("asdf12!!")
            .build();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    @DisplayName("회원 가입")
    void test1() throws JsonProcessingException {
        // given
        String requestBody = objectMapper.writeValueAsString(user1);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<Object> response = restTemplate.postForEntity(
                "/user/signup",
                request,
                Object.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("로그인, JWT 토큰 받기")
    void test2() throws JsonProcessingException {
        // given
        String requestBody = objectMapper.writeValueAsString(user1Login);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<Object> response = restTemplate.postForEntity(
                "/user/login",
                request,
                Object.class);

        // then
        token = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    @Order(3)
    @DisplayName("유저 정보 가져오기")
    void test3() throws JsonProcessingException {

        headers.set("Authorization", token);
        HttpEntity<Object> request = new HttpEntity<>(headers);

        // when
        ResponseEntity<Object> response = restTemplate.exchange( "/user/info", HttpMethod.GET, request, Object.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    @DisplayName("유저 프로필 작성")
    void test4() throws Exception {
        headers.set("Authorization", token);
        HttpEntity<Object> request = new HttpEntity<>(headers);


        String content = objectMapper.writeValueAsString(new UserDto.ProfileRequestDto("남","10대", "초보", "hihihi"));
        MockMultipartFile file1 = new MockMultipartFile("profileImg", "empty.txt", "text/plain", "".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("vacImg", "empty.txt", "text/plain", "".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("requestDto", "jsondata", "application/json", content.getBytes(StandardCharsets.UTF_8));

        ResultActions resultActions = mockMvc.perform(multipart("/user/profile")
                        .file(file1)
                        .file(file2)
                        .file(file3).headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print());

        System.out.println(resultActions);
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class TestLoginDto {
        private String username;
        private String password;
    }


}
