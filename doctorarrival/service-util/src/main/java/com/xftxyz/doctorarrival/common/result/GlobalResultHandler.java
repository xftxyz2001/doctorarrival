package com.xftxyz.doctorarrival.common.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.common.annotation.NoWrap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.xftxyz.doctorarrival")
@RequiredArgsConstructor
public class GlobalResultHandler implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (ResponseEntity.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }
        if (returnType.hasMethodAnnotation(NoWrap.class)) {
            return false;
        }
        if (returnType.getContainingClass().isAnnotationPresent(NoWrap.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 避免对响应体进行二次包装
        if (body instanceof Result) {
            return body;
        }
        // 如果响应体是字符串，需要手动转换
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        // 否则，直接返回响应体
        return Result.success(body);
    }
}
