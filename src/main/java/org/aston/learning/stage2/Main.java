package org.aston.learning.stage2;

import org.aston.learning.stage2.collection.CustomHashMap;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        System.out.println("======================= Тестирование CustomHashMap =======================");

        // Тестирование put
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put(null, 0);
        System.out.println("\nПосле добавления: " + map);

        // Тестирование get
        System.out.println("get('two'): " + map.get("two"));
        System.out.println("get(null): " + map.get(null));
        System.out.println("get('four'): " + map.get("four"));

        // Тестирование remove
        System.out.println("Удаление элемента по ключу 'two': remove('two'): " + map.remove("two"));
        System.out.println("\nПосле удаления 'two': " + map);

        // Тестирование размера
        System.out.println("size: " + map.size());
        System.out.println("isEmpty: " + map.isEmpty());

        // Тестирование containsKey
        System.out.println("containsKey('three'): " + map.containsKey("three"));
        System.out.println("containsKey('two'): " + map.containsKey("two"));
    }
}