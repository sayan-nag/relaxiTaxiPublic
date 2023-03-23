package Practice.string;

import java.util.Arrays;

public class ArraySort {

    public static void main(String[] args) {
        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}, {1, 1}};
        Arrays.sort(intervals, (x, y) -> {
            if (x[0] != y[0])
                return x[0] - y[0];
            else
                return x[1] - y[1];
        });
        int a = Integer.MAX_VALUE;
        for (int[] val : intervals) {
            System.out.println("[" + val[0] + "," + val[1] + "]");
        }
    }
}
