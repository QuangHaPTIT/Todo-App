package utils;


public class UserUtils {
	public static final String BuildUsername(String firstName, String lastName) {
		boolean isFirstEmpty = (firstName == null || firstName.trim().isEmpty());
        boolean isLastEmpty = (lastName == null || lastName.trim().isEmpty());
        
        if (isFirstEmpty && isLastEmpty) {
            return "DEFAULT_NAME";
        }

        if (isFirstEmpty) {
            return lastName.trim().toLowerCase();
        }

        if (isLastEmpty) {
            return firstName.trim().toLowerCase();
        }

        return (firstName.trim() + " " + lastName.trim()).toLowerCase();
	}
}
