package json.gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// 初始化Gson对象(相当于环境Context)
		Gson gson = new Gson();
		// 也可以使用GsonBuilder直接构筑
		gson = new GsonBuilder()
				// 序列化null
				.serializeNulls()
				// 设置日期时间格式，另有2个重载方法
				// 在序列化和反序化时均生效
				.setDateFormat("yyyy-MM-dd")
				// 禁此序列化内部类
				.disableInnerClassSerialization()
				// 生成不可执行的Json（多了 )]}' 这4个字符）
				.generateNonExecutableJson()
				// 禁止转义html标签
				.disableHtmlEscaping()
				// 格式化输出
				.setPrettyPrinting().create();
		/*
		 * 对于Json,常遇到动态类型即泛型,Gson给出一个很个性的解决方案,那就是com.google.gson.reflect.
		 * TypeToken 样例如下: 核心代码TypeToken<UniApiResult<GoodsInfoModel>> typeToken
		 * = new TypeToken<UniApiResult<GoodsInfoModel>>() {};
		 * TypeToken保存主类以及泛型信息.它的rawType属性保存主类信息,type保存主类+泛型信息.
		 * 其思想是通过((ParameterizedType)this.getGenericSuperclass()).
		 * getActualTypeArguments()[0]获取泛型对象
		 */
		TypeToken<UniApiResult<GoodsInfoModel>> typeToken = new TypeToken<UniApiResult<GoodsInfoModel>>() {
		};
		String jsonStrT = "{ \"status\":-1, \"data\":null,  \"err\":\"not_found\", "
				// 新加入data对象
				+ "\"data\": { \"price\": 4, \"name\": \"脉动\", \"type\": \"饮料\", \"amount\": 50, "
				+ "\"summary\": \"随时随地，脉动回来\",  "
				+ "\"picture\": \"http://www.jpjy365.com/images/201402/source_img/29726_G_1393285888119.jpg\", "
				+ "\"hot\": true,\"sales\": 100} }";
		UniApiResult<GoodsInfoModel> jsonModel = gson.fromJson(jsonStrT, typeToken.getType());
		System.out.println(typeToken.getType().getTypeName());
		System.out.println(typeToken.getRawType());
		System.out.println(jsonModel.getData().getName());
		// 多个泛型可以使用如下方式
		UniApiResult<GoodsInfoModel> jsonModel_1 = gson.fromJson(jsonStrT,
				TypeToken.getParameterized(UniApiResult.class, GoodsInfoModel.class).getType());
		System.out.println(jsonModel_1.getData().getName());

		String jsonStrMap = "{ \"status\":-1, \"data\":null,  \"err\":\"not_found\\\"\"}";
		Map<String, Object> map = gson.fromJson(jsonStrMap,
				TypeToken.getParameterized(Map.class, String.class, Object.class).getType());
		System.out.println(map.get("err"));
		/*
		 * JsonReader,该Reader不支持字符串中带有"双引号".Gson的流程:
		 * 
		 * 1.找到{符号 
		 * 2.找到第一个"符号 
		 * 3.找到下一个" 其中间的就是第一个Key 
		 * 4.验证下一个是否是:号 
		 * 5.分支:查找下一个是,或} 中间的就是第一个Value ; 查找下一个是{或[ 则表示该属性值为对象.是对象则进行递归 否则就是赋值.
		 */
		JsonReader reader = new JsonReader(new StringReader(jsonStrT));
		try {
			reader.peek();
			reader.beginObject();
			while (reader.hasNext()) {
				System.out.println(reader.nextName());
				reader.skipValue();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
