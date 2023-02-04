package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.By;
import ru.netology.web.data.DataHelper;

import javax.management.Query;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static javax.management.Query.div;
import static org.openqa.selenium.By.xpath;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }
    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(attribute("data-test-id", String.valueOf(cardInfo.cardId))).getText();
        return extractBalance(text);
    }
    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


    public TransferPage chooseCardForTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(attribute("data-test-id", String.valueOf(cardInfo.cardId))).$(byText("Пополнить")).click();
        return new TransferPage();
    }
}