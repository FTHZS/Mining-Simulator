public class LCMCalculator {
    // Helper function to calculate the GCD using the Euclidean algorithm
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to calculate LCM of two numbers
    private static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b; // Avoid overflow by dividing first
    }

    // Function to calculate LCM of multiple numbers
    public static int lcmOfArray(int[] numbers) {
        int result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = lcm(result, numbers[i]);
        }
        return result;
    }
}