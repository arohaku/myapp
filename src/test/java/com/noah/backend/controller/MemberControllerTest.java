package com.noah.backend.controller;

import com.noah.backend.member.controller.MemberController;
import com.noah.backend.member.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

}