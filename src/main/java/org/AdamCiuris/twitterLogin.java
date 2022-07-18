package org.AdamCiuris;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class twitterLogin {
    public WebDriver twitter;
    public twitterLogin(WebDriver twitter2) {
        this.twitter = twitter2;
        String home = twitter.getWindowHandle();
        twitter.get("https://twitter.com/login");
        // used to bind to WebElement login
        Boolean staleEle = true;


        new WebDriverWait(twitter, Duration.ofSeconds(3))
                .ignoring(StaleElementReferenceException.class)
                .until((WebDriver d) -> {
                    d.findElement(By.xpath("//div[@aria-labelledby='button-label']")).click();
                    return true;
                });
        /*while (true) {
            try {
                new WebDriverWait(twitter, Duration.ofSeconds(10)) // unhandled time out in case twitter messes up things
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-labelledby='button-label']"))).click();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("stale");
                continue;
                // somehow the element is getting changed so first click is on stale version of it
            } catch (TimeoutException e) {
                System.out.println("DEBUG TIMEOUT HAPPENED HELPHFELHLEPHPELHHLEP");
                new WebDriverWait(twitter, Duration.ofSeconds(10)) // unhandled time out in case twitter messes up things
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-labelledby='button-label']"))).click();

            }
        }*/
        while (true) {
            try {
                Set<String> handles = twitter.getWindowHandles();
                handles.remove(home); // removes current window handle
                twitter.switchTo().window(handles.iterator().next());
                System.out.println(twitter.getWindowHandle());
                break;
            } catch (NoSuchWindowException e) {
                System.out.println("no such");
                continue;
            }
        }


        new WebDriverWait(twitter, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-labelledby='picker-item-label-0']"))).click();
        twitter.switchTo().window(home);
        new WebDriverWait(twitter, Duration.ofSeconds(3))
                .until(ExpectedConditions.urlToBe("https://twitter.com/home"));



    }
//    ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(twitter, timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

   /* public static Func<WebDriver, Boolean> UrlToBe(String url)
    {
        return (WebDriver d) -> { return d.Url.ToLowerInvariant().Equals(url.ToLowerInvariant()); };
    }*/
}
