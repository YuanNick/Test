package com.sample.authorization;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthorizationCustomErrorHandler {

    private final ObjectMapper objectMapper;

    public void handle(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        // TODO log原始錯誤訊息

        ErrorInfo errorInfo = buildErrorInfo(exception);
        String errorInfoJson = objectMapper.writeValueAsString(errorInfo);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(errorInfoJson);
    }

    private ErrorInfo buildErrorInfo(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException ex) {
            String code = ex.getError().getErrorCode();
            if ("invalid_client".equals(code)) {
                return new ErrorInfo("OAuth-Authorization-0002",
                        "The client information extracted from the token is incorrect.");
            }
        }

        return new ErrorInfo("OAuth-Authorization-0001",
                "The token format is invalid or cannot be parsed.");
    }


}
