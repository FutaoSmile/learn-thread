package com.futao.learn.lagou;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @author futao
 * @date 2020/10/26
 */
public class Z5_HashMap {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(1, "v");
        Z5_HashMap.cs();

        Thread thread = new Thread(() -> IntStream.rangeClosed(2, 999_999_999).forEach(x -> map.put(x, "vvv")));
        thread.setDaemon(true);
        thread.start();



        while (true) {
            if (map.get(1) == null) {
                throw new RuntimeException("不应该获取不到啊");
            }
        }
    }

    public void cH() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("","");
    }


    public static void cs() {
        Map<String, String> stringStringMap = Collections.singletonMap("", "");
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(new HashMap<>());
    }
}
