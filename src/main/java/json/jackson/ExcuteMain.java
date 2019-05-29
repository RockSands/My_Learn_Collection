package json.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Json的反序列核心接口JsonDeserializer, 其有很多的实现类
 * @author Administrator
 *
 */
public class ExcuteMain {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String jsonStr = "{ \"price\": \"4\", \"name\": \"脉动\", \"type\": \"饮料\", \"amount\": 50, "
				+ "\"summary\": \"随时随地，脉动回来\",  "
				+ "\"picture\": \"http://www.jpjy365.com/images/201402/source_img/29726_G_1393285888119.jpg\", "
				+ "\"hot\": true,\"sales\": 100 }";
		ObjectMapper mapper = new ObjectMapper();
		GoodsInfoModel model = mapper.readValue(jsonStr, GoodsInfoModel.class);
		System.out.println("===>" + model.getPrice());

	}

}
