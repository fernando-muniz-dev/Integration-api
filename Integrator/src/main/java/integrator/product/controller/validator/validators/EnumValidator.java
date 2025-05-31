package integrator.product.controller.validator.validators;

import integrator.product.controller.validator.constraints.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return Stream.of(enumClass.getEnumConstants())
                .map(Enum::name)
                .anyMatch(e -> ignoreCase ? e.equalsIgnoreCase(value) : e.equals(value));
    }
}