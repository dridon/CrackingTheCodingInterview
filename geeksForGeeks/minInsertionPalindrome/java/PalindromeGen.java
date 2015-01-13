/* Problem : Minimum insertions required to generate a palindrome from 
 * given string. 
 * author: Sameer Jagdale. 
*/
public class PalindromeGen {
	public static int genPalindrome(String str) {
		if(str == null || str.length() == 0) {
			return 0;
		}	
			
		return genPalindrome( str, 0, str.length() -1);	
	}

	public static int genPalindrome(String str, int left, int right) {
		if(left == right) return 0;
		if(str.charAt(left) == str.charAt(right)) return genPalindrome(str, left + 2, right -1);
		return Math.min(genPalindrome(str,left+1, right), genPalindrome(str, left, right-1)) +1;
	}
	
	public static void main(String args[]) {
		System.out.println(genPalindrome("abcd"));
	}
}
