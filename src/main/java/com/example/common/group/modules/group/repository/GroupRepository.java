package com.example.common.group.modules.group.repository;

import com.example.common.group.modules.group.enums.GroupStatus;
import com.example.common.group.modules.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByIdAndStatus(Long id, GroupStatus status);
}
