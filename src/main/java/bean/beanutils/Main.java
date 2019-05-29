package bean.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args)
			throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		GoodsInfoModel model = new GoodsInfoModel();
		GoodsInfoModel2 model2 = new GoodsInfoModel2();
		model.setAmount("1");
		model.setHot(false);
		model.setName("I AM");
		model.setPrice(2);
		model.setSales(3D);
		model.setTime(new Date());
		BeanUtils.copyProperties(model2, model);
		System.out.println("=0=>" + mapper.writeValueAsString(model));
		System.out.println("=1=>" + mapper.writeValueAsString(model2));
		// 结论: 类型一致 名称一致 Date不支持
		System.out.println("-------------------------------------------------");
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, Date.class);
	}

}
