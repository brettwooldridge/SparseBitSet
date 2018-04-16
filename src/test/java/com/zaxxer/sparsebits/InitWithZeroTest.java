package com.zaxxer.sparsebits;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InitWithZeroTest {
    private SparseBitSet bitset;

    @Before
    public void setup() {
        bitset = new SparseBitSet(0);
    }

    @Test
    public void testPreviousSetBit() {
        Assert.assertEquals(-1, bitset.previousSetBit(0));
    }

    @Test
    public void testPreviousClearBit() {
        Assert.assertEquals(0, bitset.previousClearBit(0));
    }

    @Test
    public void testNextSetBit() {
        Assert.assertEquals(-1, bitset.nextSetBit(0));
    }

    @Test
    public void testNextClearBit() {
        Assert.assertEquals(0, bitset.nextClearBit(0));
    }

    @Test
    public void testClone() {
        Assert.assertEquals(-1, bitset.clone().nextSetBit(0));
    }

    @Test
    public void testAnd() {
        bitset.and(new SparseBitSet());
    }
}
