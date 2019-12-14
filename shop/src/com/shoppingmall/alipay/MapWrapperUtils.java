package com.shoppingmall.alipay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bert Q
 * ClassName : MapChangeUtils
 * Description TODO
 */
public class MapWrapperUtils {
    public static Map<String, String> change(Map<String, String[]> reqMap) {
        Map<String, String> ret = new HashMap<>(reqMap.size());
        for (String k : reqMap.keySet()) {
            String v = reqMap.get(k)[0];
            ret.put(k, v);
        }
        return ret;
    }
}
