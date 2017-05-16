package design.pattern.strategy;

public class Price {
	// 持有一个具体的策略对象
	private MemberStrategy strategy;

	public Price(MemberStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 会员价格
	 * @param booksPrice
	 * @return
	 */
	public double quote(double booksPrice) {
		return this.strategy.calcPrice(booksPrice);
	}

}
