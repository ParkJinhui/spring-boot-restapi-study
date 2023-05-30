package com.example.common;

import com.example.common.group.GroupSaveRequest;
import com.example.common.group.GroupUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class GroupIntegratedTest extends AbstractRestControllerTest {

    @Test
    public void getPage() throws Exception{
        mockMvc.perform(
                get("/group?page=1&size=1"))
        .andDo(
                document("group-list",
                        requestParameters(
                            parameterWithName("page").description("현재 페이지"),
                            parameterWithName("size").description("리스트 사이즈")
                        ),
                        responseFields(
                                subsectionWithPath("content").description("그룹 이름").type(JsonFieldType.ARRAY)
                        )
                        .and(getGroupResponse("content[]."))
                        .and(getPageableResponse())
                )
        );
    }

    @Test
    public void getDetail() throws Exception{
        mockMvc.perform(
                        get("/group/{id}", 1))

                .andDo(
                        document("group-detail",
                                pathParameters(
                                    parameterWithName("id").description("그룹의 고유값")
                                ),
                                responseFields(
                                        getGroupResponse("")
                                )
                        )

                );
    }

    @Test
    public void getDetailFail() throws Exception{
        mockMvc.perform(
                        get("/group/{id}", 0))
                .andDo(
                        document("group-detail-fail",
                                responseFields(
                                        getFailResponse()
                                )
                        )

                );
    }

    @Test
    public void save() throws Exception {
        GroupSaveRequest groupSaveRequest = new GroupSaveRequest("Sexy 새로운 그룹");

        mockMvc.perform(
                        post("/group")
                            .content(objectMapper.writeValueAsString(groupSaveRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        document("group-save"
                                , requestFields(
                                    getGroupRequest()
                                )
                                , responseFields (
                                        getGroupResponse("")
                                )
                        )

                );
    }

    @Test
    public void saveFail() throws Exception {
        GroupSaveRequest groupSaveRequest = new GroupSaveRequest();

        mockMvc.perform(
                        post("/group")
                                .content(objectMapper.writeValueAsString(groupSaveRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        document("group-save-fail"
                                , responseFields (
                                        getFailResponse()
                                )
                        )
                );
    }


    @Test
    public void update() throws Exception {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest("Sexy 업그레이드");

        mockMvc.perform(
                        put("/group/{id}", 1)
                                .content(objectMapper.writeValueAsString(groupUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        document("group-update"
                                , pathParameters(
                                        parameterWithName("id").description("그룹의 고유값")
                                )
                                , requestFields(
                                        getGroupRequest()
                                )
                                , responseFields (
                                        getGroupResponse("")
                                )
                        )

                );
    }

    @Test
    public void updateFail() throws Exception {

        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest("Sexy 업그레이드");

        mockMvc.perform(
                        put("/group/{id}", 0)
                                .content(objectMapper.writeValueAsString(groupUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        document("group-update-fail"
                                , responseFields (
                                        getFailResponse()
                                )
                        )
                );
    }


    @Test
    public void deleteResponse() throws Exception {
        mockMvc.perform(
                        delete("/group/{id}", 1)
                )
                .andDo(
                        document("group-delete"
                                , pathParameters(
                                        parameterWithName("id").description("그룹의 고유값")
                                )
                        )

                );
    }

    @Test
    public void deleteFail() throws Exception {
        mockMvc.perform(
                        delete("/group/{id}", 0)
                )
                .andDo(
                        document("group-delete-fail"
                                , responseFields (
                                        getFailResponse()
                                )
                        )
                );
    }


    private FieldDescriptor[] getGroupRequest(){
        return new FieldDescriptor[] {
                fieldWithPath("name").description("그룹 이름").type(JsonFieldType.STRING)
        };
    }

    private FieldDescriptor[] getGroupResponse(String prefix){
        return new FieldDescriptor[] {
                fieldWithPath(prefix + "id").description("그룹 고유번호").type(JsonFieldType.NUMBER),
                fieldWithPath(prefix + "name").description("그룹 이름").type(JsonFieldType.STRING),
                fieldWithPath(prefix + "status").description("상태코드[xxx, xxx, xxx]").type(JsonFieldType.STRING),
                fieldWithPath(prefix + "modifiedDate").description("수정날짜").type(JsonFieldType.STRING).optional(),
                fieldWithPath(prefix + "createdDate").description("저장날짜").type(JsonFieldType.STRING).optional()
        };
    }



}
