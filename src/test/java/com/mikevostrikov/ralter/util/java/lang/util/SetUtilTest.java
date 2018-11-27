/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mikevostrikov.ralter.util.java.lang.util;

import com.mikevostrikov.ralter.util.java.lang.util.SetUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author m.vostrikov
 */
public class SetUtilTest {
    
    public void testEqualsSetSet() {
        Set<Long> s1 = new HashSet(Arrays.asList(1L,2L,3L));
        Set<Long> s2 = new TreeSet(Arrays.asList(1L,2L,3L));
        assertTrue(SetUtil.equals(s1, s2));
    }
    
    public void testEqualsCollectionCollection() {
        Collection<Long> c1 = new HashSet(Arrays.asList(1L,2L,3L));
        Collection<Long> c2 = Arrays.asList(1L,1L,2L,3L);
        assertTrue(SetUtil.equals(c1, c2));
    }
    
}
