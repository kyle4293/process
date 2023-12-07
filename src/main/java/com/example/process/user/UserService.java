package com.example.process.user;



import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String intro = requestDto.getIntro();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }


        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, intro, role);
        userRepository.save(user);
    }

    public UserResponseDto getProfile(String username) {
        User user = findUser(username);
        return new UserResponseDto(user);
    }

    public UserResponseDto updateProfile(String username, UserRequestDto requestDto) {
        User user = findUser(username);

        user.Update(requestDto);

        return new UserResponseDto(user);
    }

    public ResponseEntity<String> updatePassword(String username, String currentPassword, String newPassword) {
        User user = findUser(username);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            System.out.println("현재 비밀번호 : " + currentPassword);
            System.out.println("저장된 비밀번호 : " + user.getPassword());
            throw new RuntimeException("암호 오류");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        profileRepository.save(user);
        return null;
    }
    private User findUser(String username) {
        return profileRepository.findById(username).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }
}