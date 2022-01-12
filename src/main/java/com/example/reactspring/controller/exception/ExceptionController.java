package com.example.reactspring.controller.exception;

import com.example.reactspring.advice.exception.CAuthenticationEntryPointException;
import com.example.reactspring.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entryPoint")
    public CommonResult entryPointException() {
        throw new CAuthenticationEntryPointException();
    }
    
    @GetMapping(value = "/accessDenied")
    public CommonResult accessDeniedException() {
        throw new CAuthenticationEntryPointException();
    }
}
