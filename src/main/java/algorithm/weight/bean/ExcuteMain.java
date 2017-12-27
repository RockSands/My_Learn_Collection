package algorithm.weight.bean;

import java.util.HashMap;
import java.util.Map;

public class ExcuteMain {

	public static void main(String[] args) {
		/*
		 * 从零开始
		 */
//		WeightedRoundDistributer distributer = WeightedRoundDistributer.newInstance()
//				.addWeightElement("192.168.0.100", 1).addWeightElement("192.168.0.101", 2)
//				.addWeightElement("192.168.0.102", 3).start();
		/*
		 * 直接稳定期迁移
		 */
//		WeightedRoundDistributer distributer = WeightedRoundDistributer.newInstance()
//				.addWeightElement("192.168.0.10", 10, 2).addWeightElement("192.168.0.101", 20, 4)
//				.addWeightElement("192.168.0.102", 30, 6).start();
		/*
		 * 迁移测试
		 */
		WeightedRoundDistributer distributer = WeightedRoundDistributer.newInstance()
				.addWeightElement("192.168.0.100", 10, 9).addWeightElement("192.168.0.101", 20, 6)
				.addWeightElement("192.168.0.102", 30, 3).start();
		int count = 200;
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
		for (WeightElement element : distributer.getWeightElements()) {
			System.out.println("=Key[" + element.getKey() + "]执行总次数=>" + element.getCounter());
		}
	}

}
