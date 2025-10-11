package org.aston.learning.stage2.collection;

import java.util.Objects;

public class CollisionKey {
    private final String name;
    private final int targetHash; // Будем контролировать хеш вручную

    public CollisionKey(String name, int targetHash) {
        this.name = name;
        this.targetHash = targetHash;
    }

    @Override
    public int hashCode() {
        return targetHash; // Всегда возвращаем одинаковый хеш!
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CollisionKey that = (CollisionKey) obj;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return name + "(hash=" + targetHash + ")";
    }
}