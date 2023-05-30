package com.example.common.group;

import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.repository.GroupRepository;
import com.example.common.group.modules.group.enums.GroupStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    private final String name = "신규그룹";
    private final GroupStatus status = GroupStatus.ACTIVE;


    @Test
    @DisplayName("진짜 저장이 되었는지 결과값을 비교해서 확인")
    void save (){
        Group group = saveGroup();

        Assertions.assertEquals(group.getName(), name);
        Assertions.assertEquals(group.getStatus(), status);
    }

    @Test
    @DisplayName("전체 내역 조회")
    void findAll (){
        saveGroup();
        List<Group> groups = groupRepository.findAll();
        Assertions.assertNotEquals(groups.size(), 0);
    }

    @Test
    @DisplayName("ID 조회")
    void findId(){
        Group group = saveGroup();
        Optional<Group> optional = groupRepository.findById(group.getId());

        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    @DisplayName("ID 조회 + Status 까지")
    void findIdAndStatus() {
        Group group = saveGroup();
        Optional<Group> optional = groupRepository.findByIdAndStatus(group.getId(), GroupStatus.ACTIVE);

        Assertions.assertTrue(optional.isPresent());


        Optional<Group> optional2 = groupRepository.findByIdAndStatus(group.getId(), GroupStatus.DELETE);

        Assertions.assertTrue(optional2.isEmpty());
    }



    @Test
    @DisplayName("업데이트 완료 -> 삭제는 DeleteStatus 변경")
    void updateAndDelete (){
        Group group = saveGroup();

        group.setName("변경된 아이디 값");
        group.setStatus(GroupStatus.DELETE);
        Group update = groupRepository.save(group);

        Assertions.assertEquals(group.getId(), update.getId());
        Assertions.assertEquals(update.getName(), "변경된 아이디 값");
        Assertions.assertEquals(update.getStatus(), GroupStatus.DELETE);

    }

    private Group saveGroup(){
        return groupRepository.save(
          new Group(null, name, status)
        );
    }


}
