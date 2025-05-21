package Adoption_Management_Package;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    Matcher matcher;

    public boolean nameValidation(String word) {
        Pattern wordPattern = Pattern.compile("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
        matcher = wordPattern.matcher(word);

        if (matcher.matches()) {
            return true;
        } else
            return false;
    }
    public boolean addressValidation(String word) {
        Pattern wordPattern = Pattern.compile("^[\\w\\s.,-]{5,}$");
        matcher = wordPattern.matcher(word);

        if (matcher.matches()) {
            return true;
        } else
            return false;
    }
    public boolean occupationAndHomeTypeValidation(String word) {
        Pattern wordPattern = Pattern.compile("^[a-zA-Z\\s'-]+$");
        matcher = wordPattern.matcher(word);

        if (matcher.matches()) {
            return true;
        } else
            return false;
    }

    public boolean contactNumberValidation(String contactNumber) {
        Pattern contactNumberPattern = Pattern.compile("^(\\+?\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$");
        matcher = contactNumberPattern.matcher(contactNumber);

        if (matcher.matches()) {
            return true;
        } else
            return false;
    }

    public boolean emailValidation(String email) {
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        matcher = emailPattern.matcher(email);

        if (matcher.matches()) {
            return true;
        } else
            return false;
    }


}
