package com.example.common.group;

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
