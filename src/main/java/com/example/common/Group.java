package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
