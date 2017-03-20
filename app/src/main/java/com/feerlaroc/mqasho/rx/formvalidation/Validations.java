package com.feerlaroc.mqasho.rx.formvalidation;

import android.support.annotation.NonNull;

/**
 * Created by root on 2017/01/26.
 */

public class Validations {

    public static ValidationResult validatePhone(@NonNull String phone) {

        if (phone.isEmpty()) {
            return ValidationResult.failure(null, phone);
        }

        boolean isValid = ValidationUtils.isValidMobileNumber(phone);
        if (isValid) {
            return ValidationResult.success(phone);
        }

        return ValidationResult.failure("Phone should be exactly 10 numbers", phone);
    }

    public static ValidationResult<String> validateZAID(@NonNull String zaid) {

        if (zaid.isEmpty()) {
            return ValidationResult.failure(null, zaid);
        }

        boolean isValid = ValidationUtils.isZAID(zaid);
        if (isValid) {
            return ValidationResult.success(zaid);
        }

        return ValidationResult.failure("Phone should be exactly 13 numbers", zaid);
    }

    public static ValidationResult<String> validateEmail(@NonNull String email) {

        return ValidationUtils.isValidEmailAddress(email);
    }

    public static ValidationResult<String> validatePerson(@NonNull String name) {

        return ValidationUtils.isValidPersonName(name);
    }

    public static ValidationResult<String> validateGender(@NonNull String zaid) {

        return null;
    }

}
