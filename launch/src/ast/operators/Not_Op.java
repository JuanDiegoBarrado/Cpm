package ast.operators;

import ast.EUnary;
import ast.Expression;
import ast.KindE;

public class Not_Op extends EUnary {
    public Not_Op(Expression opnd, int row) {
        super(opnd, row);
    }
    public KindE kind() {return KindE.SUMA;}
    public String toString() {return "!" + opnd1().toString();};

    @Override
    public void bind() {
        opnd1().bind();
    }
}
