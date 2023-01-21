package visitor.computationalgraph;

import visitor.graphvisitor.Visitor;

public class Subtraction extends OperationNode {
    private final MatrixNode A, B;

    public Subtraction(MatrixNode A, MatrixNode B) {
        this.A = A;
        this.B = B;
    }

    public void compute() throws Exception {
        if (!dimensionMatch(A, B)) throw new Exception("To subtract matrices their dimensions must be the same!");

        int n = A.getRowsNum();
        int m = A.getColumnsNum();
        double[][] AValues = A.getValue();
        double[][] BValues = B.getValue();
        double[][] resMatrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                resMatrix[i][j] = AValues[i][j] - BValues[i][j];
        }

        res.setMatrix(resMatrix);
    }

    public MatrixNode getA() {
        return this.A;
    }

    public MatrixNode getB() {
        return this.B;
    }

    private boolean dimensionMatch(MatrixNode A, MatrixNode B) {
        return A.getRowsNum() == B.getRowsNum() && A.getColumnsNum() == B.getColumnsNum();
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }
}
