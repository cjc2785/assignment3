package com.ss.assignment3.junit;


import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class LineTest {
	
	@Test 
	public void slope() {
		
		Line line = new Line(2, 3, 4, 9);
		
		double actual = line.getSlope();
		
		double delta = 0.00001;
		double expected = 3;
		
		assertEquals(expected, actual, delta);
	}
	
	@Test
	public void slopeX1EqualsX2ShouldThrow() {
		
		Line line = new Line(1, 2, 1, 3);
		
		try {
			line.getSlope();
			fail();
		} catch (ArithmeticException e) {
			
		}
	}
	
	
	
	@Test
	public void distance() {
		
		Line line = new Line(1,2,4,6);
		
		double actual = line.getDistance();
		
		double expected = 5;
		
		double delta = 0.00001;
		
		assertEquals(expected, actual, delta);
	}
	
	
	@Test
	public void parallelShouldReturnTrueForParallelLines() {
		
		//Both lines have a slope of 3
		Line line1 = new Line(2, 3, 4, 9);
		Line line2 = new Line(0, 0, 3, 9);
		
		boolean actual = line1.parallelTo(line2);
		
		assertEquals(true, actual);
	}
	
	@Test
	public void parallelShouldReturnFalseForNonParallelLines() {
		
		//Slope of 5
		Line line1 = new Line(0, 0, 1, 5);
		
		//Slope of 3
		Line line2 = new Line(0, 0, 1, 3);
		
		boolean actual = line1.parallelTo(line2);
		
		assertEquals(false, actual);
	}
}