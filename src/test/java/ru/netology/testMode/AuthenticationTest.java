package ru.netology.testMode;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.Settings.DataGenerator;
import ru.netology.Settings.RegistrationInfo;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Settings.DataGenerator.Registration.*;

public class AuthenticationTest {

    @Test
    void shouldActiveUser() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("active");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(), 'Личный кабинет')]").should(Condition.visible);
    }

    @Test
    void shouldBlockedUser() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible);
    }

    @Test
    void shouldNoLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        //Поле ввода логина оставить пустым
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $("[data-test-id='login'].input_invalid .input__sub").should(Condition.visible);
    }

    @Test
    void shouldWrongLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(getRandomLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible);
    }

    @Test
    void shouldNoPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        //Поле ввода пароля оставить пустым
        $x("//span[@class='button__text']").click();
        $("[data-test-id='password'].input_invalid .input__sub").should(Condition.visible);
    }

    @Test
    void shouldWrongPassword() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        RegistrationInfo user = DataGenerator.Registration.getRegisteredUser("blocked");
        sendRequest(user);
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(getRandomPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible);
    }
}