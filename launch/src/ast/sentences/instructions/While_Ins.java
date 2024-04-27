package ast.sentences.instructions;

import ast.sentences.Block;
import ast.Expression;
import ast.KindE;
import ast.Utils;

public class While_Ins extends Instruction {

    public While_Ins(Expression cond, Block body) {
        super(cond, body);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("while(" + this.argExpression.toString() + ")\n");
        str.append(body.toString());
        return str.toString();
    }

    @Override
    public KindE kind() {
       throw new UnsupportedOperationException("Unimplemented method 'kind'");
    }

    @Override
    public void bind() {
        this.argExpression.bind();
        this.body.bind();
    }
    
}
