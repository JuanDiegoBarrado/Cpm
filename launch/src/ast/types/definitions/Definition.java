package ast.types.definitions;

import java.util.List;

import ast.ASTNode;
import ast.ASTNodeTypable;
import ast.Delta;
import ast.Indentable;
import ast.Utils;
import ast.expressions.operands.AttributeID;
import ast.expressions.operands.FunctionCall;
import ast.preamble.Attribute;
import ast.preamble.Method;
import ast.preamble.Program;
import ast.types.interfaces.Type;
import exceptions.DuplicateDefinitionException;

public abstract class Definition extends ASTNodeTypable implements Indentable {
    protected Integer indentation;
    protected String definitionName;
    
    public Definition(String name, int row) {
        this.indentation = null;    // FIXME esto igual debería ser un 0
        this.definitionName = name;
        this.row = row;
    }

    public String getDefinitionName() {
        return this.definitionName;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public void bind() {
        try {
            Program.symbolsTable.insertDefinitions(this.definitionName, this);
        } catch (DuplicateDefinitionException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
        }
    }
    public abstract List<ASTNode> getConstructors();
    public abstract Attribute hasAttribute(AttributeID name, boolean insideClass) throws Exception;
    public abstract Method hasMethod(FunctionCall mc) throws Exception;

    public void propagateIndentation(int indent) {
        // Nothing to do by default
    }

    public Integer getSize() {
        return maximumMemory;
    }

    @Override
    public void computeOffset(Delta delta) {
        // Nothing to do
    }
}