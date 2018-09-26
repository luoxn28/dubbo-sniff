package com.luo.dubbo.sniff.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by xiangnan on 2018/9/25.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvokeResult {
    private boolean success;
    private String costTime;
    String result;

    public static InvokeResult success(String result, String costTime) {
        return new InvokeResult(true, costTime, result);
    }

    public static InvokeResult fail(String result) {
        return new InvokeResult(false, "", result);
    }
}
