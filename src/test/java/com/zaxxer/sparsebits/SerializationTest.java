package com.zaxxer.sparsebits;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests serialization of SparseBitSet of different number of set bits,
 * from 1 to MAX_BITS.
 */
public class SerializationTest {

  private static final Path DST = Paths.get(System.getProperty("java.io.tmpdir"), "sbs.ser");
  private static final int MAX_BITS = Integer.parseInt(System.getProperty("test.max_bits", "100"));

  private int numOfBits;
  private int[] data;
  private SparseBitSet sparseBitSet;

  @Test
  public void testFromOneToNBits() {
    IntStream.range(1, MAX_BITS + 1).forEach(this::execute);
  }

  private void execute(int numOfBits) {
    try {
      this.numOfBits = numOfBits;
      this.sparseBitSet = new SparseBitSet();

      createData();
      fillSparseBitSet();
      write();

      this.sparseBitSet = null;
      sortData();
      read();

      verify();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void verify() {
    for (int i = sparseBitSet.nextSetBit(0), j = 0; i >= 0; i = sparseBitSet.nextSetBit(i + 1), j++) {
      assertEquals(data[j], i);
    }
  }

  private void createData() {
    Set<Integer> s = new HashSet<>();
    Random r = new Random();
    while (s.size() < numOfBits) {
      s.add(r.nextInt(Integer.MAX_VALUE));
    }
    data = s.stream().mapToInt(i -> i).toArray();
  }

  private void sortData() {
    Arrays.sort(data);
  }

  private void fillSparseBitSet() {
    IntStream.of(data).forEach(sparseBitSet::set);
  }

  private void write() throws Exception {
    try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(DST.toFile()))) {
      s.writeObject(sparseBitSet);
    }
  }

  private void read() throws Exception {
    try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(DST.toFile()))) {
      sparseBitSet = (SparseBitSet) s.readObject();
    }
  }
}
