package xinQing.springsecurity.util;

import com.google.gson.Gson;

/**
 * JSON工具类
 *
 * Created by xinQing on 2016/11/28.
 */
public class JSONUtil {

    /**
     * 转为JSON字符串
     *
     * @param t   对象
     * @param <T> 泛型
     * @return JSON字符串
     */
    public static <T> String toJSONString(T t) {
        if (t instanceof String) {
            return (String) t;
        }
        Gson gson = new Gson();
        return gson.toJson(t);
    }
}
