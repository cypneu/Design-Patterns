package visitor.graphvisitor;

import visitor.computationalgraph.*;

public interface Visitor {
    void visit(Addition addition) throws Exception;
    void visit(Subtraction subtraction) throws Exception;
    void visit(ScalarMultiplication scalarMultiplication);
    void visit(MatrixMultiplication matrixMultiplication) throws Exception;
    void visit(Inverse inverse) throws Exception;
}
