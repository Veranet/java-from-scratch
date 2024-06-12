package halatsiankova.javafromscratch.lesson2.validator;

import java.util.regex.Pattern;

public class CommunicationValidator {
    public void validatePhoneNumber(String phoneNumber) {
        String pattern = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
        Pattern compiledPattern = Pattern.compile(pattern);
        if (!compiledPattern.matcher(phoneNumber).find()) {
            throw new IllegalArgumentException("invalid phone number");
        }
    }

    public void validateEmail(String email) {
        String pattern = "^(.+)@(\\S+)$";
        Pattern compiledPattern = Pattern.compile(pattern);
        if (!compiledPattern.matcher(email).find()) {
            throw new IllegalArgumentException("invalid email");
        }
    }
}
