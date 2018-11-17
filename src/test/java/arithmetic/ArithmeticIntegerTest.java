package arithmetic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticIntegerTest {
  ArithmeticInteger zero = ArithmeticInteger.fromLong(0);
  ArithmeticInteger one = ArithmeticInteger.fromLong(1);
  ArithmeticInteger twelve = ArithmeticInteger.fromLong(12);
  ArithmeticInteger three = ArithmeticInteger.fromLong(3);
  ArithmeticInteger eight = ArithmeticInteger.fromLong(8);
  ArithmeticInteger max = ArithmeticInteger.fromLong(0xFFFFFFFFL);

  @Test
  public void plus() {
    assertEquals(20, twelve.plus(eight).getValue());
    assertEquals(max.getValue(), max.plus(zero).getValue());
    assertEquals(zero.getValue(), zero.plus(zero).getValue());
    assertEquals(4, one.plus(three).getValue());
  }

  @Test
  public void minus() {
    assertEquals(4, twelve.minus(eight).getValue());
    assertEquals(max.getValue(), max.minus(zero).getValue());
    assertEquals(zero.getValue(), zero.minus(zero).getValue());
  }

  @Test
  public void times() {
    assertEquals(96, twelve.times(eight).getValue());
    assertEquals(zero.getValue(), max.times(zero).getValue());
    assertEquals(zero.getValue(), zero.times(zero).getValue());
    assertEquals(3, one.times(three).getValue());
  }

  @Test
  public void dividedBy() {
    assertEquals(1, twelve.dividedBy(eight).getValue());
    assertEquals(4, twelve.dividedBy(three).getValue());
    assertEquals(zero.getValue(), zero.dividedBy(twelve).getValue());
    assertEquals(0, one.dividedBy(three).getValue());
  }


  @Test
  public void shiftLeft() {
    System.out.println("--- SHIFT LEFT ---");
    System.out.println("Before: " + twelve.toBinaryString());
    System.out.println("Left 4: " + twelve.shiftLeft(4).toBinaryString());
    System.out.println();

    System.out.println("Before: " + zero.toBinaryString());
    System.out.println("Left 3: " + zero.shiftLeft(3).toBinaryString());
    System.out.println();

    System.out.println("Before: " + max.toBinaryString());
    System.out.println("Left 5: " + max.shiftLeft(5).toBinaryString());
    System.out.println();
  }

  @Test
  public void shiftRight() {
    System.out.println("--- SHIFT RIGHT ---");
    System.out.println("Before: " + twelve.toBinaryString());
    System.out.println("Right4: " + twelve.shiftRight(4).toBinaryString());
    System.out.println();

    System.out.println("Before: " + zero.toBinaryString());
    System.out.println("Right3: " + zero.shiftRight(3).toBinaryString());
    System.out.println();

    System.out.println("Before: " + max.toBinaryString());
    System.out.println("Right5: " + max.shiftRight(5).toBinaryString());
    System.out.println();
  }

  @Test
  public void and() {
    System.out.println("--- AND --- ");
    System.out.println("Value 1: " + max.toBinaryString());
    System.out.println("Value 2: " + zero.toBinaryString());
    System.out.println(" Result: " + max.and(zero).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + max.toBinaryString());
    System.out.println("Value 2: " + max.toBinaryString());
    System.out.println(" Result: " + max.and(max).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + three.toBinaryString());
    System.out.println("Value 2: " + twelve.toBinaryString());
    System.out.println(" Result: " + three.and(twelve).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + zero.toBinaryString());
    System.out.println("Value 2: " + zero.toBinaryString());
    System.out.println(" Result: " + zero.and(zero).toBinaryString());
    System.out.println();
  }

  @Test
  public void or() {
    System.out.println("--- OR --- ");
    System.out.println("Value 1: " + max.toBinaryString());
    System.out.println("Value 2: " + zero.toBinaryString());
    System.out.println(" Result: " + max.or(zero).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + max.toBinaryString());
    System.out.println("Value 2: " + max.toBinaryString());
    System.out.println(" Result: " + max.or(max).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + three.toBinaryString());
    System.out.println("Value 2: " + twelve.toBinaryString());
    System.out.println(" Result: " + three.or(twelve).toBinaryString());
    System.out.println();

    System.out.println("Value 1: " + zero.toBinaryString());
    System.out.println("Value 2: " + zero.toBinaryString());
    System.out.println(" Result: " + zero.or(zero).toBinaryString());
    System.out.println();
  }
}