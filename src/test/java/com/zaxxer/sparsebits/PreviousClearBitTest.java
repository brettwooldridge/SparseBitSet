package com.zaxxer.sparsebits;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.zaxxer.sparsebits.SparseBitSet.SHIFT1;
import static com.zaxxer.sparsebits.SparseBitSet.SHIFT2;
import static com.zaxxer.sparsebits.SparseBitSet.SHIFT3;

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
    public void minusOne() {
        final int ret = set.previousClearBit(-1);

        assertEquals(-1, ret);
    }

    @Test
    public void emptySet() {
        final int ret = set.previousClearBit(0);

        assertEquals(0, ret);
    }

    @Test
    public void bottomBit() {
        final int ret = set.previousClearBit(1);

        assertEquals(1, ret);
    }

    @Test
    public void sameBit() {
        set.set(12345);
        final int ret = set.previousClearBit(12345);

        assertEquals(12344, ret);
    }

    @Test
    public void level1Miss() {
        final int i = (1 << (SHIFT1 + SHIFT3));
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level1MissPlus1() {
        final int i = (1 << (SHIFT1 + SHIFT3)) + 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level1MissMinus1() {
        final int i = (1 << (SHIFT1 + SHIFT3)) - 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level2Miss() {
        final int i = (1 << (SHIFT3 + SHIFT2));
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level2MissPlus1() {
        final int i = (1 << (SHIFT3 + SHIFT2)) + 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level2MissMinus1() {
        final int i = (1 << (SHIFT3 + SHIFT2)) - 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level3Miss() {
        final int i = (1 << SHIFT3);
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level3MissPlus1() {
        final int i = (1 << SHIFT3) + 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void level3MissMinus1() {
        final int i = (1 << SHIFT3) - 1;
        set.set(i);
        final int ret = set.previousClearBit(i);

        assertEquals(i - 1, ret);
    }

    @Test
    public void noneBelow() {
        set.set(1);
        final int ret = set.previousClearBit(1);

        assertEquals(0, ret);
    }

    @Test
    public void oneBelow() {
        set.set(1);
        final int ret = set.previousClearBit(2);

        assertEquals(2, ret);
    }

    @Test
    public void threeNoSet() {
        set.set(1);
        final int ret = set.previousClearBit(3);

        assertEquals(3, ret);
    }

    @Test
    public void threeSet() {
        set.set(3);
        final int ret = set.previousClearBit(3);

        assertEquals(2, ret);
    }

    @Test
    public void topBit() {
        final int i = Integer.MAX_VALUE - 1;
        final int ret = set.previousClearBit(i);

        assertEquals(i, ret);
    }

    @Test
    public void randomSingleEntry() {
        final Random random = new Random(0);
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            final int x = Math.abs(random.nextInt() + 1);
            final int ret = set.previousClearBit(x);
            assertEquals("Failed on i = " + i, x, ret);
        }
    }

    @Test
    public void randomMultiEntry() {
        final Random random = new Random(0);
        final Set<Integer> values = new HashSet<>();
        for (int i = 0; i < 10000; ++i) {
            set = new SparseBitSet();
            for (int j = 0; j < 1000; ++j) {
                final int x = Math.abs(random.nextInt() + 1);
                set.set(x);
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

    @Test
    public void bug15() {
        set.set(1);
        set.set(64);
        assertEquals(63, set.previousClearBit(64));
        set.clear(0);
        set.set(1);
        assertEquals(63, set.previousClearBit(64));
    }
}