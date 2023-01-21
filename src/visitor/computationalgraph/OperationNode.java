package visitor.computationalgraph;

import visitor.graphvisitor.Visitor;

public abstract class OperationNode {
    public MatrixNode res;
    public OperationNode() {
        this.res = new MatrixNode();
    }
    public abstract void accept(Visitor visitor) throws Exception;

    public MatrixNode getResNode() {
        return this.res;
    }
}
