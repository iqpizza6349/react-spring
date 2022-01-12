package com.example.reactspring.controller.user;

import com.example.reactspring.entity.user.dto.UserRequestDto;
import com.example.reactspring.entity.user.dto.UserResponseDto;
import com.example.reactspring.model.response.CommonResult;
import com.example.reactspring.model.response.ListResult;
import com.example.reactspring.model.response.SingleResult;
import com.example.reactspring.service.response.ResponseService;
import com.example.reactspring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final ResponseService responseService;
    private final UserService userService;

    @GetMapping(value = "/{autoId}")
    public SingleResult<UserResponseDto> findUserById(@PathVariable Long autoId) {
        return responseService.getSingleResult(userService.findById(autoId));
    }

    @GetMapping(value = "/{email}")
    public SingleResult<UserResponseDto> findUserByEmail(@PathVariable String email) {
        return responseService.getSingleResult(userService.findByEmail(email));
    }

    @GetMapping(value = "/users")
    public ListResult<UserResponseDto> findAllUser() {
        return responseService.getListResult(userService.findAllUser());
    }

    @PutMapping(value = "/modify")
    public SingleResult<Long> update(@RequestBody Long autoId, @RequestBody String nickname) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .nickname(nickname)
                .build();
        return responseService.getSingleResult(userService.update(autoId, userRequestDto));
    }

    @DeleteMapping(value = "/{autoId}")
    public CommonResult delete(@PathVariable Long autoId) {
        userService.delete(autoId);
        return responseService.getSuccessResult();
    }

}
