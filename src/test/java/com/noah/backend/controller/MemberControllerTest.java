package com.noah.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noah.backend.member.controller.MemberController;
import com.noah.backend.member.dto.MemberDto;
import com.noah.backend.member.service.LoginService;
import com.noah.backend.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.noah.backend.fixture.MemberFixture.*;
import static com.noah.backend.member.controller.MemberController.MEMBER_API_URI;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(WebApplicationContext applicationContext,
               RestDocumentationContextProvider contextProvider) {

        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(documentationConfiguration(contextProvider))
                .build();
    }

    private String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("회원가입에 성공할 경우 HTTP 상태코드 200을 반환한다.")
    void successToRegistrationMember() throws Exception {

        doNothing().when(memberService).registrationMember(NEW_MEMBER);

        mockMvc.perform(post(MEMBER_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(MEMBER_REGISTRATION_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("members/create/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("로그인시 사용할 사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("하나 이상의 대소문자, 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("사용자의 닉네임")
                        )
                ));
    }

    @Test
    @DisplayName("중복된 이메일이 존재할 경우 HTTP 상태코드 409를 반환한다.")
    void failToRegistrationMemberByDuplicatedEmail() throws Exception {

        when(memberService.isDuplicatedEmail(anyString())).thenReturn(true);

        mockMvc.perform(post(MEMBER_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(MEMBER_REGISTRATION_REQUEST)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andDo(document("members/create/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("로그인시 사용할 사용자 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING)
                                        .description("하나 이상의 대소문자, 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("사용자의 닉네임")
                        )
                ));

    }

    @Test
    @DisplayName("이메일 중복검사에 성공하여 중복된 이메일이 없으면 HTTP 상태코드 200 반환한다.")
    void isNotExistDuplicatedEmail() throws Exception {

        when(memberService.isDuplicatedEmail(UNIQUE_MEMBER_EMAIL)).thenReturn(false);

        mockMvc.perform(get(MEMBER_API_URI + "/duplicated/{email}", UNIQUE_MEMBER_EMAIL))
                .andExpect(status().isOk())
                .andDo(document("members/duplicatedEmail/success",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("email")
                                        .description("로그인시 사용할 사용자 이메일")
                        )
                ));
    }

    @Test
    @DisplayName("이메일 중복검사에 실패하여 중복된 이메일이 존재하면 HTTP 상태코드 409를 반환한다.")
    void isExistDuplicatedEmail() throws Exception {
        when(memberService.isDuplicatedEmail(DUPLICATED_MEMBER_EMAIL)).thenReturn(true);

        mockMvc.perform(get(MEMBER_API_URI + "/duplicated/{email}", DUPLICATED_MEMBER_EMAIL))
                .andExpect(status().isConflict())
                .andDo(document("members/duplicatedEmail/fail",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("email")
                                        .description("로그인시 사용할 사용자 이메일")
                        )
                ));
    }


    @Test
    @DisplayName("사용자 프로필 조회에 성공하면 HTTP 상태코드 200과 사용자 프로필 정보를 반환한다.")
    void successToGetMemberProfile() throws Exception {

        when(loginService.getLoginMemberId()).thenReturn(MEMBER_UNIQUE_ID);
        when(loginService.getLoginMember()).thenReturn(MEMBER1);

        mockMvc.perform(get(MEMBER_API_URI + "/my-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(MEMBER1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("members/my-profile/success",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("로그인시 사용한 사용자 이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("사용자의 닉네임")
                        )
                ));
    }


    @Test
    @DisplayName("로그인 상태가 아니면 프로필 조회에 실패하고 HTTP 상태코드 401을 반환한다.")
    void failToGetMemberProfile() throws Exception {

        when(loginService.getLoginMemberId()).thenReturn(null);

        mockMvc.perform(get(MEMBER_API_URI + "/my-profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(MEMBER1)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andDo(document("members/my-profile/fail"));
    }
}
