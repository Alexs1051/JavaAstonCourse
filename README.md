# ASTON COURSE HOMEWORK

## Содержание
1. [Git. Алгоритмы и структуры данных](#git-алгоритмы-и-структуры-данных)

---

## Git. Алгоритмы и структуры данных

### Цель работы
Необходимо написать собственную реализацию HashMap. Обязательные методы: get, put, remove.

### Задачи
- [x] Реализовать класс `CustomHashMap`
- [x] Поддержать методы: `put()`, `get()`, `remove()`
- [x] Обеспечить обработку коллизий
- [x] Реализовать автоматическое расширение таблицы

### Реализация

#### Основная структура:
```java
public class CustomHashMap<K, V> {
    private Entry<K, V>[] table;
    private int size;
    private float loadFactor;
    private int threshold;
    
    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        final int hash;
    }
}
```
#### Метод put:
```java
public V put(K key, V value) {
    if (key == null) {
        return putForNullKey(value);
    }

    int hash = hash(key);
    int index = indexFor(hash, table.length);

    // Проверяем, существует ли уже такой ключ
    for (Entry<K, V> e = table[index]; e != null; e = e.next) {
        if (e.hash == hash && (key == e.key || key.equals(e.key))) {
            V oldValue = e.value;
            e.value = value;
            return oldValue;
        }
    }

    // Добавляем новую запись
    addEntry(hash, key, value, index);
    return null;
}
```
#### Метод get:
```java
public V get(Object key) {
    if (key == null) {
        return getForNullKey();
    }

    int hash = hash(key);
    int index = indexFor(hash, table.length);

    for (Entry<K, V> e = table[index]; e != null; e = e.next) {
        if (e.hash == hash && (key == e.key || key.equals(e.key))) {
            return e.value;
        }
    }
    return null;
}
```
#### Метод remove:
```java
public V remove(Object key) {
    if (key == null) {
        return removeForNullKey();
    }

    int hash = hash(key);
    int index = indexFor(hash, table.length);
    Entry<K, V> prev = table[index];
    Entry<K, V> e = prev;

    while (e != null) {
        Entry<K, V> next = e.next;
        if (e.hash == hash && (key == e.key || key.equals(e.key))) {
            size--;
            if (prev == e) {
                table[index] = next;
            } else {
                prev.next = next;
            }
            return e.value;
        }
        prev = e;
        e = next;
    }
    return null;
}
```
#### Тестирование:
```java
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
```

#### Результат

<img width="1071" height="364" alt="Screenshot_2" src="https://github.com/user-attachments/assets/b8bbe6ba-4dad-492b-8bc1-4387887d94c8" />

[⬆️ К содержанию](#содержание)
