<h3>Introduction</h3>

This is a twitter tweet scraper that uses Selenium. There are better ways to scrape twitter, but I am using this to learn
 Selenium as I will need it for testing and content not available through an API.

If you're looking to scrape twitter you would be better served making a curl request through their API.

I used electronJS as I already am familar with nodeJS and I am taking to opportunity to implement something new. Also, javaFX is terrible.
<h3>How to use</h3>

Provide a google account that you have a twitter account set up for in /src/main/java/resources in a file named ".env". 
Name the account name you would like to scrape there, too.

Tweet content will be saved to output.

run ``mvn package`` and run the jar with the artifact id specified in the pom.xml.

note to self: put a download link to jar on personal site