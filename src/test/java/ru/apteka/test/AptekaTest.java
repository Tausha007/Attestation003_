package ru.apteka.test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class AptekaTest {
    MainPage mainPage = new MainPage();
    CityPopup cityPopup = new CityPopup();
    FindPage findPage = new FindPage();
    ProductPage productPage = new ProductPage();
    String titleProduct = "�������";



    @BeforeEach
    public void setSelenide(){
        baseUrl="https:/aptekaeconom.com";
        open("/");
        Selenide.webdriver().driver().getWebDriver()
                .manage().addCookie(new Cookie("current_region","103006"));
        refresh();
        cityPopup.modal.shouldNotBe(visible);
    }

    @Test
    @DisplayName("����� �������")
    @Feature("�����")
    @Story("�������� ���������� ������� � ��������� ������")
    public void shouldfindTest(){
        step("� ��������� ������ ������� �������� ������", () -> {
            mainPage.inputFind.setValue(titleProduct).pressEnter();
        });

        step("���������, ��� ��������� ������� �� �������� ������", () -> {
            findPage.header.shouldHave(text("�����"));
        });

        step("��������� ����� �������� ������� �����", () -> {
            productPage.product.shouldHave(text(titleProduct));
        });

        step("���������, ��� ���������� ������������ ������� ����� 5", () -> {
            findPage.cartProductonPage.shouldHave(CollectionCondition.size(5));
        });

    }

    @Test
    @DisplayName("���������� ������ � ���������� ������")
    @Feature("�������� �����")
    @Story("��������, ��� ��������� ����� ����������� � ������ ���������� ������� ")
    public void shouldAddWishListTest(){
        step("������� �� ������ ���������� ������", () -> {
            open(baseUrl+"/catalog/mama-i-malysh/igrushki-prorezyvateli-dlya-zubov-pustyshki/104433/");
        });

        step("�������� ����� ��� ����������", () -> {
            productPage.wishlist.click();
        });

        step("���������, ��� ���������� ���������� ������� ����� 1", () -> {
            mainPage.countProduct.shouldHave(text("1"));
        });

        step("���������, ��� ���������� ������ � ������� �� ����������", () -> {
            mainPage.countProductInCart.shouldHave(text("0"));
        });


    }

    @Test
    @DisplayName("������� �� ������������� � �������� �������")
    @Feature("������� �������")
    @Story("������������")
    public void shouldOpenCatalogTab() {
        step("������� ������ �� �������", () -> {
            $(byText("���������")).shouldBe(visible).hover();
        });

        step("�������� �� ����������� ������������", () -> {
            $(byText("�����������")).shouldBe(visible).click();
        });

        step("���������, ��� ��������� ������� � ������ ��������� �������", () -> {
            findPage.header.shouldHave(text("�����������"));
        });


    }

}
