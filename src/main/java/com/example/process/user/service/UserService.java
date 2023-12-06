package com.example.process.user.service;


import com.example.process.user.dto.SignupRequestDto;
import com.example.process.user.entity.User;
import com.example.process.user.entity.UserRoleEnum;
import com.example.process.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private UserRoleEnum role = UserRoleEnum.USER;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String introduction = requestDto.getIntroduction();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        if(!checkEmail(email) && !checkNickname(nickname)) {
            checkAdminToken(requestDto);
            User user = new User(username, nickname, password, introduction, email, role);
            userRepository.save(user);
        }
    }

    /**
     * 회원가입시 이메일 중복체크하는 메서드
     */
    public boolean checkEmail(String email) {
        boolean checkedEmailOrNickname = userRepository.existsByEmail(email);
        if(checkedEmailOrNickname)
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        return false;
    }

    /**
     * 회원가입시 닉네임 중복체크하는 메서드
     */
    public boolean checkNickname(String nickname) {
        boolean checkedNickname = userRepository.existsByNickname(nickname);
        if(checkedNickname)
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        return false;
    }

    /**
     * 관리자 토큰 확인 후 권한을 설정해주는 메서드
     * */
    public void checkAdminToken(SignupRequestDto requestDto) {
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN))
                throw new IllegalArgumentException("관리자 토큰이 일치하지 않아 등록이 불가능합니다.");
            role = UserRoleEnum.ADMIN;
        }
    }
}