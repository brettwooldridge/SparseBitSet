package com.zaxxer.sparsebits;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 */
public class PreviousClearBitTest extends Assert {

    SparseBitSet set;

    @Before
    public void setUp() {
        set = new SparseBitSet();
    }

    @Test
    public void munisOne() throws Exception {
        final int ret = set.previousClearBit(-1);

        assertEquals(-1, ret);
    }

    @Test
    public void emptySet() throws Exception {
        final int ret = set.previousClearBit(0);

        assertEquals(0, ret);
    }

    @Test
    public void bottomBit() throws Exception {
        final int ret = set.previousClearBit(1);

        assertEquals(1, ret);
    }

    @Test
    public void sameBit() throws Exception {
        set.flip(12345);
        final int ret = set.previousClearBit(12345);

        assertEquals(12344, ret);
    }

    @Test
    public void noneBelow() throws Exception {
        set.flip(1);
        final int ret = set.previousClearBit(1);

        assertEquals(0, ret);
    }

    @Test
    public void oneBelow() throws Exception {
        set.flip(1);
        final int ret = set.previousClearBit(2);

        assertEquals(2, ret);
    }

    @Test
    public void threeNoSet() throws Exception {
        set.flip(1);
        final int ret = set.previousClearBit(3);

        assertEquals(3, ret);
    }

    @Test
    public void threeSet() throws Exception {
        set.flip(3);
        final int ret = set.previousClearBit(3);

        assertEquals(2, ret);
    }

    @Test
    public void topBit() throws Exception {
        final int i = Integer.MAX_VALUE - 1;
        final int ret = set.previousClearBit(i);

        assertEquals(i, ret);
    }

    @Test
    public void randomSingleEntry() throws Exception {
        final Random random = new Random(0);
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            final int x = Math.abs(random.nextInt() + 1);
            final int ret = set.previousClearBit(x);
            assertEquals("Failed on i = " + i, x, ret);
        }
    }

    @Test
    public void randomMultiEntry() throws Exception {
        final Random random = new Random(0);
        final Set<Integer> values = new HashSet<Integer>();
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            for (int j = 0; j < 1000; ++j) {
                final int x = Math.abs(random.nextInt() + 1);
                set.flip(x);
                values.add(x);
            }
            final int x = Math.abs(random.nextInt() + 1);
            int expected = x;
            while (values.contains(expected)) {
                --expected;
            }
            final int ret = set.previousClearBit(x);
            assertEquals("Failed on i = " + i + " x = " + x, expected, ret);
            values.clear();
        }
    }
}