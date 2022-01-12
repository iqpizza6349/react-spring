package com.example.reactspring.service;

import com.example.reactspring.advice.exception.CEmailLoginFailedException;
import com.example.reactspring.advice.exception.CEmailSignupFailedException;
import com.example.reactspring.advice.exception.CRefreshTokenException;
import com.example.reactspring.advice.exception.CUserNotFoundException;
import com.example.reactspring.config.token.TokenProvider;
import com.example.reactspring.entity.security.token.dto.TokenDto;
import com.example.reactspring.entity.security.token.dto.TokenRequestDto;
import com.example.reactspring.controller.sign.dto.UserLoginRequestDto;
import com.example.reactspring.controller.sign.dto.UserSignUpRequestDto;
import com.example.reactspring.entity.security.token.RefreshToken;
import com.example.reactspring.entity.user.User;
import com.example.reactspring.repository.RefreshTokenRepository;
import com.example.reactspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {

        // 회원 존재 여부
        User user = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(CUserNotFoundException::new);

        // 회원 패스워드 일치 여부
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new CEmailLoginFailedException();
        }

        // AccessToken, RefreshToken 지급
        TokenDto tokenDto = tokenProvider.createTokenDto(user.getAutoId(), user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
                .tokenKey(user.getAutoId())
                .token(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long signUp(UserSignUpRequestDto userSignUpRequestDto) {
        if (userRepository.findByEmail(userSignUpRequestDto.getEmail()).isPresent()) {
            throw new CEmailSignupFailedException();
        }
        return userRepository.save(userSignUpRequestDto.toEntity(passwordEncoder)).getAutoId();
    }

    @Transactional
    public TokenDto reIssue(TokenRequestDto tokenRequestDto) {

        // 만료된 refresh token 에러
        if (tokenProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // Access token 에서 Username(PK) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        // User pk 로 회원 검색 및 리포지토리에서 저장된 refresh token 이 없음
        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenKey(user.getAutoId())
                .orElseThrow(CRefreshTokenException::new);

        // refresh token 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // Access Token, Refresh Token 재발급 및 refresh 토큰 저장
        TokenDto newCreateToken = tokenProvider.createTokenDto(user.getAutoId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreateToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreateToken;
    }

}
