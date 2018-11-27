/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mikevostrikov.ralter.util.java.lang.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author m.vostrikov
 */
public class SetUtil {
    
    public static boolean equals(Set s1, Set s2) {
        return s1.containsAll(s2) && s2.containsAll(s1);
    }

    public static boolean equals(Collection c1, Collection c2) {
        Set s1 = new HashSet<>(c1);
        Set s2 = new HashSet<>(c2);
        return equals(s1, s2);
    }
    
}
