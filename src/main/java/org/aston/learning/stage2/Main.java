package org.aston.learning.stage2;

import org.aston.learning.stage2.collection.CollisionKey;
import org.aston.learning.stage2.collection.CustomHashMap;

public class Main {
    public static void main(String[] args) {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        System.out.println("======= Тестирование функции округление значения до степени двойки =======\n");

        System.out.println("roundUpToPowerOf2(0): " + map.roundUpToPowerOf2(0));
        System.out.println("roundUpToPowerOf2(1): " + map.roundUpToPowerOf2(1));
        System.out.println("roundUpToPowerOf2(2): " + map.roundUpToPowerOf2(2));
        System.out.println("roundUpToPowerOf2(3): " + map.roundUpToPowerOf2(3));
        System.out.println("roundUpToPowerOf2(7): " + map.roundUpToPowerOf2(7));
        System.out.println("roundUpToPowerOf2(8): " + map.roundUpToPowerOf2(8));
        System.out.println("roundUpToPowerOf2(9): " + map.roundUpToPowerOf2(9));

        System.out.println("\n======================= Тестирование CustomHashMap =======================\n");

        // Тестирование put
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        System.out.println("После добавления: " + map);

        // Тестирование get
        System.out.println("get('two'): " + map.get("two"));
        System.out.println("get(null): " + map.get(null));
        System.out.println("get('four'): " + map.get("four"));

        // Тестирование remove
        System.out.println("\nУдаление элемента по ключу 'two': remove('two'): " + map.remove("two"));
        System.out.println("После удаления 'two': " + map);

        // Тестирование размера
        System.out.println("\nsize: " + map.size());
        System.out.println("isEmpty: " + map.isEmpty());

        // Тестирование containsKey
        System.out.println("\ncontainsKey('three'): " + map.containsKey("three"));
        System.out.println("containsKey('four'): " + map.containsKey("four"));
        System.out.println("containsKey(null): " + map.containsKey(null));

        // Null ключ с null значением
        map.put(null, null);
        System.out.println("\nNull ключ с null значением");
        System.out.println("  containsKey(null): " + map.containsKey(null)); // true
        System.out.println("  get(null): " + map.get(null)); // null

        // Null ключ с не-null значением
        map.put(null, 0);
        System.out.println("Null ключ с не-null значением");
        System.out.println("  containsKey(null): " + map.containsKey(null)); // true
        System.out.println("  get(null): " + map.get(null)); // "not null"

        // Не-null ключ с null значением
        map.put("key1", null);
        System.out.println("Не-null ключ с null значением");
        System.out.println("  containsKey('key1'): " + map.containsKey("key1")); // true
        System.out.println("  get('key1'): " + map.get("key1")); // null

        map.put("a", 1);
        System.out.println("\nПосле добавления 'a = 1': " + map);
        map.remove("a");
        System.out.println("После удаления 'a': " + map);
        map.put("a", 2);
        System.out.println("После повторного добавления 'a = 2': " + map);

        map.put("b", 1);
        System.out.println("После добавления 'b = 1': " + map);
        map.put("b", 2);
        System.out.println("Перезапись 'b = 2': " + map);

        System.out.println("\n======================== Тестирование на коллизию ========================\n");

        CustomHashMap<CollisionKey, Integer> collisionMap = new CustomHashMap<>();

        CollisionKey key1 = new CollisionKey("first", 42);
        CollisionKey key2 = new CollisionKey("second", 42);
        CollisionKey key3 = new CollisionKey("third", 42);

        collisionMap.put(key1, 25);
        collisionMap.put(key2, 45);
        collisionMap.put(key3, 65);

        System.out.println("Размер map: " + collisionMap.size());

        // Проверяем, что все значения доступны
        System.out.println("get(key1): " + collisionMap.get(key1));
        System.out.println("get(key2): " + collisionMap.get(key2));
        System.out.println("get(key3): " + collisionMap.get(key3));

        // Проверяем containsKey
        System.out.println("containsKey(key1): " + collisionMap.containsKey(key1));
        System.out.println("containsKey(key2): " + collisionMap.containsKey(key2));

        // Обновляем значение в коллизии
        Integer oldValue = collisionMap.put(key1, 999);
        System.out.println("\nПосле map.put(keyA, 999)");
        System.out.println("Возвращенное старое значение: " + oldValue); // 1
        System.out.println("Новое get(keyA): " + collisionMap.get(key1)); // 999
        System.out.println("get(keyB) не изменился: " + collisionMap.get(key2)); // 2

        // Удаляем из СЕРЕДИНЫ цепочки
        Integer removed = collisionMap.remove(key2);
        System.out.println("\nПосле удаления key2");
        System.out.println("Удаленное значение: " + removed);
        System.out.println("Новый размер: " + collisionMap.size());
        System.out.println("get(key1): " + collisionMap.get(key1)); // value1 (все еще доступен)
        System.out.println("get(key2): " + collisionMap.get(key2)); // null (удален)
        System.out.println("get(key3): " + collisionMap.get(key3)); // value3 (все еще доступен)
        System.out.println("containsKey(key2): " + collisionMap.containsKey(key2));
    }
}