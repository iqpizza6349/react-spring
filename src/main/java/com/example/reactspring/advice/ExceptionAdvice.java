package com.example.reactspring.advice;

import com.example.reactspring.advice.exception.*;
import com.example.reactspring.model.response.CommonResult;
import com.example.reactspring.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.info(String.valueOf(e));
        return responseService.getFailResult(
                -1, "알 수 없는 오류가 발생했습니다."
        );
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                -110, "존재하지 않는 회원입니다."
        );
    }

    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected CommonResult emailLoginFailedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                -111, "가입하지 않은 아이디(email)입니다."
        );
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult authenticationEntryPointException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                -120, "전달된 JWT 토큰이 비정상적입니다."
        );
    }

    @ExceptionHandler(CRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult refreshTokenException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                -122, "refresh 토큰에 오류가 발생했습니다."
        );
    }

    @ExceptionHandler(CEmailSignupFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult emailSignUpFailedException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                -109, "이미 가입된 아이디(email)입니다."
        );
    }
}
