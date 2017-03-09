package com.feerlaroc.mqasho.rx.formvalidation;

import android.support.annotation.NonNull;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jay Rambhia on 5/26/16.
 */
public class ValidationUtils {

    private static String MOBILE_NUMBER_REGEX = "^[ ()+]*([0-9][ ()+]*){10}$";
    private static String TEXT_WITH_MOBILE_NUMBER_REGEX = ".*[7-9][0-9]{9}.*";
    private static String TEXT_WITH_EMAIL_ADDRESS_REGEX = ".*[a-zA-Z0-9\\+\\" +
            ".\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9]{1,64}\\.[a-zA-Z0-9]{1,25}.*";

    private static String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z._0-9]{2,19}$";
    private static String TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX = ".*[0-9]{5,}.*";

    private static String PERSON_NAME_REGEX = "[a-zA-Z][a-zA-Z ]*";

    private static String ZAID_NUMBER = "(((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]" +
            "|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-) " +
            "(\\d{4})( |-)(\\d{3})|(\\d{7}))";

    public static boolean isValidMobileNumber(String number) {
        Pattern mPattern = Pattern.compile(MOBILE_NUMBER_REGEX);
        Matcher matcher = mPattern.matcher(number);
        return matcher.find();
    }

    public static boolean isZAID(String number) {
        Pattern mPattern = Pattern.compile(ZAID_NUMBER);
        Matcher matcher = mPattern.matcher(number);
        return matcher.find();
    }

    public static ValidationResult<String> isValidPersonName(String personname) {
        if (personname.isEmpty()) {
            return ValidationResult.failure(null, personname);
        }

        if (personname.length() < 3) {
            return ValidationResult.failure("Legama lifashane kakhulu!", personname);
        }

        StringTokenizer st = new StringTokenizer(personname);
        int _number = st.countTokens();

        if(_number < 2){
            return ValidationResult.failure("Faka igama nesibongo!", personname);
        }

        Pattern mPattern = Pattern.compile(PERSON_NAME_REGEX);
        Matcher matcher = mPattern.matcher(personname);
        boolean isValid = matcher.find();

        if (isValid) {
            return ValidationResult.success(personname);
        }

        return ValidationResult.failure("Faka ama alphabet kuphela", personname);
    }

    public static boolean containsFourConsecutiveNumbers(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_FOUR_CONSECUTIVE_NUMBERS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static boolean containsMobileNumber(String text) {
        Pattern mPattern = Pattern.compile(TEXT_WITH_MOBILE_NUMBER_REGEX);
        Matcher matcher = mPattern.matcher(text);
        return matcher.find();
    }

    public static ValidationResult<String> isValidEmailAddress(@NonNull String text) {
        if (text.isEmpty()) {
            return ValidationResult.failure(null, text);
        }

        Pattern mPattern = Pattern.compile(TEXT_WITH_EMAIL_ADDRESS_REGEX);
        Matcher matcher = mPattern.matcher(text);
        boolean isValid = matcher.find();

        if (isValid) {
            return ValidationResult.success(text);
        }

        return ValidationResult.failure("Please enter correct email address", text);
    }

}
