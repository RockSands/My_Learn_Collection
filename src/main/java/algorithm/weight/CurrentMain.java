package algorithm.weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CurrentMain {

	private long sumWeight; // 权重和

	private long sumTargetWeight; // 目标权重和

	private static long expect;

	private static List<Server> servers = new ArrayList<Server>();

	private class Server {

		/**
		 * IP
		 */
		String ip;

		/**
		 * 当前数量
		 */
		long currentNum;

		/**
		 * 权重
		 */
		long weight;

		/**
		 * 目标权重
		 */
		long target_weight;

		/**
		 * 当前权重
		 */
		long current_weight;

		public Server(String ip, long target_weight) {
			this.ip = ip;
			this.target_weight = target_weight;
		}

		public void pick() {
			currentNum = currentNum + 1;
			current_weight = current_weight - sumWeight;
		}

		public void addweight() {
			current_weight = current_weight + weight;
		}
	}

	public Server GetServer() {
		Server maxServer = null;
		for (Server server : servers) {
			server.addweight();
			if (maxServer == null || maxServer.current_weight < server.current_weight) {
				maxServer = server;
			}
		}
		maxServer.pick();
		return maxServer;
	}

	public void init() {
		Server s1 = new Server("192.168.0.100", 3);
		Server s2 = new Server("192.168.0.101", 4);
		Server s3 = new Server("192.168.0.102", 3);
		servers.add(s1);
		servers.add(s2);
		servers.add(s3);
		for (Server server : servers) {
			sumTargetWeight = sumTargetWeight + server.target_weight;
		}
		expect = 1000l;
		s1.currentNum = 200l;
		s2.currentNum = 300l;
		s3.currentNum = 200l;
		System.out.println("minArriveWeight==> " + minArriveWeight());
		expect =  minArriveWeight();
		expect = (long) Math.ceil(new Double(expect + s1.currentNum + s2.currentNum + s3.currentNum) / sumTargetWeight);
		System.out.println("expect==> " + expect);
		s1.weight = (expect) * 3 - s1.currentNum;
		System.out.println("s1.weight==> " + s1.weight);
		s2.weight = (expect) * 4 - s2.currentNum;
		System.out.println("s2.weight==> " + s2.weight);
		s3.weight = (expect) * 3 - s3.currentNum;
		System.out.println("s3.weight==> " + s3.weight);
		sumWeight = s1.weight + s2.weight + s3.weight;
		System.out.println("sumWeight==> " + sumWeight);
	}

	/**
	 * 最小抵达目标权重的值
	 * 
	 * @return
	 */
	private long minArriveWeight() {
		long multiple = -1l;
		long sourceCount = 0l;
		for (Server server1 : servers) {
			sourceCount = sourceCount + server1.currentNum;
			multiple = Math.max(multiple,(long) Math.ceil(new Double(server1.currentNum) / server1.target_weight));
		}
		return multiple * sumTargetWeight - sourceCount;
	}

	public static void main(String[] args) {
		CurrentMain obj = new CurrentMain();
		obj.init();
		Map<String, Integer> countResult = new HashMap<String, Integer>();
		for (long i = 0l; i < 50; i++) {
			Server s = obj.GetServer();
			String log = "ip:" + s.ip + ";weight:" + s.weight;
			if (countResult.containsKey(log)) {
				countResult.put(log, countResult.get(log) + 1);
			} else {
				countResult.put(log, 1);
			}
			System.out.println(log);
		}
		for (Entry<String, Integer> map : countResult.entrySet()) {
			System.out.println("服务器 " + map.getKey() + " 被次数： " + map.getValue());
		}
		System.out.println("-------------------------结果-------------------------------");
		for (Server server : servers) {
			System.out.println("服务器 " + server.ip + " 请求总数： " + server.currentNum);
		}
	}
}
