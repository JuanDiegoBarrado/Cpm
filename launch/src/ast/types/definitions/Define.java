package ast.types.definitions;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import ast.SymbolsTable;
import ast.expressions.operands.AttributeID;
import ast.expressions.operands.FunctionCall;
import ast.expressions.operands.Literal;
import ast.preamble.Attribute;
import ast.preamble.Method;
import ast.types.interfaces.Const_Type;
import ast.types.interfaces.Type;
import exceptions.DuplicateDefinitionException;
import utils.GoodBoolean;
import utils.GoodInteger;
import utils.Utils;

public class Define extends Definition {
    private Literal value; 

    public Define(String id, Literal val, int row) {
        super(id, row);
        this.value = val;
    }
    
    @Override
    public String toString() {
        return "define " + definitionName + " " + value.toString();
    }

    @Override
    public List<ASTNode> getConstructors() {
        List<ASTNode> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    @Override
    public void propagateStaticVars(GoodBoolean g, SymbolsTable s) {
        super.propagateStaticVars(g, s);
        value.propagateStaticVars(g, s);
    }

	@Override
	public void bind() {
        try {
            // The order matters!
            symbolsTable.insertSymbol(definitionName, this);    // We also save it as a symbol, as it acts as a "global variable"
            super.bind();
        }
        catch (DuplicateDefinitionException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
            this.errorFlag.setValue(true);
        }
	}
    
    public Literal getLiteral() {
        return this.value;
    }

    @Override
    public void checkType() throws Exception {
        // Nothing to do
        value.checkType();
        this.type = new Const_Type(value.getType(), row, value);
    }

	@Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Attribute hasAttribute(AttributeID name, boolean insideClass) throws Exception {
        
        throw new UnsupportedOperationException("Unimplemented method 'hasAttribute'");
    }

    @Override
    public Method hasMethod(FunctionCall mc) throws Exception {
        
        throw new UnsupportedOperationException("Unimplemented method 'hasMethod'");
    }

    @Override
    public void maxMemory(GoodInteger c, GoodInteger maxi) {
        maximumMemory.setValue(0);
    }
}
