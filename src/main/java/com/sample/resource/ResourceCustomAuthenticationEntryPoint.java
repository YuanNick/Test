package com.sample.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ResourceCustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        // TODO log原始錯誤訊息

        ErrorInfo errorInfo = new ErrorInfo("OAuth-Resource-0001", "The access token is invalid or has expired.");
        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorInfoJson);
    }

}
