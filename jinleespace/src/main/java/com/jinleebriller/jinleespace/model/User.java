package com.jinleebriller.jinleespace.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 접근 제한자로 매개변수 없는 생성자 생성
@Getter
@Entity // JPA 엔티티임을 나타내는 어노테이션
@Table(name = "TB_USER") // 데이터베이스에서 이 엔티티에 대한 테이블 이름을 지정
@DynamicUpdate // Hibernate 전용 어노테이션으로, 업데이트 중에 동적 SQL 생성을 허용
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    /*
        @ManyToMany : 'User'와 'Role' 간의 다대다 관계를 나타낸다.
        다대다 관계란, 하나의 엔티티가 다수의 다른 엔티티와 관련되어 있고, 역으로 다른 엔티티도 다수의 엔티티와 관련되어 있는 경우이다.
        cascade : 연관된 여러 엔티티들 간에 어떤 작업이 수행되었을 때 그에 따라 어떤 동작을 해야 하는지 정의
        CascadeType.MERGE(병합) : 부모 엔터티가 관련 엔티티를 병합할 때, 해당 연관 엔티티도 함께 병합
        CascadeType.PERSIST(영속) : 부모 엔터티를 저장될 때, 연관된 엔터티도 함께 저장
        CascadeType.REFRESH(갱신) : 부모 엔티티가 갱신할 때, 연관된 엔티티도 함께 갱신

        JoinTable :  JPA에서 다대다 관계를 매핑할 때 사용되는 어노테이션으로, 조인 테이블을 정의할 때 활용
        name속성 : 조인 테이블의 이름을 지정
        joinColumns속성 : 현재 엔티티(User)에 대한 외래 키를 지정
        inverseJoinColumns속성 : 연관 엔티티(Role)에 대한 외래 키를 지정
     */
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="tb_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    // User와 관련된 Role 엔터티들의 집합
    private Set<Role> roles;

    @Builder  // LOMBOK 어노테이션으로, 빌더 패턴을 사용하여 클래스의 인스턴스를 편리하게 생성
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

}
