package org.AdamCiuris;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class gmailLogin {
    gmailLogin(WebDriver logMeIn, int threadID) {

        Dotenv env = Dotenv.load();

        String appendMe = Integer.toString(threadID);
        while (appendMe.length() < 4) {
            appendMe = "0" + appendMe;
        }

        logMeIn.get("https://gmail.com");
        sleep(100); // for problems with multithreading, TODO: find a way to remove and still work



        // waits until either 10 seconds pass or that input with that id is clickable
        WebElement login = new WebDriverWait(logMeIn, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='identifierId']")));
        login.sendKeys(env.get("gmail_login"+appendMe));
        WebElement submit = logMeIn.findElement(By.xpath("//*[text()='Next']")); /* "//" means anywhere in the document for xpath*/
        submit.click();




    }

    /**
     *
     * @param millis - time to sleep in milliseconds
     * @return true if no exception false if exception
     */
    private boolean sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
