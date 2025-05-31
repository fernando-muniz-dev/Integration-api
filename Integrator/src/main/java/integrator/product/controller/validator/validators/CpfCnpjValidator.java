package integrator.product.controller.validator.validators;

import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.domain.model.exceptions.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // Se for obrigatório, use @NotBlank/@NotNull também

        String numeric = value.replaceAll("\\D", "");

        if (numeric.length() == 11) {
            if (isValidCPF(numeric)) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF inválido").addConstraintViolation();
            return false;

        } else if (numeric.length() == 14) {
            if (isValidCNPJ(numeric)) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CNPJ inválido").addConstraintViolation();
            return false;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Documento deve conter 11 (CPF) ou 14 (CNPJ) dígitos").addConstraintViolation();
        return false;
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0, weight = 10;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * weight--;
            }

            int r = 11 - (sum % 11);
            char d1 = (r >= 10 ? '0' : (char) (r + '0'));

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * weight--;
            }

            r = 11 - (sum % 11);
            char d2 = (r >= 10 ? '0' : (char) (r + '0'));

            return d1 == cpf.charAt(9) && d2 == cpf.charAt(10);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
            }

            int r = sum % 11;
            char d1 = (r < 2 ? '0' : (char) ((11 - r) + '0'));

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
            }

            r = sum % 11;
            char d2 = (r < 2 ? '0' : (char) ((11 - r) + '0'));

            return d1 == cnpj.charAt(12) && d2 == cnpj.charAt(13);
        } catch (Exception e) {
            return false;
        }
    }
}