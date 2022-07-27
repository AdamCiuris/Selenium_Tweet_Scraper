package org.AdamCiuris;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class gmailPassword {
    gmailPassword(WebDriver logMeIn, int threadID) {
        Dotenv env = Dotenv.load();
        String appendMe = Integer.toString(threadID);
        while (appendMe.length() < 4) {
            appendMe = "0" + appendMe;
        }

        // useful video on xpath https://www.youtube.com/watch?v=9N7ERYWbjuw
        WebElement password = new WebDriverWait(logMeIn, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
        password.sendKeys(env.get("gmail_password"+ appendMe));
        WebElement submit = logMeIn.findElement(By.xpath("//*[text()='Next']"));
        submit.click();
    }
}
