package rsol.example.jsch.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
public class MessageUtil {

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseList(Object message){
		return parse(message, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parse(Object message) {
		return parse(message, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T parse(Object obj, Class<T> objClass) {
		T instance = null;
		try {
			Object o = null;
			if(obj instanceof String)
			   o = new ObjectMapper().readValue((String)obj, objClass);
			else
			   o = new ObjectMapper().convertValue(obj, objClass);
			Objects.requireNonNull(o);
			instance = (T) o;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unparseable message or data ", e);
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> parseList(Object obj, Class<T> objClass) {
		Iterable<T> instance = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
		    CollectionType javaType = mapper.getTypeFactory()
		    	      .constructCollectionType(List.class, objClass);
			Object o = null;
			if(obj instanceof String)
			   o = new ObjectMapper().readValue((String)obj, javaType);
			else
			   o = new ObjectMapper().convertValue(obj, javaType);
			Objects.requireNonNull(o);
			instance = (Iterable<T>) o;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unparseable message or data ", e);
		}
		return instance;
	}
	
	public static String parseEventName(String message) {
		Map<String, Object> map = parse(message);
		return (String) map.get("name");
	}
	
	public static String toJson(Object obj) {
		String json = null;
		try {
			if (obj != null && obj instanceof String)
				json = obj.toString();
			else
				json = new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			log.info("Failed to convert object to json", e);
		}
		return json;
	}
}
