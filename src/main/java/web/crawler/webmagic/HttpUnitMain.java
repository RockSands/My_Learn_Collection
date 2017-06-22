package web.crawler.webmagic;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class HttpUnitMain {

	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		// WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		// webClient.setJavaScriptEnabled(true);
		// webClient.setCssEnabled(false);
		// webClient.setJavaScriptEngine(new JavaScriptEngine(webClient));
		// webClient.setAjaxController(new
		// NicelyResynchronizingAjaxController());
		// webClient.setTimeout(Integer.MAX_VALUE);
		// webClient.setThrowExceptionOnScriptError(false);
		// HtmlPage rootPage = webClient.getPage("http://angularjs.cn");
		// System.out.println(rootPage.asXml());

		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		webClient.setCssEnabled(false); // 禁用css支持
		webClient.setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		webClient.setTimeout(20000);
		HtmlPage page = webClient.getPage("http://angularjs.cn");
		// 我认为这个最重要
		String pageXml = page.asXml(); // 以xml的形式获取响应文本

		/** jsoup解析文档 */
		Document doc = Jsoup.parse(pageXml, "http://angularjs.cn/static/js/");
		System.out.println(doc.html());
	}

}
