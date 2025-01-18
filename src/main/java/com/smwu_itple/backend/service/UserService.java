package com.smwu_itple.backend.service;

import com.smwu_itple.backend.domain.User;
import com.smwu_itple.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입
    @Transactional
    public Long join(User user){
        validateDuplicateUser(user); //중복회원 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user){
        Optional<User> optionalUser = userRepository.findByPhone(user.getPhonenumber());
        // 전화번호가 이미 존재하면 예외를 발생시킴
        optionalUser.ifPresent(u -> {
            throw new IllegalStateException("이미 존재하는 전화번호입니다.");
        });
     }

    //로그인
    public User login(String phonenumber, String passwd){
        Optional<User> optionalUser = userRepository.findByPhone(phonenumber);

        // 유저가 없거나 비밀번호가 일치하지 않는 경우 예외 발생
        User user = optionalUser.orElseThrow(() ->
                new IllegalStateException("전화번호 또는 비밀번호가 올바르지 않습니다."));

        if (!user.getPasswd().equals(passwd)) {
            throw new IllegalStateException("전화번호 또는 비밀번호가 올바르지 않습니다.");
        }

        return user; // 로그인 성공 시 사용자 반환
    }

    //회원 조회
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findOne(Long id){
        return userRepository.findOne(id);
    }
}
