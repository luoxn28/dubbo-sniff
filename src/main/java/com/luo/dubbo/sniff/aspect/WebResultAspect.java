package com.luo.dubbo.sniff.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by xiangnan on 2018/9/26.
 */
@Aspect
@Component
public class WebResultAspect {

    @Around("execution(public * com.luo.dubbo.sniff.controller.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            return WebResultUtil.success(result);
        } catch (Exception e) {
            // 返回异常信息
            return WebResultUtil.error(1, e.getMessage());
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class WebResultBean <T> {
    // 错误码
    private int code;
    // 提示信息
    private String msg;
    // 数据
    private T data;
}

class WebResultUtil {
    static WebResultBean<Object> success(Object data) {
        return new WebResultBean<>(0, "success", data);
    }

    static WebResultBean<Object> error(int code, Object data) {
        return new WebResultBean<>(code, "error", data);
    }
}
