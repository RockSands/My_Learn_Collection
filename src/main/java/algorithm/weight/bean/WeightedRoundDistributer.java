package algorithm.weight.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * 权重轮询分配者
 * 
 * @author Administrator
 *
 */
public class WeightedRoundDistributer {
	/**
	 * 是否运行
	 */
	private boolean isStart;
	/**
	 * 计数器
	 */
	private long counter = 0l;
	/**
	 * 期望值
	 */
	private long expect = 0l;
	/**
	 * 权重和
	 */
	private long weightSum = 0l;
	/**
	 * 权重的最大公约数
	 */
	private long weightDivisor = 0l;
	/**
	 * 记录元素的Key
	 */
	private Set<String> keys = new HashSet<String>();
	/**
	 * 记录权重元素--运行中的
	 */
	private List<WeightElement> runningtElements = new LinkedList<WeightElement>();
	/**
	 * 记录权重元素--未运行
	 */
	private List<WeightElement> weightElements = new LinkedList<WeightElement>();

	private WeightedRoundDistributer() {

	}

	public static WeightedRoundDistributer newInstance() {
		return new WeightedRoundDistributer();
	}

	/**
	 * 检查权重元素
	 * 
	 * @param element
	 */
	private void checkWeightElement(WeightElement element) {
		if (element == null) {
			throw new RuntimeException("权重分配不能接纳Null元素!");
		}
		if (element.getKey() == null || "".equals(element.getKey().trim())) {
			throw new RuntimeException("权重分配不能接纳Key为空的元素!");
		}
		if (element.getCounter() < 0) {
			throw new RuntimeException("权重分配不能接纳命中次数为负数的权重元素[" + element.getKey() + "]!");
		}
		if (element.getWeight() < 1) {
			throw new RuntimeException("权重分配不能接纳权重为负数或零的权重元素[" + element.getKey() + "]!");
		}
		if (keys.contains(element.getKey())) {
			throw new RuntimeException("权重分配已经包含了Key[" + element.getKey() + "]!");
		}
	}

	/**
	 * 添加权重元素
	 * 
	 * @param element
	 * @return
	 */
	public synchronized WeightedRoundDistributer addWeightElement(WeightElement element) {
		checkWeightElement(element);
		keys.add(element.getKey());
		weightElements.add(element);
		return this;
	}

	/**
	 * 删除元素
	 * 
	 * @param key
	 * @return
	 */
	public synchronized WeightedRoundDistributer removeWeightElement(String key) {
		keys.remove(key);
		return this;
	}

	/**
	 * 变更
	 * 
	 * @param key
	 * @param weight
	 * @return
	 */
	public synchronized WeightedRoundDistributer modifyWeightElement(String key, long weight) {
		if (keys.contains(key)) {
			boolean find = false;
			for (WeightElement element : weightElements) {
				if (element.getKey().equals(key)) {
					element.setWeight(weight);
					find = true;
					break;
				}
			}
			if (!find) {
				weightElements.add(new WeightElement(key, weight));
			}
		}
		return this;
	}

	/**
	 * 期望值
	 * 
	 * @param expect
	 * @return
	 */
	public synchronized WeightedRoundDistributer expect(long expect) {
		this.expect = expect;
		return this;
	}

	/**
	 * 计算公约数
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private long greatestCommonDivisor(long a, long b) {
		if (a < b) {
			return greatestCommonDivisor(b, a);
		}
		if (0 == b) {
			return a;
		}
		if (isTwo(a)) {
			if (isTwo(b)) {
				return greatestCommonDivisor(a / 2, b / 2) * 2;
			}
			return greatestCommonDivisor(a / 2, b);
		} else {
			if (isTwo(b)) {
				return greatestCommonDivisor(a, b / 2);
			}
			return greatestCommonDivisor(b, a - b);
		}

	}

	/**
	 * 是否为2的倍数
	 * 
	 * @param a
	 * @return
	 */
	private boolean isTwo(long a) {
		if (0 == a % 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 最小化权重,最大公约数处理
	 * 
	 * @return
	 */
	private long greatestCommonDivisor() {
		// 最大公约数
		long zdgys = greatestCommonDivisor(runningtElements.get(0).getWeight(), runningtElements.get(1).getWeight());
		if (runningtElements.size() > 2) {
			for (int index = 2, size = runningtElements.size(); index < size; index++) {
				zdgys = greatestCommonDivisor(zdgys, runningtElements.get(index).getWeight());
			}
		}
		return zdgys;
	}

	/**
	 * 最小期望值下,单位权重代表数量
	 * 
	 * @return
	 */
	private long minUnitWeight() {
		long unitValue = 0l;
		for (WeightElement element : runningtElements) {
			unitValue = Math.max(unitValue, (long) Math.ceil(new Double(element.getCounter()) * weightDivisor / element.getWeight()));
		}
		return unitValue;
	}

	/**
	 * 启动
	 */
	public synchronized void start() {
		if (keys.isEmpty()) {
			throw new RuntimeException("权重分配未定义任何权重元素，无法启动");
		}
		if (keys.size() == 1) {
			throw new RuntimeException("权重分配仅定义一个权重元素，无法启动");
		}
		if (weightElements.isEmpty()) {
			if (isStart) {// 如果为空,则未变更
				return;
			} else {// 如果为空,则未变更
				throw new RuntimeException("权重分配未定义任何权重元素，无法启动");
			}
		}
		WeightElement index = null;
		WeightElement indexRunning = null;
		boolean hasRunning;
		for (Iterator<WeightElement> it0 = weightElements.iterator(); it0.hasNext();) {
			index = it0.next();
			hasRunning = false;
			checkWeightElement(index);
			for (Iterator<WeightElement> it1 = runningtElements.iterator(); it1.hasNext();) {
				indexRunning = it1.next();
				if (index.getKey().equals(indexRunning.getKey())) {
					hasRunning = true;
					indexRunning.setWeight(index.getWeight());
					break;
				}
			}
			if (!hasRunning) {
				runningtElements.add(index);
			}
			it0.remove();
		}
		counter = 0l;
		weightSum = 0l;
		for (Iterator<WeightElement> it = runningtElements.iterator(); it.hasNext();) {
			indexRunning = it.next();
			counter = counter + indexRunning.getCounter();
			weightSum = weightSum + indexRunning.getWeight();
		}
		// 期待值计算
		weightDivisor = greatestCommonDivisor();
		long unitValue = 0;
		unitValue = (counter + expect) / (weightSum / weightDivisor);
		unitValue = Math.max(unitValue, minUnitWeight());
	}

	/**
	 * 下一个
	 * 
	 * @return
	 */
	public synchronized String next() {

	}

}
