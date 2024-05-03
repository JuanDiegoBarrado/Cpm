package ast.expressions.operators;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import ast.Utils;
import ast.expressions.Expression;
import ast.preamble.Function;
import ast.preamble.Program;
import ast.types.Type;
import exceptions.InvalidIdException;
import exceptions.UndefinedFunctionException;

public class FunctionCall extends Expression { 
    protected List<ASTNode> functionReferences; // Constructor and Parenthesis list
    protected String id;
    protected List<Type> typeArgs;
    protected List<Expression> args;
    protected ASTNode matchedFunction;      // FIXME igual esto debería ser una definicion de funcion y no un astnode

    public FunctionCall(String opnd1, List<Expression> opnd2, int row) {
        this.id = opnd1;
        this.args = opnd2;
        this.row = row;
        this.typeArgs = new ArrayList<>();
    }
    
    public String toString() {return id + args.toString();}

    @Override
    public void bind() {
        try {
            this.functionReferences = Program.symbolsTable.getFuncAndConstructsDefinitions(this.id);
        } catch (InvalidIdException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
        }
        for (Expression exp : args)
          exp.bind();
    }

    @Override
    public Type checkType() throws Exception {
        for (Expression arg : args)
            typeArgs.add(arg.checkType());
        if (functionReferences == null)
            throw new UndefinedFunctionException("Function name " + this.id + " is not defined");
        List<Function> candidates = new ArrayList<>();
        for (ASTNode node : functionReferences)
            candidates.add((Function) node);
        Function matched = matchWith(candidates);
        matchedFunction = matched;
        return matched.getType();
    }

    public Function matchWith(List<Function> fs) throws Exception {
        for (Function f : fs) {
            boolean match = true;
            List<Type> types = f.getArgumentTypes();
            if (typeArgs.size() != args.size())
                continue;
            for (int i = 0; i < args.size(); i++) {
                if (!types.get(i).canBeAssigned(typeArgs.get(i))) {
                    match = false;
                    break;
                }
            }
            if (match)
                return f;
        }
        throw new UndefinedFunctionException("There is no function that matches " + this.id + " at row " + this.row);
    }
}