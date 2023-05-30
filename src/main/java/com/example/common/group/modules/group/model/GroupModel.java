package com.example.common.group.modules.group.model;


import com.example.common.group.modules.group.entity.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "group", collectionRelation = "groups")
@Getter @Setter
public class GroupModel extends RepresentationModel<GroupModel> {

    private Long index;
    private String groupName;

    public GroupModel(Group group) {
        this.index = group.getId();
        this.groupName = group.getName();
    }
}
