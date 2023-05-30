package com.example.common.group.modules.group.model;

import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.controller.GroupController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GroupModelAssembler extends RepresentationModelAssemblerSupport<Group, GroupModel> {
    public GroupModelAssembler() {
        super(GroupController.class, GroupModel.class);
    }

    @Override
    public GroupModel toModel(Group entity) {
        GroupModel groupModel = new GroupModel(entity);
        groupModel.add(linkTo(methodOn(GroupController.class).getDetail(entity.getId())).withSelfRel());
        return groupModel;
    }

    @Override
    public CollectionModel<GroupModel> toCollectionModel(Iterable<? extends Group> entities) {
        return super.toCollectionModel(entities);
    }
}
