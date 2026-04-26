package testData.authData;

public final class Credentials {

    private Credentials(){}

    public static final String FIRST_USER =
            System.getenv().getOrDefault(
                    "TEST_FIRST_USER",
                    "standard_user"
            );

    public static final String CORRECT_PASSWORD =
            System.getenv().getOrDefault(
                    "TEST_CORRECT_PASSWORD",
                    "secret_sauce"
            );

    public static final String INCORRECT_LOGIN =
            System.getenv().getOrDefault(
                    "TEST_INCORRECT_LOGIN",
                    "incorrect login"
            );

    public static final String INCORRECT_PASSWORD =
            System.getenv().getOrDefault(
                    "TEST_INCORRECT_PASSWORD",
                    "incorrect password"
            );
}