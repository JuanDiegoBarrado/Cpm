package ast.expressions;

import ast.Josito;
import ast.SymbolsTable;
import utils.GoodBoolean;

public abstract class UnaryExpression extends Expression {
    private Expression opnd;
    
    public UnaryExpression(Expression opnd, int row) {
        this.opnd = opnd;
        this.row = row;
    }

    @Override
    public Expression opnd1() {return this.opnd;}
    @Override
    public Expression opnd2() { throw new UnsupportedOperationException("Unary expressions does not have right operand");}

    @Override
    public void propagateStaticVars(GoodBoolean g, SymbolsTable s) {
        super.propagateStaticVars(g, s);
        if (opnd != null)
            opnd.propagateStaticVars(g, s);
    }

    @Override
    public void checkType() throws Exception {
        this.opnd.checkType();        
    }

    public void generateValue(Josito jose) throws Exception { // Code_E
        if (opnd1() != null)
            opnd1().generateValue(jose);
        jose.translateOperator(this.operator);
    }
}
