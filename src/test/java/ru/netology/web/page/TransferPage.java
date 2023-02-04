package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TransferPage {
    /*private SelenideElement heading = $("div h1");
    private SelenideElement amountInput = $("[data-test-id='amount'] input");
    private SelenideElement cardNumberInput = $("[data-test-id='from'] input");
    private SelenideElement submitTransferButton = $(".button_view_extra");*/
    private SelenideElement errorMessage = $("[data-test-id=\"error-notification\"]");

    /*public TransferPage() {
        heading.shouldBe(visible);
    }*/
    public DashboardPage fillAmount (int amount, String cardNumber){
        $("[data-test-id=amount] input").setValue(String.valueOf(amount));
        $("[data-test-id=from] input").setValue(cardNumber);
        $$("button").find(exactText("Пополнить")).click();
        return new DashboardPage();
    }

    public void errorMessage (){
        errorMessage.shouldBe(visible);
    }


}


