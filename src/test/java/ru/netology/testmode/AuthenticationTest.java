package ru.netology.testmode;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import ru.netology.settings.DataGenerator;
import ru.netology.settings.RegistrationInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.settings.DataGenerator.Registration.*;

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
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Ошибка! Пользователь заблокирован "), Duration.ofSeconds(15));
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
        $x("//span[@class='input__sub']").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
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
        $x("//div[@class='notification__title']").should(Condition.text("Ошибка"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").should(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15));
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
        $x("//span[@class='input__sub']").should(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
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
        $x("//div[@class='notification__title']").should(Condition.text("Ошибка"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").should(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}