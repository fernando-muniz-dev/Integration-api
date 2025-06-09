package integrator.product.controller.controllers;

import integrator.product.controller.response.ApiResponse;
import integrator.product.controller.response.ErrorResponse;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.exceptions.MultiStatusResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@ControllerAdvice
public class ApiResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(ApiResponse.class)
                && !returnType.getParameterType().equals(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        String message = "Sucesso"; // default

        if (body instanceof ErrorResponse) {
            return body;
        }

        Method method = returnType.getMethod();
        if (method != null && method.isAnnotationPresent(SuccessMessage.class)) {
            message = method.getAnnotation(SuccessMessage.class).value();
        }

        HttpStatus status = HttpStatus.OK;

        // Verifica se é uma lista de resultados que podem ter erro
        if (body instanceof List<?> list) {
            boolean hasError = list.stream()
                    .filter(MultiStatusResult.class::isInstance)
                    .map(MultiStatusResult.class::cast)
                    .anyMatch(MultiStatusResult::hasError);

            if (hasError) {
                status = HttpStatus.MULTI_STATUS;
            }
        }

        response.setStatusCode(status);
        return new ApiResponse<>(status.value(), message, body);
    }
}