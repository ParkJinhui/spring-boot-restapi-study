package com.example.common.group.modules.group.model.request;

import com.example.common.group.modules.group.GroupConst;
import com.example.common.group.modules.group.enums.GroupStatus;
import com.example.common.group.modules.group.entity.Group;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GroupSaveRequest {

    @NotBlank(message = GroupConst.BlankNameMsg)
    private String name;

    public Group toEntity (){
        return new Group(null, name, GroupStatus.ACTIVE);
    }

}
