public class Polynomial {
    public double[] coefficients;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
    };

    public Polynomial(double[] coefficients_input) {
        int arr_len = coefficients_input.length;
        coefficients = new double[arr_len];
        for (int i = 0; i < arr_len; i++) {
            coefficients[i] = coefficients_input[i];
        }
        ;
    };

    public Polynomial add(Polynomial polynomial) {
        int max_len = Math.max(coefficients.length, polynomial.coefficients.length);
        double[] new_coefficients = new double[max_len];
        for (int i = 0; i < max_len; i++) {
            new_coefficients[i] = 0;
        }
        for (int i = 0; i < coefficients.length; i++) {
            new_coefficients[i] += coefficients[i];
        }
        ;
        for (int i = 0; i < polynomial.coefficients.length; i++) {
            new_coefficients[i] += polynomial.coefficients[i];
        }
        ;
        return new Polynomial(new_coefficients);
    };

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        ;
        return result;
    };

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    };
}