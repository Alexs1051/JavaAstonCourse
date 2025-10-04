package org.aston.learning.stage2.collection;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAX_CAPACITY = 1 << 30;

    private Entry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    // Внутренний класс для хранения пар ключ-значение
    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        final int hash;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    // Конструкторы
    public CustomHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.loadFactor = loadFactor;
        int capacity = roundUpToPowerOf2(initialCapacity);
        this.threshold = (int) (capacity * loadFactor);
        this.table = new Entry[capacity];
    }

    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public CustomHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    // Вспомогательный метод для округления до степени двойки
    private int roundUpToPowerOf2(int number) {
        if (number > MAX_CAPACITY) {
            return MAX_CAPACITY;
        }
        if (number < 1) {
            return 1;
        }
        return Integer.highestOneBit(number - 1) << 1;
    }

    // Вычисление хеша для ключа
    private int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        // Дополнительное перемешивание для уменьшения коллизий (магия)
        // return h;
        return h ^ (h >>> 16);
    }

    // Получение индекса в таблице
    private int indexFor(int hash, int length) {
        // Вместо hash % length, т.к. битовые операции выполняются быстрее деления (магия),
        // length - должна быть степень двойки (типа этого: 16 = 2^4)
        // return hash % length;
        return hash & (length - 1);
    }

    /**
     * Добавляет пару ключ-значение в Map
     */
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

    // Обработка null ключа
    private V putForNullKey(V value) {
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }
        addEntry(0, null, value, 0);
        return null;
    }

    // Добавление новой записи
    private void addEntry(int hash, K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(hash, key, value, e);

        if (size++ >= threshold) {
            resize(2 * table.length);
        }
    }

    // Изменение размера таблицы
    private void resize(int newCapacity) {
        if (table.length == MAX_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry<K, V>[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    // Перераспределение записей в новую таблицу
    private void transfer(Entry<K, V>[] newTable) {
        int newCapacity = newTable.length;
        for (Entry<K, V> e : table) {
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(e.hash, newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
    }

    /**
     * Получает значение по ключу
     */
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

    // Получение значения для null ключа
    private V getForNullKey() {
        for (Entry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                return e.value;
            }
        }
        return null;
    }

    /**
     * Удаляет пару ключ-значение по ключу
     */
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

    // Удаление для null ключа
    private V removeForNullKey() {
        Entry<K, V> prev = table[0];
        Entry<K, V> e = prev;

        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.key == null) {
                size--;
                if (prev == e) {
                    table[0] = next;
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

    /**
     * Проверяет наличие ключа в Map
     */
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Возвращает количество элементов в Map
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли Map
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Очищает Map
     */
    public void clear() {
        table = new Entry[table.length];
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Entry<K, V> entry : table) {
            for (Entry<K, V> e = entry; e != null; e = e.next) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(e.key).append("=").append(e.value);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
