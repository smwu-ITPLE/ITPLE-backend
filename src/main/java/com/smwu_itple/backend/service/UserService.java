package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.Owner;
import com.smwu_itple.backend.domain.User;
import com.smwu_itple.backend.dto.request.UserLoginRequest;
import com.smwu_itple.backend.dto.request.UserSignupRequest;
import com.smwu_itple.backend.dto.response.LateSimpleResponse;
import com.smwu_itple.backend.dto.response.UserLoginResponse;
import com.smwu_itple.backend.dto.response.UserProfileResponse;
import com.smwu_itple.backend.repository.OwnerRepository;
import com.smwu_itple.backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    //회원가입
    @Transactional
    public void join(UserSignupRequest userSignupRequest) {
        User user = new User();
        user.setName(userSignupRequest.getName());
        user.setPhonenumber(userSignupRequest.getPhonenumber());
        user.setPasswd(userSignupRequest.getPasswd());
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    // 중복 회원 검증
    private void validateDuplicateUser(User user) {
        if (userRepository.findByPhonenumber(user.getPhonenumber()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 전화번호입니다.");
        }
    }

    //로그인
    public UserLoginResponse login(UserLoginRequest userLoginRequest, HttpSession session) {
        User user = userRepository.findByPhonenumberAndPasswd(
                userLoginRequest.getPhonenumber(), userLoginRequest.getPasswd()
        ).orElseThrow(() -> new IllegalStateException("전화번호 또는 비밀번호가 올바르지 않습니다."));

        // 세션에 사용자 ID 저장
        session.setAttribute("userId", user.getId());

        // UserLoginResponse 생성 및 반환
        return new UserLoginResponse(user.getId(), user.getName(), user.getPhonenumber());
    }

    // 로그아웃
    @Transactional
    public boolean logout(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            session.invalidate();
            return true;
        }
        return false;
    }

    // 사용자 조회
    public UserProfileResponse getProfile(Long userId) {
        User user =  findUserByIdOrThrow(userId);
        return new UserProfileResponse(user.getName());
    }

    public List<LateSimpleResponse> getLatelist(Long userId){
        List<Owner> owners = ownerRepository.findByUserId(userId);
        return owners.stream()
                .map(owner -> new LateSimpleResponse(owner.getLate().getId(), owner.getLate().getName()))
                .collect(Collectors.toList());
    }

    public User findUserByIdOrThrow(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
