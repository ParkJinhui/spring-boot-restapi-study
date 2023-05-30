package com.example.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class AbstractRestControllerTest {

    protected MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .alwaysDo(print())
                .build();
    }

    protected FieldDescriptor[] getPageableResponse(){
        return new FieldDescriptor[] {
                subsectionWithPath("pageable").description("페이지 정보").type(JsonFieldType.OBJECT),
                subsectionWithPath("sort").description("정렬정보").type(JsonFieldType.OBJECT),

                fieldWithPath("last").description("마지막").type(JsonFieldType.BOOLEAN),
                fieldWithPath("totalElements").description("총 리스트 갯수").type(JsonFieldType.NUMBER),
                fieldWithPath("totalPages").description("총 페이지 수").type(JsonFieldType.NUMBER),
                fieldWithPath("size").description("한페이지에 리스트 갯수").type(JsonFieldType.NUMBER),
                fieldWithPath("number").description("현재").type(JsonFieldType.NUMBER),
                fieldWithPath("first").description("처음 여부").type(JsonFieldType.BOOLEAN),
                fieldWithPath("numberOfElements").description("numberOfElements").type(JsonFieldType.NUMBER),
                fieldWithPath("empty").description("빈 값 여부").type(JsonFieldType.BOOLEAN),
        };
    }


    protected FieldDescriptor[] getFailResponse(){
        return new FieldDescriptor[] {
                fieldWithPath("msg").description("에러에 대한 메세지 값").type(JsonFieldType.STRING)
        };
    }




}
