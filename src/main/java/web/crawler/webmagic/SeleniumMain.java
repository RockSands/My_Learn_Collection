package web.crawler.webmagic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumMain {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");
		Navigation navigation = driver.navigate();
		navigation.to("http://angularjs.cn");
		WebElement webElement = driver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
		driver.close();
	}

}
