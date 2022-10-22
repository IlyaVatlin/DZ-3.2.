package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorInvalidData = $("[data-test-id=error-notification]");
    private final SelenideElement blockMessage = $("[data-test-id=error-notification]");

    public VerificationPage validData(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void wrongPassword(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        errorInvalidData.shouldBe(visible);
        new LoginPage();
    }

    public void invalidData(DataHelper.AuthInfo info) {
        loginField.doubleClick();
        loginField.sendKeys(Keys.DELETE);
        loginField.setValue(info.getLogin());
        passwordField.doubleClick();
        passwordField.sendKeys(Keys.DELETE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void getErrorIfInvalidData() {
        errorInvalidData.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void getMessageIfSystemLocked () {
        blockMessage.shouldBe(visible).shouldHave(text("Ошибка! Превышено количество ввода! Учётная запись временно заблокирована!"));
    }

}