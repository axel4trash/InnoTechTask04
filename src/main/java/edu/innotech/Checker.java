package edu.innotech;

import java.util.Set;

public interface Checker<T> {
    <T> boolean check(T data);
}
