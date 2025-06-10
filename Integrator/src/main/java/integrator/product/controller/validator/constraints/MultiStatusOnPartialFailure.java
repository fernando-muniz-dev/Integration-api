package integrator.product.controller.validator.constraints;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiStatusOnPartialFailure {
    String value();
}