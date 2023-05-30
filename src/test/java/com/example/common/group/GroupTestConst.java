package com.example.common.group;

import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.enums.GroupStatus;

public interface GroupTestConst {

    Group group = new Group(1L, "그룹", GroupStatus.ACTIVE);

}
