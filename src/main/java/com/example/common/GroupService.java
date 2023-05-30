package com.example.common;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    public Page<Group> getPage(Pageable pageable) {
        return null;
    }
    public Optional<Group> getDetail(Long id) {
        return null;
    }

    public Group save(GroupSaveRequest request) {
        return null;
    }

    public Group update(Group group, GroupUpdateRequest request) {
        return null;
    }

    public void delete(Group id) {

    }
}
