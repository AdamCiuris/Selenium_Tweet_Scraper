package org.AdamCiuris;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class tweetScraper {
    Dotenv env = Dotenv.load();
    WebDriver scrapeMe;
    public tweetScraper(WebDriver scrapeMe) {
        this.scrapeMe = scrapeMe;


        scrapeMe.get("https://www.twitter.com/"+env.get("account_to_scrape"));
        webWait();
        scrapeMe.navigate().refresh(); // navigating like above logs us out but this refresh logs us back in
        webWait();
        JavascriptExecutor j = (JavascriptExecutor) scrapeMe;
        WebElement timeline = scrapeMe.findElement(By.xpath("//div[@data-testid='primaryColumn']"));
        List<WebElement> we = timeline.findElements(By.xpath("//div[@data-testid='tweetText']"));  // must be what they use for their own testing
        Set<WebElement> tweetSet = new HashSet<>();
        HashSet<String> tweetTextSet = new HashSet<>();
        int failcount = 0;
        int len = we.size();
        while(tweetSet.size() < 100) {

            for (WebElement t :
                    we) {
                String bind = t.getText();
                if (tweetSet.add(t) && !tweetTextSet.add(t.getText()))
                {
                    failcount++;
                }
//                System.out.println(bind);
//                System.out.println(tweetSet.size());

            }
            j.executeScript("window.scrollBy(0,1000)");
            webWait();
            we = timeline.findElements(By.xpath("//div[@data-testid='tweetText']"));
        }
        System.out.println(tweetTextSet.size());
        System.out.println(tweetTextSet);
//        System.out.println(failcount);
        try {
            save(tweetTextSet);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
    /*
    waits until document binds complete to readyState, used to wait for new tweets to appear after scroll
     */
    private void webWait() {
        new WebDriverWait(scrapeMe, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
    private void save(HashSet<String> writeMe) throws IOException {
        FileWriter fw = new FileWriter();
        for (String t:writeMe
        ) {
            fw.write(t);
        }
    }
}
