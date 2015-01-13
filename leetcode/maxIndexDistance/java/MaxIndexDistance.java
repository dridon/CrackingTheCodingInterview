import java.util.Stack;
import java.util.Random;
import java.util.Arrays;
public class MaxIndexDistance {
    public static int getMaxIndexDistance(int arr[]) {
        Stack<Integer> startingPoints = new Stack<Integer>();
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < min) {
                startingPoints.push(new Integer(i));
                min = arr[i];
            }
        }
        int j = arr.length - 1;
        int start = startingPoints.pop();
        int maxDist = Integer.MIN_VALUE;
        int minValue = -1;
        int maxValue = -1;
        while( j > 0 ) {
            if(arr[j] > arr[start] ) {
                if((j - start) > maxDist) {
                    minValue = start;
                    maxValue = j;
                    maxDist = j - start;
                }
                if(startingPoints.isEmpty()) {
                    break;
                }
                start = startingPoints.pop();
            } else {
                j--;
            }
        }  
        System.out.println("Min Value " + minValue + " Max Value " + maxValue);
        return maxDist;
    }

    public static int[] genRandomArray(int num) {
        int[] randArray = new int[num];
        final int max = 100;
        final int min = -100;
        Random random =  new Random();
        for(int i = 0; i < num; i++) {
            randArray[i] = random.nextInt(max - min+ 1) -min ;
        }
        return randArray;
    }
    
    public static void main(String args[]) {
        int arr[] = {4,3,5,2,1,3,2,3};// genRandomArray(10);
        printArray(arr);
        System.out.println(getMaxIndexDistance(arr));
    }

    public static void printArray(int[] arr) {
        Arrays.stream(arr).forEach(p -> System.out.print(p+"\t"));
        System.out.println("");
    }

}
