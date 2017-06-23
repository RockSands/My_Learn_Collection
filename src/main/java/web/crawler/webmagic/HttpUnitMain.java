package web.crawler.webmagic;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class HttpUnitMain {

	/**
	 * 测试HTMLUnit抓取页面的能力,目前测试BrowserVersion不同区别很大,而且Unit不同版本支持浏览器驱动不一致,
	 * 这里使用HtmlUnit-2.13 版本Firefox17能获取JS的静态信息
	 * @param args
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setTimeout(Integer.MAX_VALUE);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setJavaScriptEngine(new JavaScriptEngine(webClient));
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		HtmlPage rootPage = webClient.getPage("http://angularjs.cn");
		System.out.println(rootPage.asXml());
	}
}
