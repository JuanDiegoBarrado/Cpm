package ast.expressions.operators;

import ast.expressions.BinaryExpression;
import ast.expressions.Expression;
import ast.types.interfaces.Int_Type;
import ast.types.interfaces.Type;
import ast.types.interfaces.Type.Type_T;
import exceptions.InvalidTypeException;


public class Pow_Op extends BinaryExpression {
    public Pow_Op(Expression opnd1, Expression opnd2, int row) {
        super(opnd1, opnd2, row);
        this.type = new Int_Type(row);
        this.operator = Operator_T.POW;
    }
    
    public String toString() {return opnd1().toString() + " ^ " +opnd2().toString();}
   
    @Override
    public void bind() {
        opnd1().bind();
        opnd2().bind();
        this.type.bind();
    }

    @Override
    public void checkType() throws Exception {
        super.checkType();
        Type left = opnd1().getType();
        Type right = opnd2().getType();
        if (left.getKind() != Type_T.INT)
            throw new InvalidTypeException(String.format("'%s' was expected but '%s' was read", Type_T.INT.name(), left.getKind().name()));
        if (right.getKind() != Type_T.INT)
            throw new InvalidTypeException(String.format("'%s' was expected but '%s' was read", Type_T.INT.name(), right.getKind().name()));
        this.type.checkType();
    }
}
