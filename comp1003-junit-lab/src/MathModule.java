
public class MathModule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//int test = myMultiply(-2_000_000_000,6);
			int test = myDivide(6,0);			
			System.out.println(test);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	public static int myMultiply(int firstNum, int secondNum) throws Exception {
		
		long newAnswer = (long)firstNum*secondNum;
		
		if (newAnswer > Integer.MAX_VALUE) {
			throw new Exception("Number too big");
		}else if (newAnswer < Integer.MIN_VALUE) {
			throw new Exception("Number too small");
		}
		
		return firstNum * secondNum;
	}
	
	public static int myDivide(int firstNum, int secondNum) throws Exception {
			
			//long newAnswer = (long)firstNum/secondNum;
			
			if (secondNum == 0) {
				throw new Exception("Divided by 0");
			} 
			
			long newAnswer = (long)firstNum/secondNum;
			 if (newAnswer > Integer.MAX_VALUE) {
				throw new Exception("Number too big");
			}else if (newAnswer < Integer.MIN_VALUE ) {
				throw new Exception("Number too small");
			} //else if (secondNum == 0) {
				//throw new Exception("Divided by 0");
			//}
			
			return firstNum / secondNum;
		}

}
