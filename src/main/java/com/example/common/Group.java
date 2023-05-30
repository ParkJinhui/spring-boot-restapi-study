package com.example.common;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("그룹 이름")
    private String name;

    @Comment("상태")
    @Enumerated(EnumType.STRING)
    private GroupStatus status;

}
