package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorInvalidCode = $("[data-test-id=error-notification]");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void validVerify(String verificationCode) {
        codeField.setValue(String.valueOf(verificationCode));
        verifyButton.click();
        new DashboardPage();
    }

    public void invalidVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(String.valueOf(verificationCode));
        verifyButton.click();
        errorInvalidCode.shouldBe(visible);
    }

    public void getErrorIfInvalidSmsCode() {
        errorInvalidCode.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз"));
    }
}