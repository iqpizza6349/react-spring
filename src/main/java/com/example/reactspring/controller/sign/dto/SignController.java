package com.example.reactspring.controller.sign.dto;

import com.example.reactspring.config.token.TokenProvider;
import com.example.reactspring.entity.security.token.dto.TokenDto;
import com.example.reactspring.entity.security.token.dto.TokenRequestDto;
import com.example.reactspring.model.response.SingleResult;
import com.example.reactspring.repository.UserRepository;
import com.example.reactspring.service.SignService;
import com.example.reactspring.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/sign")
public class SignController {

    private final SignService signService;
    private final ResponseService responseService;

    @PostMapping(value = "/login")
    public SingleResult<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        TokenDto tokenDto = signService.login(userLoginRequestDto);
        return responseService.getSingleResult(tokenDto);
    }
    
    @PostMapping(value = "/signup")
    public SingleResult<Long> signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        Long signUpId = signService.signUp(userSignUpRequestDto);
        return responseService.getSingleResult(signUpId);
    }

    @PostMapping(value = "/reissue")
    public SingleResult<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(signService.reIssue(tokenRequestDto));
    }

}
