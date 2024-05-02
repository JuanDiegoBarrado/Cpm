package ast.types;

import ast.ASTNode;

public abstract class Type implements ASTNode {
    protected int row;

    protected Type_T kind;
    public static enum Type_T { INT, BOOL, CLASS, STRUCT, POINTER, ARRAY, TEMP_UNKNOWN };

    public Type(Type_T v, int row) {
        this.kind = v;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public Type_T getKind() {
        return this.kind;
    }

    public String toString() {
        return this.kind.name().toLowerCase();
    }

    @Override
    public Type checkType() throws Exception {
        return this;
    }

    @Override
    public void propagateIndentation(int indent) {
        // Nothing to do
    }

    public Type getRootType() {
        return this;
    }

    public boolean equals(Type other) {
        if (other == null)
            return false;
        else
            return this.kind == other.kind;
    }
} 
