package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.entity.PostImg;
import com.blog.JinLeeSpace.repository.PostImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

// 포스트 이미지를 업로드하고, 이미지 정보를 저장하는 클래스
@Service
@RequiredArgsConstructor
@Transactional
public class PostImgService {

    @Value("${postImgLocation}")
    private String postImgLocation;

    private final PostImgRepository postImgRepository;

    private final FileService fileService;

    public void savePostImg(PostImg postImg, MultipartFile postImgFile) throws Exception {
        // 업로드 했던 포스트 이미지 파일의 원래 이름
        String oriImgName = postImgFile.getOriginalFilename();
        // 실제 로컬에 저장된 포스트 이미지 파일의 이름
        String imgName = "";
        // 업로드 결과 로컬에 저장된 포스트 이미지 파일을 불러오는 경로
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(postImgLocation, oriImgName, postImgFile.getBytes());
            imgUrl = "/images/img/" + imgName;
        }

        // 포스트 이미지 정보 저장
        postImg.updatePostImg(oriImgName, imgName, imgUrl);
        postImgRepository.save(postImg);
    }
}
