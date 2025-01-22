package com.smwu_itple.backend.infra.util;

import com.smwu_itple.backend.infra.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static Long getUserIdFromSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return userId;
    }
}
