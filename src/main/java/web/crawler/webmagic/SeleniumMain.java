package web.crawler.webmagic;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Selenium的运行需要安装浏览器,并通过环境变量真正执行浏览器,再从浏览器上抓取内容。
 * 需要部署phantomjs
 * @author Administrator
 *
 */
public class SeleniumMain {

	public static void main(String[] args) {
		File file = new File("C:/phantomjs-2.1.1/bin/phantomjs.exe");	
		System.setProperty("phantomjs.binary.path", file.getAbsolutePath());	
		WebDriver driver = new PhantomJSDriver();	
        driver.get("http://angularjs.cn");   
        System.out.println(driver.getPageSource());
		driver.close();
	}

}
