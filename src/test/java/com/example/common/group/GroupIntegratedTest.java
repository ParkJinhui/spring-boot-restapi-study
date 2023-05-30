package com.example.common.group;

import com.example.common.AbstractRestControllerTest;
import com.example.common.group.modules.group.model.request.GroupSaveRequest;
import com.example.common.group.modules.group.model.request.GroupUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
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
                        links(
                            linkWithRel("first").description("나의 현재 페이지"),
                            linkWithRel("prev").description("나의 현재 페이지"),
                            linkWithRel("self").description("나의 현재 페이지"),
                            linkWithRel("next").description("나의 현재 페이지"),
                            linkWithRel("last").description("나의 현재 페이지")
                        ),
                        requestParameters(
                            parameterWithName("page").description("현재 페이지"),
                            parameterWithName("size").description("리스트 사이즈")
                        ),
                        responseFields(
                                subsectionWithPath("_embedded.groups").description("그룹 이름").type(JsonFieldType.ARRAY)
                        )
                        .and(getGroupResponse("_embedded.groups[]."))
                        .and(getPageableResponse())
                        .and(getPageLinkResponse())
                )
        );
    }

    @Test
    public void getDetail() throws Exception{
        mockMvc.perform(
                        get("/group/{id}", 1))

                .andDo(
                        document("group-detail",
                                links(getSelfLink()),
                                pathParameters(
                                    parameterWithName("id").description("그룹의 고유값")
                                ),
                                responseFields(
                                    getGroupResponse("")
                                ).and(getSelfLinkResponse())
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

                                ,links(getSelfLink())
                                , requestFields(
                                    getGroupRequest()
                                )
                                , responseFields (
                                        getGroupResponse("")
                                ).and(getSelfLinkResponse())
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
                                ,links(getSelfLink())
                                , pathParameters(
                                        parameterWithName("id").description("그룹의 고유값")
                                )
                                , requestFields(
                                        getGroupRequest()
                                )
                                , responseFields (
                                        getGroupResponse("")
                                ).and(getSelfLinkResponse())
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
                fieldWithPath(prefix + "index").description("그룹 고유번호").type(JsonFieldType.NUMBER),
                fieldWithPath(prefix + "groupName").description("그룹 이름").type(JsonFieldType.STRING),
        };
    }



}
