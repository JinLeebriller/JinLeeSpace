package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.dto.MemberFormDto;
import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.entity.PostImg;
import com.blog.JinLeeSpace.repository.MemberRepository;
import com.blog.JinLeeSpace.repository.PostImgRepository;
import com.blog.JinLeeSpace.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostImgRepository postImgRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "C:/Users/hp/Desktop/JinLeeSpace/postImg/Img";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setId("test01");
        memberFormDto.setPassword("12345");
        memberFormDto.setNickName("테스트아이디01");
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setAddress("서울시 마포구 합정동");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("포스트 등록 테스트")
    void savePost() throws Exception {
        PostFormDto postFormDto = new PostFormDto();
        postFormDto.setTitle("테스트 포스트");
        postFormDto.setContent("테스트 포스트 입니다.");

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Member member = createMember();
        memberRepository.save(member);

        Long postIdNumber = postService.savePost(member.getId(), postFormDto, multipartFileList);

        List<PostImg> postImgList = postImgRepository.findByPostIdNumberOrderByIdNumberAsc(postIdNumber);
        Post post = postRepository.findById(postIdNumber).orElseThrow(EntityNotFoundException::new);

        assertEquals(postFormDto.getTitle(), post.getTitle());
        assertEquals(postFormDto.getContent(), post.getContent());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), postImgList.get(0).getOriImgName());
    }
}