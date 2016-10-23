package zsbpj.lccpj.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtils {

	public static String createGsonString(Object object) {
		Gson gson = new Gson();
		String gsonString = gson.toJson(object);
		return gsonString;
	}

	public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		T t = gson.fromJson(gsonString, cls);
		return t;
	}

	public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list_person = gson.fromJson(gsonString,
				new TypeToken<List<T>>() {
				}.getType());
		return list_person;
	}

	public static List<Map<String, Object>> changeGsonToListMaps(String gsonString) {
		List<Map<String, Object>> list = null;
		Gson gson = new Gson();
		list = gson.fromJson(gsonString,
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());
		return list;
	}
	public static <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {
		List<T> lst =  new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for(final JsonElement elem : array){
			lst.add(new Gson().fromJson(elem, clazz));
		}
		return lst;
	}

}
