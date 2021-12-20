package com.ppjt10.skifriend.controller;


import com.ppjt10.skifriend.dto.CommentDto;
import com.ppjt10.skifriend.dto.FreePostDto;
import com.ppjt10.skifriend.security.UserDetailsImpl;
import com.ppjt10.skifriend.service.FreePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FreePostController {
    private final FreePostService freePostService;

    //region 자유 게시판 게시글 작성
    @PostMapping("/board/{skiResort}/freeBoard")
    //수정 필요할 수도? "" 부분
    public void writeFreePosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String skiResort,
            @ModelAttribute("requestDto") FreePostDto.RequestDto requestDto
    ) {
        freePostService.uploadFreePosts(userDetails, skiResort, requestDto);
    }
    //endregion

    //region 자유 게시판 게시글 상세조회
    @GetMapping("/board/{skiResort}/freeBoard/{postId}")
    public ResponseEntity<FreePostDto.ResponseDto> readFreePost(
            @PathVariable String skiResort,
            @PathVariable Long postId
    ) {
        return freePostService.getFreePost(skiResort, postId);
    }

    //region 자유 게시판 게시글 수정
    @PutMapping("/board/{skiResort}/freeBoard/{postId}")
    public void editFreePost(
            @PathVariable String skiResort,
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute("requestDto") FreePostDto.RequestDto requestDto
    ) {
        freePostService

    }



    //endregion

    //region 자유 게시판 게시글 삭제
    @DeleteMapping("/board/{skiResort}/freeBoard/{postId}")
    public void deleteFreePost(
            @PathVariable String skiResort,
            @PathVariable Long postId
    ) {
        freePostService.deleteFreePost(postId, skiResort);
    }
    //endregion

    //region 자유 게시판 게시글 댓글 작성
    @PostMapping("/board/{skiResort}/freeBoard/{postId}/comments")
    public void writeFreePostComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            CommentDto.RequestDto requestDto,
            @PathVariable String skiResort,
            @PathVariable Long postId
    ) {
        freePostService.writeComment(userDetails, requestDto, skiResort, postId);
    }
    //endregion


    //region 자유 게시판 게시글 댓글 수정
    @PutMapping("/board/{skiResort}/freeBoard/{postId}/comments/{commentId}")
    public void editFreePostComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            CommentDto.RequestDto requestDto,
//            @PathVariable String skiResort,
            @PathVariable Long commentId

    ) {
        freePostService.editComment(userDetails, requestDto, commentId);
    }

    @DeleteMapping("/board/{skiResort}/freeBoard/{postId}/comments/{commentId}")
    public void deleteFreePostComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId
            ) {
        freePostService.deleteComment(userDetails, commentId);
    }

}
