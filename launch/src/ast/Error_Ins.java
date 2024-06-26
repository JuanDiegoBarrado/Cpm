package ast;
 
import ast.sentences.instructions.Instruction;
import utils.GoodBoolean;
import utils.Utils;

public class Error_Ins extends Instruction {
    
    public Error_Ins(int row) {
        super(null, null, row);
    }
   
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("ERROR INS\n");
        return str.toString();
    }

    @Override
    public void bind() {
        // Nothing to do
    }

    @Override
    public void checkType() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'checkType'");
    }

    @Override
    public void generateCode(Josito jose) {
        throw new UnsupportedOperationException("Unimplemented method 'generateCode'");
    }

    @Override
    public void propagateStaticVars(GoodBoolean g, SymbolsTable s) {
        throw new UnsupportedOperationException("Unimplemented method 'propagateErrorFlag'");
    }
}