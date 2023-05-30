package com.example.common.group;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GroupUpdateRequest {

    @NotBlank(message = GroupConst.BlankNameMsg)
    private String name;

}
