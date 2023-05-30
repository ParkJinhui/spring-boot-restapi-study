package com.example.common.group.modules.group.controller;


import com.example.common.exception.Common400Exception;
import com.example.common.group.modules.group.GroupConst;
import com.example.common.group.modules.group.model.request.GroupSaveRequest;
import com.example.common.group.modules.group.service.GroupService;
import com.example.common.group.modules.group.model.request.GroupUpdateRequest;
import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.model.GroupModel;
import com.example.common.group.modules.group.model.GroupModelAssembler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // code -> 200, message <= 에러코드 ResponseDTO
    @GetMapping
    public ResponseEntity<PagedModel<GroupModel>> getPage(
            Pageable pageable,
            PagedResourcesAssembler<Group> assembler
    ) {

        return ResponseEntity.ok(
                assembler.toModel(
                        groupService.getPage(pageable),
                        new GroupModelAssembler()
                )
        );

    }


    @GetMapping("/{id}")
    public ResponseEntity<GroupModel> getDetail (@PathVariable Long id) {

        Optional<Group> optionalGroup = groupService.getDetail(id);
        if (optionalGroup.isEmpty()) {
            throw new Common400Exception(GroupConst.NotFound);
        }

        return ResponseEntity.ok(
                new GroupModelAssembler().toModel(optionalGroup.get())
        );
    }

    @PostMapping
    public ResponseEntity<GroupModel> save(
            @Valid @RequestBody GroupSaveRequest request,
            Errors errors
    ){

        if (errors.hasErrors()){
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        return ResponseEntity.ok(
            new GroupModelAssembler().toModel(groupService.save(request))
        );

    }


    @PutMapping("/{id}")
    public ResponseEntity<GroupModel> update(
            @PathVariable Long id,
            @Valid @RequestBody GroupUpdateRequest request, Errors errors
    ){

        if (errors.hasErrors()){
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        Optional<Group> optionalGroup = groupService.getDetail(id);
        if (optionalGroup.isEmpty()) {
            throw new Common400Exception(GroupConst.NotFound);
        }

        return ResponseEntity.ok(
                new GroupModelAssembler().toModel(groupService.update(optionalGroup.get(), request))
        );

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        Optional<Group> optionalGroup = groupService.getDetail(id);
        if (optionalGroup.isEmpty()) {
            throw new Common400Exception(GroupConst.NotFound);
        }

        groupService.delete(optionalGroup.get());

        return ResponseEntity.ok(
            GroupConst.DeleteSuccess
        );

    }
}
