package algorithm.weight.bean;

import java.util.HashMap;
import java.util.Map;

public class ExcuteMain {

	public static void main(String[] args) {
		WeightedRoundDistributer distributer = WeightedRoundDistributer.newInstance();
		/*
		 * 从零开始
		 */
		// distributer.addWeightElement("192.168.0.100",
		// 1).addWeightElement("192.168.0.101", 2)
		// .addWeightElement("192.168.0.102", 3).start();
		/*
		 * 直接稳定期迁移
		 */
		// distributer.addWeightElement("192.168.0.100", 100,
		// 2).addWeightElement("192.168.0.101", 200, 4)
		// .addWeightElement("192.168.0.102", 300, 6).start();
		distributer.addWeightElement("192.168.0.100", 100, 9)
				.addWeightElement("192.168.0.101", 200, 6)
				.addWeightElement("192.168.0.102", 300, 3).start();
		int count = 2000;
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key = null;
		for (int i = 0; i < count; i++) {
			key = distributer.next();
			System.out.println("=Pick=>" + key);
			if (!map.containsKey(key)) {
				map.put(key, 0);
			}
			map.put(key, map.get(key) + 1);
		}
		System.out.println("------------------结束----------------------");
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("=Key[" + entry.getKey() + "]执行次数=>" + entry.getValue());
		}
	}

}
