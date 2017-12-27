package algorithm.weight.bean;

import java.util.ArrayList;
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
	 * 运行时权重和
	 */
	private long runtimeWeightSum = 0l;

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

	/**
	 * 构造器
	 */
	private WeightedRoundDistributer() {

	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
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
		if (element.getCounter() < 0l) {
			throw new RuntimeException("权重分配不能接纳命中次数为负数的权重元素[" + element.getKey() + "]!");
		}
		if (element.getWeight() < 1l) {
			throw new RuntimeException("权重分配不能接纳权重为负数或零的权重元素[" + element.getKey() + "]!");
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
		if (keys.contains(element.getKey())) {
			throw new RuntimeException("权重分配已经包含了Key[" + element.getKey() + "]!");
		}
		keys.add(element.getKey());
		weightElements.add(element);
		return this;
	}

	/**
	 * 添加权重元素
	 * 
	 * @param key
	 * @param weight
	 * @return
	 */
	public synchronized WeightedRoundDistributer addWeightElement(String key, long weight) {
		WeightElement element = new WeightElement(key, weight);
		return addWeightElement(element);
	}

	/**
	 * 添加权重元素
	 * 
	 * @param key
	 * @param counter
	 * @param weight
	 * @return
	 */
	public synchronized WeightedRoundDistributer addWeightElement(String key, long counter, long weight) {
		WeightElement element = new WeightElement(key, counter, weight);
		return addWeightElement(element);
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
	 * 启动
	 */
	public synchronized WeightedRoundDistributer start() {
		if (keys.isEmpty()) {
			throw new RuntimeException("权重分配未定义任何权重元素，无法启动");
		}
		if (keys.size() == 1) {
			throw new RuntimeException("权重分配仅定义一个权重元素，无法启动");
		}
		if (weightElements.isEmpty()) {
			if (isStart) {// 如果为空,则未变更
				return this;
			} else {// 如果为空,则未变更
				throw new RuntimeException("权重分配未定义任何权重元素，无法启动!");
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
			if (!keys.contains(indexRunning.getKey())) {// 如果Keys不存在,该权重元素已被删除
				it.remove();
				continue;
			}
			counter = counter + indexRunning.getCounter();
			weightSum = weightSum + indexRunning.getWeight();
		}
		/*
		 * 期待值相关的计算
		 */
		// 权重的最大公约数
		long weightDivisor = greatestCommonDivisor();
		long minWeightSum = weightSum / weightDivisor;
		// 期待值时,每个权重代表的数量
		long unitValue = 0l;
		unitValue = minUnitWeight(weightDivisor);
		if (counter == 0l || counter == unitValue * minWeightSum) { // 直接为平稳期
			for (WeightElement element : runningtElements) {
				element.setCurrentWeight(0l);
				element.setRuntimeWeight(element.getWeight());
				runtimeWeightSum = weightSum;
				expect = 0l;
			}
		} else {// 定义迁移期
			unitValue = Math.max(unitValue, (counter + expect) / minWeightSum);
			expect = unitValue * minWeightSum - counter;
			runtimeWeightSum = expect;
			// 设定运行时的权重
			for (WeightElement element : runningtElements) {
				element.setRuntimeWeight(unitValue * element.getWeight() / weightDivisor - element.getCounter());
			}
		}
		isStart = true;
		return this;
	}

	/**
	 * @return the runningtElements
	 */
	public synchronized List<WeightElement> getWeightElements() {
		List<WeightElement> list = null;
		if (!isStart) {
			list = new ArrayList<WeightElement>(weightElements.size());
			for (WeightElement element : weightElements) {
				list.add(new WeightElement(element.getKey(), element.getCounter(), element.getWeight()));
			}
		} else {
			list = new ArrayList<WeightElement>(runningtElements.size());
			for (WeightElement element : runningtElements) {
				list.add(new WeightElement(element.getKey(), element.getCounter(), element.getWeight()));
			}
		}
		return list;
	}

	/**
	 * @return the isStart
	 */
	public boolean isStart() {
		return isStart;
	}

	/**
	 * @return the counter
	 */
	public long getCounter() {
		return counter;
	}

	/**
	 * @return the keys
	 */
	public Set<String> getKeys() {
		return keys;
	}

	/**
	 * 下一个
	 * 
	 * @return
	 */
	public synchronized String next() {
		if (!isStart) {
			throw new RuntimeException("权重分配未启动!");
		}
		String selectKey = pickKey();
		if (expect > 1l) {
			expect--;
		}
		if (expect == 1l) {
			for (WeightElement element : runningtElements) {
				element.setCurrentWeight(0l);
				element.setRuntimeWeight(element.getWeight());
			}
			runtimeWeightSum = weightSum;
			expect = 0l;
		}
		counter++;
		return selectKey;
	}

	/**
	 * 结算,返回之前执行的记录并从头开始进行
	 * 
	 * @return
	 */
	public synchronized List<WeightElement> settlement() {
		if (!isStart) {
			throw new RuntimeException("权重分配未启动!");
		}
		List<WeightElement> list = new ArrayList<WeightElement>(runningtElements.size());
		for (WeightElement element : runningtElements) {
			list.add(new WeightElement(element.getKey(), element.getCounter(), element.getWeight()));
			element.settlement();
		}
		expect = 0l;
		runtimeWeightSum = weightSum;
		counter = 0l;
		return list;
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
		if (0 == a % 2l) {
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
	 * @param 最大公约数
	 * @return
	 */
	private long minUnitWeight(long weightDivisor) {
		long unitValue = 0l;
		for (WeightElement element : runningtElements) {
			unitValue = Math.max(unitValue,
					(long) Math.ceil(new Double(element.getCounter()) * weightDivisor / element.getWeight()));
		}
		return unitValue;
	}

	/**
	 * 获取Key
	 * 
	 * @return
	 */
	private synchronized String pickKey() {
		WeightElement pickElement = null;
		for (WeightElement element : runningtElements) {
			element.addWeight();
			if (pickElement == null || pickElement.getCurrentWeight() < element.getCurrentWeight()) {
				pickElement = element;
			}
		}
		pickElement.hit(runtimeWeightSum);
		return pickElement.getKey();
	}
}
