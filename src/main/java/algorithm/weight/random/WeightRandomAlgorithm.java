package algorithm.weight.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class WeightRandomAlgorithm {
	private int sumWeight = 0; // 权重和
	private List<Server> serverList; // 服务器集合
	private Random random;// 随机数生产器

	/**
	 * 算法流程： 假设有一组服务器 S = {S0, S1, …, Sn-1} 有相应的权重，变量currentIndex表示上次选择的服务器
	 * 权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器， 通过权重的不断递减
	 * 寻找 适合的服务器返回，直到轮询结束，权值返回为0
	 */
	public Server GetServer() {
		int randomNum = random.nextInt(sumWeight);
		for (Server server : serverList) {
			if (server.hit(randomNum)) {
				return server;
			}
		}
		// 不会发生返回Null
		return null;
	}

	class Server {
		public String ip;

		/**
		 * 数据区间起始
		 */
		public int start;

		/**
		 * 数据区间结束
		 */
		public int weight;

		public Server(String ip, int start, int weight) {
			super();
			this.ip = ip;
			this.start = start;
			this.weight = weight;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		/**
		 * 是否命中
		 * 
		 * @param randomNum
		 * @return
		 */
		public boolean hit(int randomNum) {
			return randomNum >= start && randomNum < start + weight;
		}
	}

	public void init() {
		Server s1 = new Server("A", 0, 1);
		Server s2 = new Server("B", 1, 2);
		Server s3 = new Server("C", 3, 3);
		Server s4 = new Server("D", 6, 4);
		sumWeight = 1 + 2 + 3 + 4;
		random = new Random();
		serverList = new ArrayList<Server>();
		serverList.add(s1);
		serverList.add(s2);
		serverList.add(s3);
		serverList.add(s4);
	}

	public static void main(String[] args) {
		WeightRandomAlgorithm obj = new WeightRandomAlgorithm();
		obj.init();

		Map<String, Integer> countResult = new HashMap<String, Integer>();

		for (int i = 0; i < 100; i++) {
			Server s = obj.GetServer();
			String log = s.ip + "\t";
			if (countResult.containsKey(log)) {
				countResult.put(log, countResult.get(log) + 1);
			} else {
				countResult.put(log, 1);
			}
			System.out.print(log);
		}

		for (Entry<String, Integer> map : countResult.entrySet()) {
			System.out.println("服务器 " + map.getKey() + " 请求次数： " + map.getValue());
		}
	}

}
