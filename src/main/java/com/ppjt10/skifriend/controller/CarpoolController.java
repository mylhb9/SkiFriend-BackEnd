package com.ppjt10.skifriend.controller;

import com.ppjt10.skifriend.entity.User;
import com.ppjt10.skifriend.security.UserDetailsImpl;
import com.ppjt10.skifriend.service.CarpoolService;
import com.ppjt10.skifriend.dto.CarpoolDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CarpoolController {
    private final CarpoolService carpoolService;

    //카풀 게시물 조회
    //skiResort = 스키장 이름
    @GetMapping("/board/carpool/{skiResort}")
    public ResponseEntity<List<CarpoolDto.ResponseDto>> getCarpools(@PathVariable String skiResort,
                                                                    @RequestParam int page,
                                                                    @RequestParam int size
    ) {
        page = page - 1;
        List<CarpoolDto.ResponseDto> responseDtoList = carpoolService.getCarpools(skiResort, page, size);
        return ResponseEntity.ok()
                .body(responseDtoList);
    }

    //카풀 게시물 작성
    @PostMapping("/board/carpool/{skiResort}")
    public void createCarpool(@PathVariable String skiResort,
                              @RequestBody CarpoolDto.RequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        carpoolService.createCarpool(skiResort, requestDto, user);
    }

    //카풀 게시뭏 수정
    @PutMapping("/board/carpool/{carpoolId}")
    public void updateCarpool(@PathVariable Long carpoolId,
                              @RequestBody CarpoolDto.RequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        carpoolService.updateCarpool(carpoolId, requestDto, user.getId());
    }

    //카풀 게시글 삭제
    @DeleteMapping("/board/carpool/{carpoolId}")
    public void deleteCarpool(@PathVariable Long carpoolId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        carpoolService.deleteCarpool(carpoolId, user.getId());
    }

    // 카풀 모집 완료 기능
    @PostMapping("/board/carpool/{carpoolId}/status")
    public void changeStatus(@PathVariable Long carpoolId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        carpoolService.changeStatus(carpoolId, userDetails.getUser().getId());
    }

    //region 카풀 카테고리 분류
    @GetMapping("/board/carpool/category")
    public ResponseEntity<Page<CarpoolDto.ResponseDto>> sortCategories(
            @RequestBody CarpoolDto.CategoryRequestDto categoryRequestDto,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        page = page - 1;
        return carpoolService.sortCarpools(categoryRequestDto, page, size);
    }
    //endregion
}
