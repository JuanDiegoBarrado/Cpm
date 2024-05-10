package ast.sentences.instructions;

import ast.Josito;
import ast.expressions.operands.Literal;
import ast.sentences.Block;
import utils.Utils;

public class Case_Ins extends Instruction {

    public Case_Ins(Literal cond, Block body, int row) {
        super(cond, body, row);
    }

    @Override
    public void checkType() throws Exception {
        super.checkType();
        this.type = argExpression.getType();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("case: " + argExpression.toString() + '\n');
        str.append(body.toString());
        return str.toString();
    }

    public Integer getCaseValue() {
        return ((Literal) argExpression).toIntConst();
    }

    @Override
    public void generateCode(Josito jose) { 
        body.generateCode(jose);
    }

    // FIXME no sabemos si el literal irá a memoria o a pila
    // public void maxMemory(Integer c, Integer maxi);
}