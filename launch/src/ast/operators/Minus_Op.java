package ast.operators;

import ast.EUnary;
import ast.Expression;
import ast.types.Int_Type;
import ast.types.Type.Type_T;
import exceptions.UnexpectedTypeException;


public class Minus_Op extends EUnary {
    public Minus_Op(Expression opnd, int row) {
        super(opnd, row);
    }
    
    
    public String toString() {return "-" + opnd1().toString();}
   
    @Override
    public void bind() {
        opnd1().bind();
        opnd2().bind();
    }

    @Override
    public void checkType() throws Exception { 
        opnd1().checkType();
        if (opnd1().getType() != Type_T.INT)
            throw new UnexpectedTypeException(Type_T.INT.name() + " was expected but " + opnd1().getType().name() + " was read");
        this.type = new Int_Type(row);    
    }
}
