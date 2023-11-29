package com.blog.JinLeeSpace.dto;

// 포스트 저장 후 포스트 이미지에 대한 데이터를 전달할 DTO 클래스

import com.blog.JinLeeSpace.entity.PostImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostImgDto {

    // 포스트 이미지별 고유 번호
    private Long idNumber;

    // 이미지 파일명
    private String imgName;

    // 원본 이미지 파일명
    private String oriImgName;

    // 이미지 조회 경로
    private String imgUrl;

    // 대표 이미지 여부
    private String repimgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    /*
        PostImg 엔티티 객체를 파라미터로 받아서 PostImg 객체의 자료형과 멤버변수의 이름이 같을 때
        PostImgDto로 값을 복사해서 반환
    */
    public static PostImgDto of(PostImg postImg) {
        return modelMapper.map(postImg, PostImgDto.class);
    }


}
