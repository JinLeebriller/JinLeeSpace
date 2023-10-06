package com.jinleebriller.jinleespace.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity // JPA 엔티티임을 나타내는 어노테이션
@Table(name = "TB_ROLE") //  데이터베이스에서 이 엔터티에 대한 테이블 이름을 지정
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스가 테이블에 레코드를 추가할 때마다 자동으로 증가하는 값을 사용하여 식별자를 생성
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    /*
        roles는 User엔티티의 필드이며, User엔티티가 관계의 주인이고, Role엔티티는 그에 따르는 엔티티 임을 나타낸다.
        roles 필드를 통해 양방향 관계가 설정되어 있다.
    */
    @ManyToMany(mappedBy = "roles")
    // Role과 관련된 User 엔터티들의 집합
    private Set<User> users;

}
