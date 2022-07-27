package org.AdamCiuris;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class twitterLogin {
    public WebDriver twitter;
    public twitterLogin(WebDriver twitter2, int threadID) {
        this.twitter = twitter2;
        String home = twitter.getWindowHandle();
        twitter.get("https://twitter.com/login");
        // used to bind to WebElement login
        Boolean staleEle = true;

//        webWait();
        WebElement click;
        while(true) {
            try {
                new WebDriverWait(twitter, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-labelledby='button-label']"))).click();
            } catch (StaleElementReferenceException e) {
                continue;
            } catch (TimeoutException e) {
                // HOW DO I FIX THIS
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
//        new WebDriverWait(twitter, Duration.ofSeconds(3))
//                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-labelledby='button-label']"))).click();
        webWait();

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


    private void webWait() {
        try {
            new WebDriverWait(twitter, 30).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /* public static Func<WebDriver, Boolean> UrlToBe(String url)
    {
        return (WebDriver d) -> { return d.Url.ToLowerInvariant().Equals(url.ToLowerInvariant()); };
    }*/
}
