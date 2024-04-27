package ast.preamble;

import java.util.List;

import ast.Expression;
import ast.sentences.Block;
import ast.sentences.declarations.Declaration;
import ast.types.Type;

public class Constructor extends Function {
    public Constructor(String name, List<Declaration> args, Type return_t, Block body, Expression return_var, int row) {
        super(name, args, return_t, body, return_var, row);
    }
    
    // Adding visibility to the function
    public Constructor(String name, List<Declaration> args, Type return_t, Block body, Expression return_var, Visibility visibility, int row) {
        super(name, args, return_t, body, return_var, visibility, row);
    }

    @Override
    public void bind() {
        super.propagateBind();
    }
}