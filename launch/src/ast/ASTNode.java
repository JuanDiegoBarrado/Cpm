package ast;

public interface ASTNode {
    // public ?? type() // for the future
    // public ?? bind() // for the future
    // public ?? generateCode() // for the future
    public NodeKind nodeKind();
    public String toString();
    public void propagateIndentation(int indent);
}
