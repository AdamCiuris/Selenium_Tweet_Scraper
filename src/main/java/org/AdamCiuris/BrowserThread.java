package org.AdamCiuris;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ChromeDriver Version: 103.0.5060.24
 * Author: Adam Ciuris
 * Title: Twitter Tweet Scraper
 * Description: Logs into gmail account specified in .env and uses it to scrape twitter.
 * Dependencies: See pom.xml.
 * */
public class BrowserThread {
    public static WebDriver driver;
    private Lock syncThis = new ReentrantLock();


    Dotenv dotenv = Dotenv.configure()
            .directory("../../resources")
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    class Initialize implements Runnable {
        private static Lock loopLock = new ReentrantLock();
        private static Condition reachedTwitterHomePage = loopLock.newCondition();
        /**
         * Instantiates webdriver.
         *
         * @param initThis - webdriver to instantiate
         */
        public void init() {
            driver = new ChromeDriver();
        }

        public void close() {
            loopLock.unlock();
            driver.quit();
        }

        /**
         * Logs into gmail with .env vars
         */
        public void gmail() {
            try {
                new gmailLogin(driver); // should be eligible for garbage collection since not reachable
                new gmailPassword(driver);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }

        /**
         * Logs into twitter with gmail account.
         */
        public void twitter() {
            int test = 0;
            try {
                new twitterLogin(driver);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    loopLock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        public void scrape() {
            try {
                new tweetScraper(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            do {
                try {
                    init();
                    gmail();
                    twitter();
                } catch (TimeoutException te) {
                    continue;
                }
                if (!loopLock.tryLock()) {
                    try {
                        reachedTwitterHomePage.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } while (!driver.getCurrentUrl().equals("https://twitter.com/home"));
            // end setup


            scrape();



        } // end inner class Initialize



    }

    public void close() {
        driver.close();
    }




    public static void main(String[] args) {
        BrowserThread run = new BrowserThread();
        shutdown(run);
//
//        List<String> list = Arrays.asList("1","2","3","4","5","6");
//        try {
//            FileWriter test = new FileWriter();
//            for (String s:
//            new HashSet<String>(list)) {
//             test.write(s);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        BrowserThread.Initialize my = run.new Initialize();
        my.run();

       /* try {
            run = retryUntilIntoTwitter();

        }
        finally {
            //shutdown(300, run);
        }*/

    }

    /**
     * Call to shut down chrome browser.
     *
     * @param millis - time to wait before shutdown
     * @param run    - main thread
     */
    public static void shutdown(BrowserThread run) {
        /**
         * absolutely necessary to keep browser instances manageable
         * below hook kills the webdriver on program exit
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                run.close();
            }
        }, "Shutdown-thread"));
    }
}