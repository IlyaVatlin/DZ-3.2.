package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.DataBase;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginSystemTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    public static void cleanUp() {
        DataBase dataBase = new DataBase();
        dataBase.cleanDataBase();
    }

    // Успешная авторизация;
    @Test
    public void shouldSuccessfulLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validData(authInfo);
        var code = DataBase.getCorrectVerificationCode();
        verificationPage.validVerify(code);
    }

    // Авторизация с неверным паролем;
    @Test
    public void shouldGetErrorIfInvalidData() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getWrongAuthInfo();
        loginPage.wrongPassword(authInfo);
        loginPage.getErrorIfInvalidData();
    }

    // Авторизация с некорректным смс-кодом;
    @Test
    public void shouldGetErrorIfWrongCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validData(authInfo);
        var code = DataHelper.getWrongVerificationCode();
        verificationPage.invalidVerify(code);
        verificationPage.getErrorIfInvalidSmsCode();
    }

    // Ошибка при трёхкратном введении неверного пароля при входе в систему;
    @Test
    public void shouldGetErrorIfPasswordIncorrectThreeTimes() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getWrongAuthInfo();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getMessageIfSystemLocked();
    }
}