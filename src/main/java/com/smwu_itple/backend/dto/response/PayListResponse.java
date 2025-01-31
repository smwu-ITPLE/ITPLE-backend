package com.smwu_itple.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PayListResponse {
    private List<PaySumResponse> paySumList; // 상주별 부의금 총합 & 퍼센트
    private List<PayCreateResponse> payList; // 개별 부의금 목록
}