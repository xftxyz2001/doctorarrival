package com.xftxyz.doctorarrival.gateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xftxyz.doctorarrival.common.helper.JwtHelper;
import com.xftxyz.doctorarrival.common.result.Result;
import com.xftxyz.doctorarrival.common.result.ResultEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Order(0)
@Component
@RequiredArgsConstructor
public class GlobalAuthorizationFilter implements GlobalFilter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final ObjectMapper objectMapper;

    @Value("${admin.authorization}")
    private String adminAuthorization;

    private static final String CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 获取请求路径
        String path = request.getURI().getPath();

        // TODO: 黑白名单

        // 内部服务接口不允许外部访问
        if (antPathMatcher.match("/**/inner/**", path)) {
            return failed(response, ResultEnum.PERMISSION_DENIED);
        }

        // 获取Authorization请求头
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 管理员接口需要管理员权限
        if (antPathMatcher.match("/admin/**", path)) {
            if (!StringUtils.hasLength(authorization) || !authorization.equals(adminAuthorization)) {
                return failed(response, ResultEnum.TOKEN_EXPIRED);
            }
        }

        Long uesrId = null;
        // api接口含有auth的需要认证
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            // 获取用户id
            if (!StringUtils.hasLength(authorization)) {
                return failed(response, ResultEnum.USER_NOT_LOGIN);
            }
            try {
                uesrId = JwtHelper.parseToken(authorization);
            } catch (JWTVerificationException e) {
                return failed(response, ResultEnum.TOKEN_EXPIRED);
            }
        }

        // 将用户id添加到请求头
        if (!ObjectUtils.isEmpty(uesrId)) {
            request.mutate().header(JwtHelper.X_USER_ID, uesrId.toString());
        }
        return chain.filter(exchange);
    }

    private Mono<Void> failed(ServerHttpResponse response, ResultEnum resultEnum) {
        Result<?> result = Result.failed(resultEnum.getCode(), resultEnum.getMessage());
        String body;
        try {
            body = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            body = "";
        }
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE_VALUE);
        return response.writeWith(Mono.just(buffer));
    }
}
