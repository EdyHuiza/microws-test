package com.truextend.test.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ResultResponse {
    @Builder.Default
    private Date timestamp = new Date();
    private boolean status;
    private String message;
    private Object data;
}
