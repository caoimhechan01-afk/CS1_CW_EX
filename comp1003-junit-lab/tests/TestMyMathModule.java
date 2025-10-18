import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class TestMyMathModule {

	private static int input1;
	private static int input2;
	private static int input3;
	private static int input4;

	@BeforeAll
	static void setup() {
		input1 = 6;
		input2 = 3;
		input3 = 2000000000;
		input4 = -2000000000;
	}
	
	@Test
	@Disabled
	void test() {
		fail("Not yet implemented");
		//System.out.println("Not yet implemented");
	}
	
	@Test
	void test1() {
		
		int myAnswer = 0;
		
		try {
			myAnswer = MathModule.myDivide(input1, 0);
		} catch (Exception e) {
			if (e.getClass()==Exception.class) {
				return; //it passed – acted correctly.
			}
		}
		assertEquals(2, myAnswer);
	}
	void dividebyzero() {
		
		int myAnswer = 0;
		
		try {
			myAnswer = MathModule.myDivide(input1, input2);
		} catch (Exception e) {
			if (e.getClass()==Exception.class) {
				return; //it passed – acted correctly.
			}
		}
		assertEquals(2, myAnswer);
		
	}
	void testError() {
		
		int myAnswer = 0;
		
		try {
			myAnswer = MathModule.myDivide(input1,input2);
		} catch (Exception e) {
			if (e.getClass()==Exception.class) {
				return; //it passed – acted correctly.
			}
		}
			fail("no error thrown, hashtag sadface");
		}
}
