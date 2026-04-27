package testData.errorMessages;


public final class ErrorMessage {
    private static final String INCORRECT_DATA_ERROR = "Username and password do not match any user in this service.";

    public static String showAuthErrorWithIncorrectData() {
        return INCORRECT_DATA_ERROR;
    }
}
