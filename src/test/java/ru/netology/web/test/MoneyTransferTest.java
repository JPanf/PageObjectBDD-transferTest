package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.generateInvalidAmount;
import static ru.netology.web.data.DataHelper.generateValidAmount;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyFirstToSecond() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
//    var loginPage = open("http://localhost:9999", LoginPageV1.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.firstCard();
        var secondCardInfo = DataHelper.secondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        int amount = generateValidAmount(secondCardBalance);
        var transferPageFirstCard = dashboardPage.chooseCardForTransfer(firstCardInfo);
        transferPageFirstCard.fillAmount(amount, secondCardInfo.getCardNumber());
        var expectedFirstCardBalance = firstCardBalance + amount ;
        var expectedSecondCardBalance = secondCardBalance-amount;
        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(firstCardInfo));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(secondCardInfo));
    }
    @Test
    void shouldTransferMoneySecondToFirst() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.firstCard();
        var secondCardInfo = DataHelper.secondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var transferPageSecondCard = dashboardPage.chooseCardForTransfer(secondCardInfo);
        int amount = generateValidAmount(firstCardBalance);
        transferPageSecondCard.fillAmount(amount, firstCardInfo.getCardNumber());
        var expectedSecondCardBalance = secondCardBalance + amount;
        var expectedFirstCardBalance = firstCardBalance - amount;
        assertEquals(expectedSecondCardBalance,dashboardPage.getCardBalance(secondCardInfo));
        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(firstCardInfo));
    }

    @Test
    void shouldNotTransferOverBalanceSum() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.firstCard();
        var secondCardInfo = DataHelper.secondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var transferPage = dashboardPage.chooseCardForTransfer(secondCardInfo);
        int amount = generateInvalidAmount(firstCardBalance);
        transferPage.fillAmount(amount, firstCardInfo.getCardNumber());
        transferPage.errorMessage();
    }
}
