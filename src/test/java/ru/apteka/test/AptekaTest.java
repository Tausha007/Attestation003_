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

public class AptekaTest extends WebTest{
    MainPage mainPage = new MainPage();
    CityPopup cityPopup = new CityPopup();
    FindPage findPage = new FindPage();
    ProductPage productPage = new ProductPage();
    String titleProduct = "Витамин";



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
    @DisplayName("Поиск товаров")
    @Feature("Поиск")
    @Story("Проверка количества товаров в поисковой выдаче")
    public void shouldfindTest(){
        step("В поисковой строке введите название товара", () -> {
            mainPage.inputFind.setValue(titleProduct).pressEnter();
        });

        step("Проверить, что произошел переход на страницу поиска", () -> {
            findPage.header.shouldHave(text("Поиск"));
        });

        step("Найденный товар содержит искомый текст", () -> {
            productPage.product.shouldHave(text(titleProduct));
        });

        step("Проверить, что количество отображаемых товаров равно 5", () -> {
            findPage.cartProductonPage.shouldHave(CollectionCondition.size(5));
        });

    }

    @Test
    @DisplayName("Добавление товара в отложенный список")
    @Feature("Отложить товар")
    @Story("Проверка, что выбранный товар добавляется в список отложенных товаров ")
    public void shouldAddWishListTest(){
        step("Переход по ссылке выбранного товара", () -> {
            open(baseUrl+"/catalog/mama-i-malysh/igrushki-prorezyvateli-dlya-zubov-pustyshki/104433/");
        });

        step("Пометить товар как отложенный", () -> {
            productPage.wishlist.click();
        });

        step("Проверить, что количество отложенных товаров равно 1", () -> {
            mainPage.countProduct.shouldHave(text("1"));
        });

        step("Проверить, что количество товара в корзине не изменилось", () -> {
            mainPage.countProductInCart.shouldHave(text("0"));
        });


    }

    @Test
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    @Feature("Каталог товаров")
    @Story("Подкатегории")
    public void shouldOpenCatalogTab() {
        step("Навести курсор на вкладку", () -> {
            $(byText("Косметика")).shouldBe(visible).hover();
        });

        step("Кликнуть на появившуюся подкатегорию", () -> {
            $(byText("Дезодоранты")).shouldBe(visible).click();
        });

        step("Проверить, что произошел переход в нужную категорию товаров", () -> {
            findPage.header.shouldHave(text("Дезодоранты"));
        });


    }

}
