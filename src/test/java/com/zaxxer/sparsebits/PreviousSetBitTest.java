package com.zaxxer.sparsebits;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class PreviousSetBitTest extends Assert {

    SparseBitSet set;

    @Before
    public void setUp() {
        set = new SparseBitSet();
    }

    @Test
    public void emptySet() throws Exception {
        final int ret = set.previousSetBit(0);

        assertEquals(-1, ret);
    }

    @Test
    public void bottomBit() throws Exception {
        set.flip(0);
        final int ret = set.previousSetBit(0);

        assertEquals(0, ret);
    }

    @Test
    public void betweenTwo() throws Exception {
        set.flip(4);
        set.flip(8);
        final int ret = set.previousSetBit(5);

        assertEquals(4, ret);
    }

    @Test
    public void sameBit() throws Exception {
        set.flip(12345);
        final int ret = set.previousSetBit(12345);

        assertEquals(12345, ret);
    }

    @Test
    public void noneBelow() throws Exception {
        set.flip(1);
        final int ret = set.previousSetBit(0);

        assertEquals(-1, ret);
    }

    @Test
    public void oneBelow() throws Exception {
        set.flip(1);
        final int ret = set.previousSetBit(2);

        assertEquals(1, ret);
    }

    @Test
    public void twoBelow() throws Exception {
        set.flip(1);
        final int ret = set.previousSetBit(3);

        assertEquals(1, ret);
    }

    @Test
    public void topBit() throws Exception {
        final int i = Integer.MAX_VALUE - 1;
        set.flip(i);
        final int ret = set.previousSetBit(i);

        assertEquals(i, ret);
    }

    @Test
    public void randomSingleEntry() throws Exception {
        final int max = Integer.MAX_VALUE - 1;
        final Random random = new Random(0);
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            final int x = Math.abs(random.nextInt() + 1);
            set.flip(x);
            final int ret = set.previousSetBit(max);
            assertEquals("Failed on i = " + i, x, ret);
        }
    }

    @Test
    public void randomMultiEntry() throws Exception {
        final Random random = new Random(0);
        final List<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            for (int j = 0; j < 1000; ++j) {
                final int x = Math.abs(random.nextInt() + 1);
                set.flip(x);
                values.add(x);
            }
            final int x = Math.abs(random.nextInt() + 1);
            Collections.sort(values);
            int expected = -1;
            for (final Integer val : values) {
                if (val > x) {
                    break;
                }
                expected = val;
            }
            final int ret = set.previousSetBit(x);
            assertEquals("Failed on i = " + i, expected, ret);
            values.clear();
        }
    }
}