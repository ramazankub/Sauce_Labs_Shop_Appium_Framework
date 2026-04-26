package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LoginPage;
import pages.MainPage;
import testData.authData.Credentials;

import java.util.stream.Stream;


public class LoginTest extends BaseTest{
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

   @Test
    public void successLogin() {
        loginPage.login(Credentials.FIRST_USER, Credentials.CORRECT_PASSWORD);
        mainPage.checkIsOpened();

    }

    @Test
    public void incorrectLogin() {
        loginPage.login(Credentials.INCORRECT_LOGIN, Credentials.INCORRECT_PASSWORD);
        loginPage.showAuthError();
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    void loginWithInvalidDataShowsError(String username, String password) {
        loginPage.login(username, password);
        loginPage.showAuthError();
    }

    private static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(Credentials.INCORRECT_LOGIN, Credentials.INCORRECT_PASSWORD),
                Arguments.of(Credentials.FIRST_USER, Credentials.INCORRECT_PASSWORD)
        );
    }

    @Test
    public void loginByPressCreds() {
        loginPage.pasteCredentialsByPress(Credentials.FIRST_USER,
                Credentials.CORRECT_PASSWORD);

        mainPage.checkIsOpened();
    }
}
