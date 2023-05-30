package com.example.common.group;


import com.example.common.group.modules.group.GroupConst;
import com.example.common.group.modules.group.controller.GroupController;
import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.enums.GroupStatus;
import com.example.common.group.modules.group.model.request.GroupSaveRequest;
import com.example.common.group.modules.group.model.request.GroupUpdateRequest;
import com.example.common.group.modules.group.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class GroupMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("페이지 Mock")
    public void getPage() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);

        // SELECT count(1) From group
        // querydsl
        Group group = GroupTestConst.group;
        PageImpl<Group> groupPage = new PageImpl<>(List.of(group), pageRequest, 1);
        given(groupService.getPage(pageRequest)).willReturn(groupPage);

        // {
        //"content":[
            //{
                //"createdDate":null,
                //"modifiedDate":null,
                //"id":1,
                //"name":"그룹",
                //"status":"ACTIVE"
            //}
        //],
        // Pageable 도 해야함
        mockMvc.perform(
            get("/group")
                    .param("page", "0")
                    .param("size", "10")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.groups[0].index").value(group.getId()))
                .andExpect(jsonPath("$._embedded.groups[0].groupName").value(group.getName()))
                .andExpect(jsonPath("$._embedded.groups[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$.page.size").exists())
                .andExpect(jsonPath("$.page.totalElements").exists())
                .andExpect(jsonPath("$.page.totalPages").exists())
                .andExpect(jsonPath("$.page.number").exists())
        ;
    }

    @Test
    @DisplayName("디테일 조회 실패")
    public void getDetailFail() throws Exception {

        given(groupService.getDetail(1L)).willReturn(Optional.empty());

        mockMvc.perform(
                        get("/group/{id}", 1L)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(GroupConst.NotFound))
        ;
    }

    @Test
    @DisplayName("디테일 조회")
    public void getDetail() throws Exception {
        Group group = GroupTestConst.group;
        given(groupService.getDetail(1L)).willReturn(Optional.of(group));

        // {"createdDate":null,"modifiedDate":null,"id":1,"name":"그룹","status":"ACTIVE"}
        mockMvc.perform(
                        get("/group/{id}", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value(group.getId()))
                .andExpect(jsonPath("$.groupName").value(group.getName()))
                .andExpect(jsonPath("$._links.self.href").exists())
        ;
    }


    @Test
    @DisplayName("저장 실패")
    public void getSaveFail() throws Exception {
        GroupSaveRequest groupSaveRequest = new GroupSaveRequest();
        mockMvc.perform(
                        post("/group")
                                .content(objectMapper.writeValueAsString(groupSaveRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(GroupConst.BlankNameMsg))
        ;
    }


    @Test
    @DisplayName("저장 성공")
    public void getSave() throws Exception {
        GroupSaveRequest request = new GroupSaveRequest("그룹이다.");

        // save 결과
        Group group = new Group(1L, request.getName(), GroupStatus.ACTIVE);
        given(groupService.save(request)).willReturn(group);

        mockMvc.perform(
                    post("/group")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value(group.getId()))
                .andExpect(jsonPath("$.groupName").value(group.getName()))
                .andExpect(jsonPath("$._links.self.href").exists());
    }


    @Test
    @DisplayName("수정 실패 (Validation)")
    public void getUpdateValidationFail() throws Exception {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        mockMvc.perform(
                        put("/group/{id}", 0)
                                .content(objectMapper.writeValueAsString(groupUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(GroupConst.BlankNameMsg))
        ;
    }

    @Test
    @DisplayName("수정 실패 Detail 없음")
    public void getUpdateNoDataFoundFail() throws Exception {
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest("변경된 그룹이름");

        given(groupService.getDetail(0L)).willReturn(Optional.empty());
        mockMvc.perform(
                        put("/group/{id}", 0)
                                .content(objectMapper.writeValueAsString(groupUpdateRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(GroupConst.NotFound));
    }


    @Test
    @DisplayName("수정 성공")
    public void getUpdate() throws Exception {
        GroupUpdateRequest request = new GroupUpdateRequest("변경된 그룹이름");

        Group group = new Group(0L, request.getName(), GroupStatus.ACTIVE);

        given(groupService.getDetail(0L)).willReturn(Optional.of(group));
        given(groupService.update(group, request)).willReturn(group);

        mockMvc.perform(
                        put("/group/{id}", 0)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value(group.getId()))
                .andExpect(jsonPath("$.groupName").value(group.getName()))
                .andExpect(jsonPath("$._links.self.href").exists());
    }



    @Test
    @DisplayName("삭제 Detail 없음")
    public void getDeleteNoDataFoundFail() throws Exception {
        given(groupService.getDetail(0L)).willReturn(Optional.empty());

        mockMvc.perform(
                        delete("/group/{id}", 0)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(GroupConst.NotFound));
    }


    @Test
    @DisplayName("삭제 성공")
    public void getDelete() throws Exception {
        given(groupService.getDetail(0L)).willReturn(Optional.of(GroupTestConst.group));

        MvcResult mvcResult = mockMvc.perform(
                        delete("/group/{id}", 0)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        String body = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(body, GroupConst.DeleteSuccess);
    }

}
