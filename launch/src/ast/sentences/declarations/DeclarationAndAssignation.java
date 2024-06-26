package ast.sentences.declarations;

import ast.Delta;
import ast.Josito;
import ast.SymbolsTable;
import ast.expressions.Expression;
import ast.expressions.operands.VariableID;
import ast.sentences.Sentence;
import ast.sentences.instructions.Assignation_Ins;
import utils.GoodBoolean;
import utils.GoodInteger;

public class DeclarationAndAssignation extends Sentence {
    private Declaration d;
    private Assignation_Ins a;

    public DeclarationAndAssignation(Declaration d, Expression a, int row) {
        this.d = d;
        this.a = new Assignation_Ins(new VariableID(d.getVarname(), row), a, row);
    }

    @Override
    public void propagateStaticVars(GoodBoolean g, SymbolsTable s) {
        super.propagateStaticVars(g, s);
        d.propagateStaticVars(g, s);
        a.propagateStaticVars(g, s);
    }

    @Override
    public void propagateIndentation(int indent) {
        this.indentation = indent;
        d.propagateIndentation(indent);
        a.propagateIndentation(indent);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.d.toString());
        str.append(a.toString());
        return str.toString();
    }

    @Override
	public void bind() {
        d.bind();
        a.bind();
	}

    @Override
    public void checkType() throws Exception {
        this.d.checkType();
        this.a.checkType();
    }

    @Override
    public void maxMemory(GoodInteger c, GoodInteger max) {
        d.maxMemory(c, max);
        a.maxMemory(c, max);
    }

    @Override
    public void computeOffset(Delta delta) {
        this.d.computeOffset(delta);
        this.a.computeOffset(delta);
    }

    @Override
    public void generateCode(Josito jose) { 
        d.generateCode(jose);
        a.generateCode(jose);
    }
}