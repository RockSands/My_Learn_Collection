package json.gson;

import com.google.gson.annotations.SerializedName;

public class UniApiResult<T> {

	@SerializedName("status")
	private int mStatus;

	@SerializedName("err")
	private String mErr;

	@SerializedName("data")
	private T mData;

	public int getStatus() {
		return mStatus;
	}

	public void setStatus(int status) {
		mStatus = status;
	}

	public String getErr() {
		return mErr;
	}

	public void setErr(String err) {
		mErr = err;
	}

	public T getData() {
		return mData;
	}

	public void setData(T data) {
		mData = data;
	}
}