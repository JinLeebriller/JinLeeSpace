package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.dto.PostFormDto;
import com.blog.JinLeeSpace.dto.PostImgDto;
import com.blog.JinLeeSpace.dto.PostSearchDto;
import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.entity.Post;
import com.blog.JinLeeSpace.entity.PostImg;
import com.blog.JinLeeSpace.repository.MemberRepository;
import com.blog.JinLeeSpace.repository.PostImgRepository;
import com.blog.JinLeeSpace.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

// 포스트를 등록하는 클래스
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImgService postImgService;
    private final PostImgRepository postImgRepository;
    private final MemberRepository memberRepository;

    public Long savePost(String memberId, PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws Exception {

        // 포스트 등록
        Post post = postFormDto.createPost();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException(memberId));
        post.setMember(member);
        postRepository.save(post);

        // 이미지 등록
        for (int i = 0; i < postImgFileList.size(); i++) {
            PostImg postImg = new PostImg();
            postImg.setPost(post);
            if (i == 0) {
                postImg.setRepimgYn("Y");
            } else {
                postImg.setRepimgYn("N");
            }
            postImgService.savePostImg(postImg, postImgFileList.get(i));
        }

        return post.getIdNumber();
    }

    // 등록된 포스트를 불러오는 메서드
    @Transactional(readOnly = true)
    public PostFormDto getPostDtl(Long idNumber) {

        // 해당 포스트의 이미지를 조회
        List<PostImg> postImgList = postImgRepository.findByPostIdNumberOrderByIdNumberAsc(idNumber);

        // 조회한 PostImg 엔티티를 PostImgDto 객체로 만들어서 리스트에 추가
        List<PostImgDto> postImgDtoList = new ArrayList<>();
        for (PostImg postImg : postImgList) {
            PostImgDto postImgDto = PostImgDto.of(postImg);
            postImgDtoList.add(postImgDto);
        }

        // 포스트의 idNumber를 통해 포스트 엔티티를 조회
        Post post = postRepository.findByIdNumber(idNumber).orElseThrow(EntityNotFoundException::new);
        PostFormDto postFormDto = PostFormDto.of(post);
        postFormDto.setPostImgDtoList(postImgDtoList);
        return postFormDto;
    }

    // 포스트를 수정하는 메서드
    public Long updatePost(PostFormDto postFormDto, List<MultipartFile> postImgFileList) throws Exception {

        // 포스트 수정
        Post post = postRepository.findByIdNumber(postFormDto.getIdNumber()).orElseThrow(EntityNotFoundException::new);
        post.updatePost(postFormDto);

        // 이미지 등록
        List<Long> postImgIdNumbers = postFormDto.getPostImgIdNumberList();
        for (int i = 0; i < postImgFileList.size(); i++) {
            postImgService.updatePostImg(postImgIdNumbers.get(i), postImgFileList.get(i));
        }

        return post.getIdNumber();
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostPage(PostSearchDto postSearchDto, Pageable pageable) {
        return postRepository.getPostPage(postSearchDto, pageable);
    }

}
