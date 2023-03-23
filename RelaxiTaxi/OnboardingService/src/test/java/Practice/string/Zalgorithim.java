package Practice.string;

public class Zalgorithim {

    public static void main(String[] args) {
        Zalgorithim zalgorithim = new Zalgorithim();
        int result = zalgorithim.zAlgorithm("aabbbb", "bb", 8, 2);
        System.out.println(result);
    }

    public static int fixJ(String str, int i, int j) {
        while (str.charAt(i) != str.charAt(j)) {
            j--;
        }
        return j;
    }

    public static boolean checkPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    public static int minCharsforPalindrome(String str) {
        //lmmllrs
        if (str.length() == 0 || str.length() == 1) {
            return 0;
        }
        int i = 0, j = str.length() - 1;
        j = fixJ(str, i, j);
        while (j != i) {
            if (checkPalindrome(str, i, j)) {
                return str.length() - j - 1;
            }
            j = fixJ(str, i, --j);
        }
        return str.length() - j - 1;
    }

    public int zAlgorithm(String s, String p, int n, int m) {
        // Write your code here

        int left = 0;
        int right = 0;
        int currentPointer = 0;

        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == p.charAt(currentPointer)) {
                System.out.println("i = " + i + " currentPointer = " + currentPointer);
                currentPointer++;
                if (currentPointer == p.length()) {
                    count++;
                    currentPointer = 0;
                }

            } else {
                currentPointer = 0;
            }
        }
        return count;

    }


}
