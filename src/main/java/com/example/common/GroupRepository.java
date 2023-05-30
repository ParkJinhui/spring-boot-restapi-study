package com.example.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByIdAndStatus(Long id, GroupStatus status);
}
