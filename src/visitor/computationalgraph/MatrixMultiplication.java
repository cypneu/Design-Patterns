package visitor.computationalgraph;

import visitor.graphvisitor.Visitor;

public class MatrixMultiplication extends OperationNode {
    private final MatrixNode A, B;

    public MatrixMultiplication(MatrixNode A, MatrixNode B) {
        this.A = A;
        this.B = B;
    }
    public void compute() throws Exception {
        if (!eligibleForMatrixMul(A, B)) throw new Exception("Matrices don't have proper dimension to be multiplied");

        int n = A.getRowsNum();
        int k = A.getColumnsNum();
        int m = B.getColumnsNum();
        double[][] AValues = A.getValue();
        double[][] BValues = B.getValue();
        double[][] resMatrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int l = 0; l < k; l++)
                    resMatrix[i][j] += AValues[i][l] * BValues[l][j];
            }
        }

        res.setMatrix(resMatrix);
    }

    public MatrixNode getA() {
        return this.A;
    }

    public MatrixNode getB() {
        return this.B;
    }

    private boolean eligibleForMatrixMul(MatrixNode A, MatrixNode B) {
        return A.getColumnsNum() == B.getRowsNum();
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }
}
