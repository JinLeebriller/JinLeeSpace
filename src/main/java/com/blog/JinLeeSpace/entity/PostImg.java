package com.blog.JinLeeSpace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_img")
@Getter @Setter
public class PostImg {

    // 고유 번호
    @Id
    @Column(name="post_idNumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identificationNumber;

}
