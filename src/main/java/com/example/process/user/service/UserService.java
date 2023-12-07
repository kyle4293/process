package com.example.process.user.service;


import com.example.process.user.dto.LoginRequestDto;
import com.example.process.user.dto.SignupRequestDto;
import com.example.process.user.entity.User;
import com.example.process.user.entity.UserRoleEnum;
import com.example.process.user.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private UserRoleEnum role = UserRoleEnum.USER;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String introduction = requestDto.getIntroduction();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        if (!checkEmail(email) && !checkNickname(nickname)) {
            checkAdminToken(requestDto);
            User user = new User(username, nickname, password, introduction, email, role);
            userRepository.save(user);
        }
    }

    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(user.getUsername(), user.getRole());
    }

    /**
     * 회원가입시 이메일 중복체크하는 메서드
     */
    private boolean checkEmail(String email) {
        boolean checkedEmailOrNickname = userRepository.existsByEmail(email);
        if (checkedEmailOrNickname)
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        return false;
    }

    /**
     * 회원가입시 이름 중복체크하는 메서드
     */
    private boolean checkNickname(String nickname) {
        boolean checkedNickname = userRepository.existsByUsername(nickname);
        if (checkedNickname)
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        return false;
    }

    /**
     * 관리자 토큰 확인 후 권한을 설정해주는 메서드
     */
    private void checkAdminToken(SignupRequestDto requestDto) {
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN))
                throw new IllegalArgumentException("관리자 토큰이 일치하지 않아 등록이 불가능합니다.");
            role = UserRoleEnum.ADMIN;
        }
    }
}