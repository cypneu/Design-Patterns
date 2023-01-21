package visitor.computationalgraph;

import visitor.graphvisitor.Visitor;

public class ScalarMultiplication extends OperationNode {
    private final MatrixNode A;
    private final double scalar;
    public ScalarMultiplication(MatrixNode A, double scalar) {
        this.A = A;
        this.scalar = scalar;
    }

    public void compute() {
        int n = A.getRowsNum();
        int m = A.getColumnsNum();
        double[][] AValues = A.getValue();
        double[][] resMatrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                resMatrix[i][j] = AValues[i][j] * scalar;
        }

        res.setMatrix(resMatrix);
    }

    public MatrixNode getA() {
        return this.A;
    }

    public double getScalar() {
        return this.scalar;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
