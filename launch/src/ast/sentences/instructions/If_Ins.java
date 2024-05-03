package ast.sentences.instructions;

import ast.sentences.Block;
import ast.types.Type;
import ast.types.Type.Type_T;
import exceptions.BooleanConditionException;
import ast.Utils;
import ast.expressions.Expression;
import ast.preamble.Program;

public class If_Ins extends Instruction {
    private Block elseBody;

    public If_Ins(Expression cond, Block if_body, Block else_body, int row) {
        super(cond, if_body, row);
        this.elseBody = else_body;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append(Utils.PURPLE + "if" + Utils.RESET + "(" + this.argExpression.toString() + ")\n");
        str.append(body.toString());
        if(!elseBody.empty()) {
            Utils.appendIndent(str, indentation);
            str.append(Utils.PURPLE + "else" + Utils.RESET + '\n');
            str.append(elseBody.toString());
        }
        return str.toString();
    }
    
    @Override
    public void propagateIndentation(int indent) {
        this.indentation = indent;
        this.body.propagateIndentation(indent + 1);
        this.elseBody.propagateIndentation(indent + 1);
    }

    @Override
    public void bind() {
        Program.symbolsTable.newScope();
        this.argExpression.bind();
        this.body.bind();
        Program.symbolsTable.closeScope();
        if (!elseBody.empty()) {
            Program.symbolsTable.newScope();
            elseBody.bind();
            Program.symbolsTable.closeScope();
        }
    }

    @Override
    public Type checkType() throws Exception {
        try {
            Type_T t = argExpression.checkType().getKind();
            if (t == null || t != Type_T.BOOL)
                throw new BooleanConditionException("If condition must be bolean type in the row " + this.row);
        } catch (Exception e) {
            System.out.println(e);
        }
        body.checkType();
        return null;
    }
}
