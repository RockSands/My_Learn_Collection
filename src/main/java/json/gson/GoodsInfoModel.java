package json.gson;

import com.google.gson.annotations.SerializedName;

public class GoodsInfoModel {

	@SerializedName("price")
	private int mPrice;

	@SerializedName("name")
	private String mName;

	@SerializedName("type")
	private String mType;

	@SerializedName("amount")
	private int mAmount;

	@SerializedName("summary")
	private String mSummary;

	@SerializedName("picture")
	private String mPicture;

	@SerializedName("hot")
	private boolean mHot;

	@SerializedName("sales")
	private int mSales;

	public int getPrice() {
		return mPrice;
	}

	public void setPrice(int price) {
		mPrice = price;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}

	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int amount) {
		mAmount = amount;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		mSummary = summary;
	}

	public String getPicture() {
		return mPicture;
	}

	public void setPicture(String picture) {
		mPicture = picture;
	}

	public boolean getHot() {
		return mHot;
	}

	public void setHot(boolean hot) {
		mHot = hot;
	}

	public int getSales() {
		return mSales;
	}

	public void setSales(int sales) {
		mSales = sales;
	}
}
