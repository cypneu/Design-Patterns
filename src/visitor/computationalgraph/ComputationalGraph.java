package visitor.computationalgraph;

import java.util.ArrayList;


public class ComputationalGraph {
    public ArrayList<OperationNode> vertices = new ArrayList<>();

    public MatrixNode add(OperationNode operation) {
        vertices.add(operation);
        return operation.getResNode();
    }
}
