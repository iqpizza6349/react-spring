package com.example.reactspring.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    /**
     * @apiNote 응답 성공 여부: boolean
     */
    private boolean success;

    /**
     * @apiNote 응답 반환 코드: int
     * @apiNote code > 0: successful
     * @apiNote code == 0: deprecated or bugs
     * @apiNote code < 0: not-successful
     */
    private int code;

    /**
     * @apiNote 응답 반환 메시지: String
     */
    private String msg;

}
