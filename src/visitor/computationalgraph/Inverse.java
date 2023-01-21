package visitor.computationalgraph;

import visitor.graphvisitor.Visitor;

public class Inverse extends OperationNode {
    private static final double epsilon = 1e-8;
    private final MatrixNode A;

    public Inverse(MatrixNode A) {
        this.A = A;
    }

    public void compute() throws Exception {
        if (!eligibleForInv(A)) throw new Exception("Provided matrix is not eligible for inverting");

        int n = A.getRowsNum();
        double[][] x = new double[n][n];
        double[][] b = new double[n][n];

        double[][] temp = A.getValue();
        double[][] AValues = new double[A.getRowsNum()][A.getColumnsNum()];
        for (int i = 0; i < temp.length; i++)
            AValues[i] = temp[i].clone();

        int[] index = new int[n];
        for (int i = 0; i < n; ++i)
            b[i][i] = 1;

        gaussian(AValues, index);

        for (int i = 0; i < n - 1; ++i)
            for (int j = i + 1; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    b[index[j]][k] -= AValues[index[j]][i] * b[index[i]][k];

        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / AValues[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= AValues[index[j]][k] * x[k][i];
                }
                x[j][i] /= AValues[index[j]][j];
            }
        }
        res.setMatrix(x);
    }

    public void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double[] c = new double[n];

        for (int i = 0; i < n; ++i)
            index[i] = i;

        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                a[index[i]][j] = pj;

                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    private boolean eligibleForInv(MatrixNode A) {
        return A.getRowsNum() == A.getColumnsNum() && Math.abs(determinantOfMatrix(A.getValue(), A.getRowsNum(), A.getRowsNum())) > epsilon;
    }

    private double determinantOfMatrix(double[][] AValues, int n, int inputN) {
        double det = 0;

        if (n == 1) return AValues[0][0];

        double[][] cofactors = new double[inputN][inputN];
        int sign = 1;

        for (int i = 0; i < n; i++) {
            getCofactor(AValues, cofactors, i, n);
            det += sign * AValues[0][i] * determinantOfMatrix(AValues, n - 1, inputN);

            sign = -sign;
        }

        return det;
    }

    private void getCofactor(double[][] AValues, double[][] cofactors, int q, int n) {
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != 0 && col != q) {
                    cofactors[i][j++] = AValues[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public MatrixNode getA() {
        return this.A;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }
}
