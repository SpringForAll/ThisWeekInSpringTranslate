package com.spring4all.util;

import java.util.ArrayList;
import java.util.Iterator;

//重写toStirng方法，不要默认的
public class MyArrayList extends ArrayList<String> {
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder buffer = new StringBuilder(size() * 16);
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != this) {
                buffer.append(next);
            } else {
                buffer.append("(this Collection)");
            }
            if (it.hasNext()) {

            }
        }
        return buffer.toString();
    }
}