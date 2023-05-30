package com.example.common;


import com.example.common.exception.Common400Exception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<Page<Group>> getPage(Pageable pageable){
        return ResponseEntity.ok(groupService.getPage(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Group> getDetail (@PathVariable Long id) {
        Optional<Group> optionalGroup = groupService.getDetail(id);
        if (optionalGroup.isEmpty()) {
            throw new Common400Exception(GroupConst.NotFound);
        }
        return ResponseEntity.ok(optionalGroup.get());
    }

    @PostMapping
    public ResponseEntity<Group> save(
            @Valid @RequestBody GroupSaveRequest request,
            Errors errors
    ){
        if (errors.hasErrors()){
            throw new Common400Exception(errors.getFieldErrors().get(0).getDefaultMessage());
        }

        return ResponseEntity.ok(groupService.save(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Group> update(
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

        return ResponseEntity.ok(groupService.update(optionalGroup.get(), request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        Optional<Group> optionalGroup = groupService.getDetail(id);
        if (optionalGroup.isEmpty()) {
            throw new Common400Exception(GroupConst.NotFound);
        }

        groupService.delete(optionalGroup.get());
        return ResponseEntity.ok(GroupConst.DeleteSuccess);
    }

}
