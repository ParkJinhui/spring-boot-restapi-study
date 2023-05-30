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
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
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
                subsectionWithPath("page").description("페이지 정보").type(JsonFieldType.OBJECT),

                fieldWithPath("page.totalElements").description("총 리스트 갯수").type(JsonFieldType.NUMBER),
                fieldWithPath("page.totalPages").description("총 페이지 수").type(JsonFieldType.NUMBER),
                fieldWithPath("page.size").description("한페이지에 리스트 갯수").type(JsonFieldType.NUMBER),
                fieldWithPath("page.number").description("현재").type(JsonFieldType.NUMBER),
        };
    }

    protected FieldDescriptor[] getSelfLinkResponse(){
        return new FieldDescriptor[] {
                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("내 자신 링크"),
        };
    }

    protected FieldDescriptor[] getPageLinkResponse(){
        return new FieldDescriptor[] {
                fieldWithPath("_links.first.href").type(JsonFieldType.STRING).description("처음 페이지"),
                fieldWithPath("_links.prev.href").type(JsonFieldType.STRING).description("전 페이지"),
                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 페이지"),
                fieldWithPath("_links.next.href").type(JsonFieldType.STRING).description("다음 페이지"),
                fieldWithPath("_links.last.href").type(JsonFieldType.STRING).description("마지막 페이지"),
        };
    }

    protected LinkDescriptor[] getSelfLink(){
        return new LinkDescriptor[] {
                linkWithRel("self").description("나의 현재 페이지")
        };
    }


    protected FieldDescriptor[] getFailResponse(){
        return new FieldDescriptor[] {
                fieldWithPath("msg").description("에러에 대한 메세지 값").type(JsonFieldType.STRING)
        };
    }







}
