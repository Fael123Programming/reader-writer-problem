package util;

public class RandomNumber {
    public static int between(int a, int b) {
        if (b < a) {
            throw new IllegalArgumentException("b must be greater than or equal to a.");
        }
        return (int) (a + (Math.random() * (b - a)));
    }
}
