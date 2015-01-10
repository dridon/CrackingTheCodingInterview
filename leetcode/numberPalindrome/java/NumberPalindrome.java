public class NumberPalindrome {
	
	public static boolean isPalindrome(int num) {
		int numDigits = getNumDigits(num);
		int div = (int)Math.pow(10,(numDigits - 1));
		while (num != 0) {
			if(getLeftDigit(num, div) != getRightDigit(num)) {
				return false;	
			}
			num = num % div;
			num = num / 10;
			div = div / 100;
		}
		return true;
	}

	public static int getLeftDigit(int num, int div) {
		return num / div;
	}
	
	public static int getRightDigit(int num) {
		return num % 10;
	}
	
	public static int getNumDigits(int num) {
		if(num == 0) return 1;
		int count = 0;
		while (num != 0) {
			num = num / 10;
			count++;
		}
		return count;		
	}
	
	public static void main(String args[]) {
		System.out.println(NumberPalindrome.isPalindrome(4994));
		System.out.println(NumberPalindrome.isPalindrome(49194));
		System.out.println(NumberPalindrome.isPalindrome(-49194));
		System.out.println(NumberPalindrome.isPalindrome(33));
		System.out.println(NumberPalindrome.isPalindrome(0));
		System.out.println(NumberPalindrome.isPalindrome(23));
		System.out.println(NumberPalindrome.isPalindrome(-49193));
	}
}
