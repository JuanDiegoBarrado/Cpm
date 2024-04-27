package ast.sentences.instructions;

import ast.sentences.Sentence;
import ast.sentences.Block;
import java.util.ArrayList;
import java.util.List;

import ast.Expression;
import ast.KindE;
import ast.Utils;

public class For_Ins extends Instruction {

    public For_Ins(Sentence first_ins, Expression cond, Sentence last_ins, Block body) {
        super(null, null);
        While_Ins inner_while = new While_Ins(cond, body.add_instruction(last_ins));
        List<Sentence> for_ins = new ArrayList<>();
        for_ins.add(first_ins);
        for_ins.add(inner_while);
        Block for_body = new Block(for_ins);
        this.body = for_body;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("for(" + this.argExpression.toString() + ")\n");
        return str.toString();
    }

    @Override
    public KindE kind() {
       throw new UnsupportedOperationException("Unimplemented method 'kind'");
    }

    @Override
    public void bind() {        
        this.body.bind();
    }
    
}
