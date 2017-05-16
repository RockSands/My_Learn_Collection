package reflect.clazz;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import reflect.com.bean.Person;

/**
 * Class验证
 * 
 * @author chenkw
 *
 */
public class Main {

	public static void main(String[] args) {
		/*
		 * 初始类
		 */
		List<Person> personList = new ArrayList<Person>();
		/*
		 * 获取Class对象,<>号内是泛型,可以限制类型
		 */
		Class<?> personClazz = personList.getClass();
		Class<? extends List> listClazz = ArrayList.class;
		// 类的名称
		System.out.println(personClazz.getName());
		System.out.println(listClazz.getName());
		// 类的简易名称(除去包名)
		System.out.println(personClazz.getSimpleName());
		// 获取类的父类定义,如果无超类则返回Object.
		Type genericType = personList.getClass().getGenericSuperclass();
		System.out.println(genericType.getTypeName());// java.util.AbstractList<E>
		System.out.println(Person.class.getGenericSuperclass().getTypeName());// java.util.AbstractList<E>
		/*
		 * 获取通用泛型的错误样例()
		 */
		Type type = personClazz.getGenericSuperclass();
		// 返回java.util.AbstractList<E>,其实返回的是Class的定义文件,由于未初始化所以E并未被定义.
		System.out.println(type.getTypeName());
		/*
		 * 获取通用泛型的正确样例,参考Gson
		 */
		// 正确的方式:参考Gson获取方式,由于TypeToken初始化,所以通用类型已被定义.
		TypeToken<List<Person>> token = new TypeToken<List<Person>>() {
		};
		// reflect.clazz.Main.reflect.clazz.Main$TypeToken<java.util.List<reflect.com.bean.Person>>
		System.out.println(token.getType());
		// 1
		System.out.println(token.getType().getActualTypeArguments().length);
		// 类型java.util.List<reflect.com.bean.Person> Type是可以保存泛型类型的,其无法擦除
		System.out.println(token.getType().getActualTypeArguments()[0]);
	}

	/**
	 * 参考Gson,获取泛型信息
	 * 
	 * @author chenkw
	 *
	 * @param <T>
	 */
	public static class TypeToken<T> {
		final ParameterizedType type;

		protected TypeToken() {
			type = (ParameterizedType) this.getClass().getGenericSuperclass();
		}

		ParameterizedType getType() {
			return type;
		}

		Type[] getTypeArguments() {
			return type.getActualTypeArguments();
		}
	}

}