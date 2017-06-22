package web.crawler.webmagic;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * 官方文档的样例代码
 * http://webmagic.io/docs/zh/posts/ch1-overview/architecture.html
 * @author Administrator
 *
 */
public class FirstMain {

	public static void main(String[] args) {
		// GithubRepoPageProcessor是作者的样例，本身就有Main执行方法
		Spider.create(new GithubRepoPageProcessor())
				// 从https://github.com/code4craft开始抓
				.addUrl("https://github.com/code4craft")
				// 设置Scheduler，使用Files来管理URL队列
				.setScheduler(new FileCacheQueueScheduler("D:\\data\\webmagic"))
				// 设置Pipeline，将结果以json方式保存到文件
				.addPipeline(new JsonFilePipeline("D:\\data\\webmagic\\pipe"))
				// 开启5个线程同时执行
				.thread(5)
				// 启动爬虫
				.run();
	}
}
