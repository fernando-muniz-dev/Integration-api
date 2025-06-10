package integrator.product.controller.controllers;

import integrator.product.controller.response.ApiResponse;
import integrator.product.controller.response.ErrorResponse;
import integrator.product.controller.validator.constraints.MultiStatusOnPartialFailure;
import integrator.product.controller.validator.constraints.SuccessMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

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

        Method method = returnType.getMethod();
        String message = "Sucesso";
        HttpStatus status = HttpStatus.OK;

        if (body instanceof ErrorResponse) {
            return body;
        }

        if (method != null) {
            if (method.isAnnotationPresent(SuccessMessage.class)) {
                message = method.getAnnotation(SuccessMessage.class).value();
            }

            if (method.isAnnotationPresent(MultiStatusOnPartialFailure.class)) {
                StatusDetectionResult detectionResult = detectPartialOrTotalFailure(body, Collections.newSetFromMap(new IdentityHashMap<>()));
                if (detectionResult.allFalse()) {
                    message = method.getAnnotation(MultiStatusOnPartialFailure.class).value();
                    status = HttpStatus.BAD_REQUEST;
                } else if (detectionResult.hasFalse && detectionResult.hasTrue) {
                    status = HttpStatus.MULTI_STATUS;
                }
            }
        }

        response.setStatusCode(status);
        return new ApiResponse<>(status.value(), message, body);
    }

    private StatusDetectionResult detectPartialOrTotalFailure(Object body, Set<Object> visited) {
        if (body == null || visited.contains(body)) return new StatusDetectionResult();

        visited.add(body);

        StatusDetectionResult result = new StatusDetectionResult();

        if (body instanceof Collection<?>) {
            for (Object item : (Collection<?>) body) {
                if (item == null) continue;

                Field successField = getFieldIfExists(item.getClass(), "success");
                if (successField != null) {
                    try {
                        successField.setAccessible(true);
                        Object successValue = successField.get(item);
                        if (Boolean.TRUE.equals(successValue)) {
                            result.hasTrue = true;
                        } else if (Boolean.FALSE.equals(successValue)) {
                            result.hasFalse = true;
                        }
                        if (result.hasTrue && result.hasFalse) return result;
                    } catch (IllegalAccessException ignored) {

                    }
                } else {
                    StatusDetectionResult innerResult = detectPartialOrTotalFailure(item, visited);
                    result.merge(innerResult);
                    if (result.hasTrue && result.hasFalse) return result;
                }
            }
            return result;
        }

        if (isPrimitiveOrWrapper(body.getClass())) {
            return result;
        }

        try {
            for (Field field : body.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(body);

                if (fieldValue == null) continue;

                if (fieldValue instanceof Collection<?>) {
                    StatusDetectionResult innerResult = detectPartialOrTotalFailure(fieldValue, visited);
                    result.merge(innerResult);
                    if (result.hasTrue && result.hasFalse) return result;
                } else if (!isPrimitiveOrWrapper(fieldValue.getClass())) {
                    StatusDetectionResult innerResult = detectPartialOrTotalFailure(fieldValue, visited);
                    result.merge(innerResult);
                    if (result.hasTrue && result.hasFalse) return result;
                }
            }
        } catch (IllegalAccessException e) {
            return result;
        }

        return result;
    }

    private Field getFieldIfExists(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz.equals(String.class) ||
                Number.class.isAssignableFrom(clazz) ||
                Boolean.class.equals(clazz) ||
                Character.class.equals(clazz) ||
                clazz.isEnum() ||
                clazz.getName().startsWith("java.") ||
                clazz.equals(Class.class) ||
                clazz.equals(Package.class);
    }

    private static class StatusDetectionResult {
        boolean hasTrue = false;
        boolean hasFalse = false;

        boolean allFalse() {
            return hasFalse && !hasTrue;
        }

        void merge(StatusDetectionResult other) {
            this.hasTrue |= other.hasTrue;
            this.hasFalse |= other.hasFalse;
        }
    }
}
