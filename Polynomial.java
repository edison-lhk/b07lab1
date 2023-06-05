import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
        exponents = new int[1];
        exponents[0] = 0;
    };

    public Polynomial(double[] coefficientsInput, int[] exponentsInput) {
        int arr_len = coefficientsInput.length;
        coefficients = new double[arr_len];
        exponents = new int[arr_len];
        for (int i = 0; i < arr_len; i++) {
            coefficients[i] = coefficientsInput[i];
            exponents[i] = exponentsInput[i];
        }
    };

    public Polynomial(File file) {
        try {
            Scanner scanner = new Scanner(file);
            String data = scanner.nextLine();
            data = data.replace("-", "+-");
            String[] dataSplit = data.split("\\+", 0);
            coefficients = new double[dataSplit.length];
            exponents = new int[dataSplit.length];
            for (int i = 0; i < dataSplit.length; i++) {
                if (dataSplit[i].length() == 1) {
                    coefficients[i] = Double.parseDouble(dataSplit[i]);
                    exponents[i] = 0;
                } else {
                    String[] polySplit = dataSplit[i].split("x", 0);
                    coefficients[i] = Double.parseDouble(polySplit[0]);
                    exponents[i] = Integer.parseInt(polySplit[1]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    };

    public Polynomial add(Polynomial polynomial) {
        int[] tempExponents = new int[exponents.length + polynomial.exponents.length];
        int expo1Index = 0;
        int expo2Index = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            if (expo1Index < exponents.length && expo2Index < polynomial.exponents.length) {
                if (exponents[expo1Index] < polynomial.exponents[expo2Index]) {
                    tempExponents[i] = exponents[expo1Index];
                    expo1Index++;
                } else if (exponents[expo1Index] > polynomial.exponents[expo2Index]) {
                    tempExponents[i] = polynomial.exponents[expo2Index];
                    expo2Index++;
                } else {
                    tempExponents[i] = exponents[expo1Index];
                    expo1Index++;
                    expo2Index++;
                }
            } else if (expo1Index < exponents.length) {
                tempExponents[i] = exponents[expo1Index];
                expo1Index++;
            } else if (expo2Index < polynomial.exponents.length) {
                tempExponents[i] = polynomial.exponents[expo2Index];
                expo2Index++;
            } else {
                tempExponents[i] = -1;
            }
        }
        int unfiltered_len = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            if (tempExponents[i] == -1) {
                break;
            }
            unfiltered_len++;
        }
        double[] unfilteredCoefficients = new double[unfiltered_len];
        int[] unfilteredExponents = new int[unfiltered_len];
        for (int i = 0; i < unfiltered_len; i++) {
            unfilteredCoefficients[i] = 0;
            unfilteredExponents[i] = tempExponents[i];
        }
        for (int i = 0; i < coefficients.length; i++) {
            int j = 0;
            while (unfilteredExponents[j] != exponents[i]) {
                j++;
            }
            unfilteredCoefficients[j] += coefficients[i];
        }
        for (int i = 0; i < polynomial.coefficients.length; i++) {
            int j = 0;
            while (unfilteredExponents[j] != polynomial.exponents[i]) {
                j++;
            }
            unfilteredCoefficients[j] += polynomial.coefficients[i];
        }
        int new_len = 0;
        for (int i = 0; i < unfilteredExponents.length; i++) {
            if (unfilteredCoefficients[i] != 0) {
                new_len++;
            }
        }
        double[] newCoefficients = new double[new_len];
        int[] newExponents = new int[new_len];
        int unfiltered_index = 0;
        for (int i = 0; i < new_len; i++) {
            while (unfiltered_index < unfiltered_len) {
                if (unfilteredCoefficients[unfiltered_index] != 0) {
                    newExponents[i] = tempExponents[unfiltered_index];
                    unfiltered_index++;
                    break;
                }
                unfiltered_index++;
            }
        }
        Arrays.sort(newExponents);
        for (int i = 0; i < new_len; i++) {
            for (int j = 0; j < unfiltered_len; j++) {
                if (unfilteredExponents[j] == newExponents[i]) {
                    newCoefficients[i] = unfilteredCoefficients[j];
                    break;
                }
            }
        }
        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial multiply(Polynomial polynomial) {
        double[] tempCoefficients = new double[coefficients.length * polynomial.coefficients.length];
        int[] tempExponents = new int[exponents.length * polynomial.exponents.length];
        int tempIndex = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            tempExponents[i] = -1;
        }
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < polynomial.coefficients.length; j++) {
                double tempCoefficient = coefficients[i] * polynomial.coefficients[j];
                int tempExponent = exponents[i] + polynomial.exponents[j];
                boolean expoExist = false;
                for (int k = 0; k < tempCoefficients.length; k++) {
                    if (tempExponents[k] == tempExponent) {
                        expoExist = true;
                        tempCoefficients[k] += tempCoefficient;
                        break;
                    }
                }
                if (!expoExist) {
                    tempCoefficients[tempIndex] = tempCoefficient;
                    tempExponents[tempIndex] = tempExponent;
                    tempIndex++;
                }
            }
        }
        int unfiltered_len = 0;
        int new_len = 0;
        for (int i = 0; i < tempExponents.length; i++) {
            if (tempExponents[i] == -1) {
                break;
            }
            if (tempCoefficients[i] != 0) {
                new_len++;
            }
            unfiltered_len++;
        }
        double[] newCoefficients = new double[new_len];
        int[] newExponents = new int[new_len];
        int unfiltered_index = 0;
        for (int i = 0; i < new_len; i++) {
            while (unfiltered_index < unfiltered_len) {
                if (tempCoefficients[unfiltered_index] != 0) {
                    newExponents[i] = tempExponents[unfiltered_index];
                    unfiltered_index++;
                    break;
                }
                unfiltered_index++;
            }
        }
        Arrays.sort(newExponents);
        for (int i = 0; i < new_len; i++) {
            for (int j = 0; j < unfiltered_len; j++) {
                if (tempExponents[j] == newExponents[i]) {
                    newCoefficients[i] = tempCoefficients[j];
                    break;
                }
            }
        }
        return new Polynomial(newCoefficients, newExponents);
    };

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    };

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    };

    public void saveToFile(String fileName) {
        String result = "";
        for (int i = 0; i < coefficients.length; i++) {
            if (exponents[i] == 0) {
                result = result + Double.toString(coefficients[i]);
            } else {
                if (coefficients[i] < 0) {
                    result = result + Double.toString(coefficients[i]) + "x" + Integer.toString(exponents[i]);
                } else {
                    result = result + "+" + Double.toString(coefficients[i]) + "x" + Integer.toString(exponents[i]);
                }
            }
        }
        result = result.replace(".0", "");
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(result);
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(result);
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    };
}