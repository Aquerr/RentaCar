package io.github.aquerr.rentacar.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.aquerr.rentacar.domain.ApiException;
import io.github.aquerr.rentacar.i18n.MessageService;
import io.github.aquerr.rentacar.web.rest.RestErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Order(1)
@Component
public class ApiExceptionFilter extends OncePerRequestFilter
{
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try
        {
            filterChain.doFilter(request, response);
        }
        catch (Exception exception)
        {
            if (exception.getClass().isAnnotationPresent(ApiException.class))
            {
                ApiException apiException = exception.getClass().getAnnotation(ApiException.class);

                RestErrorResponse restErrorResponse = RestErrorResponse.of(apiException.status().value(), messageService.resolveMessage(apiException.messageKey()));
                response.setStatus(restErrorResponse.getStatus());
                response.getWriter().write(objectMapper.writeValueAsString(restErrorResponse));
            }
            else
            {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write(exception.getMessage());
            }
        }
    }
}
