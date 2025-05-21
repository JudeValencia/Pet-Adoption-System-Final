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

    // TODO: Create other validation methods for other fields
}
