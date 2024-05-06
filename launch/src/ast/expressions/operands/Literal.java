package ast.expressions.operands;

import ast.expressions.Expression;

public abstract class Literal extends Expression {
    
    public Literal(int row) {
        this.row = row;
    }
    
    @Override
    public void bind() { /* Nothing to do*/ }

    public abstract boolean equals(Literal other);

    @Override
    public Expression opnd1() { throw new UnsupportedOperationException("Literals does not have operands"); }
    @Override
    public Expression opnd2() { throw new UnsupportedOperationException("Literals does not have operands"); }
}
