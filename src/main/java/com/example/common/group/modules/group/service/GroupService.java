package com.example.common.group.modules.group.service;


import com.example.common.group.modules.group.enums.GroupStatus;
import com.example.common.group.modules.group.entity.Group;
import com.example.common.group.modules.group.model.request.GroupSaveRequest;
import com.example.common.group.modules.group.model.request.GroupUpdateRequest;
import com.example.common.group.modules.group.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Page<Group> getPage(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }
    public Optional<Group> getDetail(Long id) {
        return groupRepository.findByIdAndStatus(id, GroupStatus.ACTIVE);
    }

    @Transactional
    public Group save(GroupSaveRequest request) {
        return groupRepository.save(request.toEntity());
    }

    @Transactional
    public Group update(Group group, GroupUpdateRequest request) {
        group.setName(request.getName());
        return groupRepository.save(group);
    }

    @Transactional
    public void delete(Group group) {
        groupRepository.delete(group);
    }
}
