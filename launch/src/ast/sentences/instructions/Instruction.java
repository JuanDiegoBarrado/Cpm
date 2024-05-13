package ast.sentences.instructions;

import ast.Delta;
import ast.SymbolsTable;
import ast.expressions.Expression;
import ast.sentences.Block;
import ast.sentences.Sentence;
import utils.GoodBoolean;
import utils.GoodInteger;

public abstract class Instruction extends Sentence {
    protected int indentation;
    protected Expression argExpression;
    protected Block body;

    public Instruction(Expression args, Block ins, int row) {
        super();
        this.argExpression = args;
        this.body = ins;
        this.row = row;
    }
    
    @Override
    public void propagateStaticVars(GoodBoolean g, SymbolsTable s) {
        super.propagateStaticVars(g, s);
        if (argExpression != null)
            argExpression.propagateStaticVars(g, s);
        if (body != null)
            body.propagateStaticVars(g, s);
    }

    @Override
    public void propagateIndentation(int indent) {
        super.propagateIndentation(indent);
        if (body != null) body.propagateIndentation(indent + 1);
    }

    public String toString() {return "";}
    
    @Override
    public void bind() {
        if (this.argExpression != null)
            this.argExpression.bind();
        if (this.body != null)
            this.body.bind();
    }

    @Override
    public void checkType() throws Exception {
        if (this.argExpression != null)
            this.argExpression.checkType();
        if (this.body != null)
            this.body.checkType();
    }

    @Override
    public void maxMemory(GoodInteger c, GoodInteger maxi) {
        if (this.argExpression != null)
            this.argExpression.maxMemory(c, maxi);
        if (this.body != null)
            this.body.maxMemory(c, maxi);
    }

    @Override
    public void computeOffset(Delta delta) {
        if (this.argExpression != null)
            this.argExpression.computeOffset(delta);
        if (this.body != null)
            this.body.computeOffset(delta);
    }
}
