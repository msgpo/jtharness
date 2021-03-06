/*
 * $Id$
 *
 * Copyright (c) 2001, 2009, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.sun.javatest.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * A map whose entries are stored in a parent map by prefixing
 * the key names with a specific string.
 */
public class PrefixMap<V> implements Map<String, V> {
    private Map<String, V> map;
    private String prefix;

    /**
     * Create a map whose entries are stored in a parent map
     * by prefixing the key names with a specific string.
     *
     * @param map    the parent map
     * @param prefix the prefix with which to prefix the entries in the
     *               parent map
     */
    public PrefixMap(Map<String, V> map, String prefix) {
        this.map = map;
        this.prefix = prefix + ".";
    }

    @Override
    public void clear() {
        for (Iterator<String> i = map.keySet().iterator(); i.hasNext(); ) {
            String key = i.next();
            if (key.startsWith(prefix)) {
                i.remove();
            }
        }
    }

    /**
     * Get the prefix that this map is applying.
     * This is the value that was supplied to the constructor.  This class
     * reserves the right to return a String which is not reference equivalent
     * to the given string.
     *
     * @return The prefix string, which may be a zero length string.
     */
    public String getPrefix() {
        // protect against zero length prefix
        if (prefix.length() > 1) {
            return prefix.substring(0, prefix.length() - 1);
        } else {
            return "";
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(prefix + key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Entry<String, V> e : map.entrySet()) {
            String key = e.getKey();
            if (key.startsWith(prefix) && e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<String, V>> entrySet() {
        Map<String, V> m = new HashMap<>();
        for (Entry<String, V> e : map.entrySet()) {
            String key = e.getKey();
            if (key.startsWith(prefix)) {
                m.put(key.substring(prefix.length()), e.getValue());
            }
        }
        return m.entrySet();
    }

    @Override
    public V get(Object key) {
        return map.get(prefix + key);
    }

    @Override
    public int hashCode() {
        return map.hashCode() + prefix.hashCode();
    }

    @Override
    public boolean isEmpty() {
        for (String key : map.keySet()) {
            if (key.startsWith(prefix)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<String> keySet() {
        Set<String> s = new HashSet<>();
        for (String key : map.keySet()) {
            if (key.startsWith(prefix)) {
                s.add(key.substring(prefix.length()));
            }
        }
        return s;
    }

    @Override
    public V put(String key, V value) {
        return map.put(prefix + key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> t) {
        for (Entry<? extends String, ? extends V> e : t.entrySet()) {
            String key = e.getKey();
            put(key, e.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        return map.remove(prefix + key);
    }

    @Override
    public int size() {
        int n = 0;
        for (String key : map.keySet()) {
            if (key.startsWith(prefix)) {
                n++;
            }
        }
        return n;
    }

    @Override
    public Collection<V> values() {
        Collection<V> c = new Vector<>();
        for (Entry<String, V> e : map.entrySet()) {
            String key = e.getKey();
            if (key.startsWith(prefix)) {
                c.add(e.getValue());
            }
        }
        return c;
    }
}
