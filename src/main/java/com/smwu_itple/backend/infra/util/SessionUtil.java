package com.smwu_itple.backend.infra.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static Long getUserIdFromSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        return userId;
    }
}
