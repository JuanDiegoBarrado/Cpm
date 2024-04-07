package ast.types;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Type implements ASTNode {

  protected enum Type_T {
    INT, BOOL, ARRAY, CLASS, STRUCT, POINTER;

    public String toString() {
      return "Tipo: " + this.name();
    }
  }

  protected Type_T type;
  
  public Type(Type_T v) {
    this.type = v;   
  }

  public String toString() {
    return this.type.toString();
  }

  @Override
  public NodeKind nodeKind() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'nodeKind'");
  }
}
