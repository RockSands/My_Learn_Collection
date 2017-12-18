package algorithm.weight.robin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 权重轮询调度算法(WeightedRound-RobinScheduling)-Java实现
 * 
 * @author chenkw
 */
public class SmoothRoundRobinScheduling {

	private int sumWeight; // 权重和

	private List<Server> serverList; // 服务器集合

	public Server GetServer() {
		Server maxServer = null;
		for(Server server : serverList){
			server.addweight();
			if(maxServer == null || maxServer.getCurrent_weight() <  server.getCurrent_weight()){
				maxServer = server;
			}
		}
		maxServer.pick();
		return maxServer;
	}

	class Server {
		public String ip;
		public int weight;
		public int current_weight;

		public Server(String ip, int weight) {
			super();
			this.ip = ip;
			this.weight = weight;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getCurrent_weight() {
			return current_weight;
		}

		public void setCurrent_weight(int current_weight) {
			this.current_weight = current_weight;
		}

		/**
		 * 加权
		 */
		public void addweight() {
			current_weight = current_weight + weight;
		}

		/**
		 * 被选择
		 */
		public void pick() {
			current_weight = current_weight - sumWeight;
		}
	}

	public void init() {
		Server s1 = new Server("192.168.0.100", 1);
		Server s2 = new Server("192.168.0.101", 2);
		Server s3 = new Server("192.168.0.102", 3);
		Server s4 = new Server("192.168.0.103", 4);
		Server s5 = new Server("192.168.0.104", 5);
		sumWeight = 1 + 2 + 3 + 4 + 5;
		serverList = new ArrayList<Server>();
		serverList.add(s1);
		serverList.add(s2);
		serverList.add(s3);
		serverList.add(s4);
		serverList.add(s5);
	}

	public static void main(String[] args) {
		SmoothRoundRobinScheduling obj = new SmoothRoundRobinScheduling();
		obj.init();

		Map<String, Integer> countResult = new HashMap<String, Integer>();

		for (int i = 0; i < 1000; i++) {
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
			System.out.println("服务器 " + map.getKey() + " 请求次数： " + map.getValue());
		}
	}

}