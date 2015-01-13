import java.util.Random;
import java.util.Arrays;
public class MaxDistance {
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
    
    public static int getMaxDifference(int[] arr) {
        int maxDiff = Integer.MIN_VALUE;
        int currNum = arr[0];
        int min = 0;
        int max = 0;
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] < currNum) {
                min = i;
                currNum = arr[i];
            } 
            if(arr[i] >= currNum && ((arr[i] - currNum) > maxDiff)) {
                maxDiff = arr[i] - currNum; 
                max = i;
            } 
        }
        System.out.println("max " + max + " min "+ min);
        return max - min;
    }
    
    public static void main(String args[]) {
        int[] arr = genRandomArray(10);
        printArray(arr);
        System.out.println("Difference" + getMaxDifference(arr));
    }
    public static void printArray(int[] arr) {
        Arrays.stream(arr).forEach(p -> System.out.print(p+"\t"));
        System.out.println("");
    }
}
