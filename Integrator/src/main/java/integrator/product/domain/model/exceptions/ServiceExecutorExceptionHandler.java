package integrator.product.domain.model.exceptions;

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
