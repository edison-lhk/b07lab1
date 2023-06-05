import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2, -9 };
        int[] e2 = { 1, 4 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
        double[] c3 = { 5, 6, -2 };
        int[] e3 = { 3, 0, 1 };
        Polynomial p3 = new Polynomial(c3, e3);
        double[] c4 = { 6, 5, 4, 3 };
        int[] e4 = { 3, 2, 1, 0 };
        Polynomial p4 = new Polynomial(c4, e4);
        Polynomial m = p3.multiply(p4);
        System.out.println(Arrays.toString(m.coefficients) + "||" + Arrays.toString(m.exponents));
        try {
            File file = new File("test1.txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("1-2x1+3x2-4x3");
                fileWriter.close();
                Polynomial p5 = new Polynomial(file);
                System.out.println(Arrays.toString(p5.coefficients) + "||" + Arrays.toString(p5.exponents));
            } else {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("1-2x1+3x2-4x3");
                fileWriter.close();
                Polynomial p5 = new Polynomial(file);
                System.out.println(Arrays.toString(p5.coefficients) + "||" + Arrays.toString(p5.exponents));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        double[] c6 = { 9, -8, 7, -6, 5 };
        int[] e6 = { 0, 1, 5, 7, 10 };
        Polynomial p6 = new Polynomial(c6, e6);
        p6.saveToFile("test2.txt");
    }
}