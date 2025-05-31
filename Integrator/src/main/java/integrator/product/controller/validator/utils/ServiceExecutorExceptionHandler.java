package integrator.product.controller.validator.utils;

import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.model.exceptions.InternalServerErrorException;
import integrator.product.domain.model.exceptions.NotFoundException;
import org.slf4j.Logger;

public class ServiceExecutorExceptionHandler {
    @FunctionalInterface
    public interface Executable<T> {
        T execute();
    }

    public static <T> T execute(Logger logger, String errorMessage, Executable<T> executable) {
        try {
            return executable.execute();
        } catch (NotFoundException | BadRequestException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(errorMessage, e);
            throw new InternalServerErrorException(errorMessage);
        }
    }
}
