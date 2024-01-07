package com.blog.JinLeeSpace.dto;

import com.blog.JinLeeSpace.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

// 포스트 데이터 정보를 전달하는 DTO 클래스

@Getter
@Setter
public class PostFormDto {

    private Long idNumber;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private int viewCount;

    // 포스트 저장 후 수정할 때 포스트 이미지 정보를 저장하는 리스트
    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    // 포스트 이미지 고유번호를 저장하는 리스트
    private List<Long> postImgIdNumberList = new ArrayList<>();

    // modelMapper를 이용하여 엔티티 객체와 DTO 객체간의 데이터를 복사하여 복사한 객체를 반환
    private static ModelMapper modelMapper = new ModelMapper();

    public Post createPost() {
        return modelMapper.map(this, Post.class);
    }

    public static PostFormDto of(Post post) {
        return modelMapper.map(post, PostFormDto.class);
    }
}
