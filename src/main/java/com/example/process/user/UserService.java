package com.example.process.user;



import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.profileRepository  = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }
    // ADMIN_TOKEN

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

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
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    public List<UserResponseDto> getUserList() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }
    @Transactional
    public ProfileResponseDto getProfile(Long id) {
        User user = findProfile(id);
        return new ProfileResponseDto(user);
    }

    @Transactional
    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto requestDto) {
        User user = findProfile(id);
        user.updateProfile(requestDto);
        return new ProfileResponseDto(user);
    }

    @Transactional
    public User findProfile(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INDEX_NOT_FOUND)
        );
    }

}