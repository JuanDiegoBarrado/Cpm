package ast.preamble;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import ast.Utils;
import ast.expressions.Expression;
import ast.expressions.operators.MethodCall;
import ast.expressions.values.FieldID;
import ast.sentences.Block;
import ast.sentences.declarations.Declaration;
import ast.types.Type;
import ast.types.Type.Type_T;
import exceptions.DuplicateDefinitionException;
import exceptions.MatchingTypeException;
import exceptions.UndefinedAttributeException;
import exceptions.UndefinedFunctionException;

public class Function extends Definition {
    protected List<Argument> args;
    protected Block body;
    protected Type return_t;
    protected Expression return_var;

    public Function(String name, List<Argument> args, Type return_t, Block body, Expression return_var, int row) {
        super(name, row);
        this.args = args;
        this.return_t = return_t;
        this.body = body;
        this.return_var = return_var;
    }

    @Override
    public String toString() { // TODO no imprimimos los argumentos en el toString de la funcion
        if(this.indentation == null)
            this.propagateIndentation(0);
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("Function: " + id + "\n");
        Utils.appendIndent(str, indentation);
        str.append("Tipo: " + return_t + "\n");
        Utils.appendIndent(str, indentation);
        str.append("Cuerpo: \n" + body);
        Utils.appendIndent(str, indentation);
        str.append("Return: " + return_var + "\n");
        return str.toString();
    }

    
    @Override
    public void bind() {
        try {
            Program.symbolsTable.insertFunction(this.id, this);
            Program.symbolsTable.setCurrentDefinition(this.id);
            propagateBind();
            Program.symbolsTable.setCurrentDefinition("");
        }
        catch (DuplicateDefinitionException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
        }
    }
    
    protected void propagateBind() {
        Program.symbolsTable.newScope();
        for (Declaration d : args) 
            d.bind(); 
        if (return_t != null)
            return_t.bind();
        body.bind();
        if (return_var != null)
            return_var.bind();
        Program.symbolsTable.closeScope();
    }

    @Override
    public List<ASTNode> getConstructors() {
        return null; // This method will not be used
    }
    
    @Override
    public Type checkType() throws Exception {
        body.checkType();
        try {
            Type returnType = return_var.checkType();
            if(!returnType.equals(return_t)) {
                throw new MatchingTypeException(String.format("The type of the returning expression at function %s '%s' doesnt match the typeof the declared return type '%s' at row %d\n", this.id, returnType, this.return_t, returnType.getRow()));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return return_t;
    }

    @Override
    public void propagateIndentation(int indent) {
        this.indentation = indent;
        this.body.propagateIndentation(indent + 1);
    }

    @Override
    public Type_T checkKind() {
        return null;
    }

    public Type getType(){
        return return_t;
    }

    @Override
    public Attribute hasAttribute(FieldID name) throws Exception {
        throw new UndefinedAttributeException("There are no attributes defined inside a function");
    }

    @Override
    public Method hasMethod(MethodCall m) throws Exception {
        throw new UndefinedFunctionException("There are no methods defined inside a function");
    }

    public String hash() {
        List<Type> types = new ArrayList<>();
        for (Argument a : args)
            types.add(a.getType_T());
        return hash(this.id, types);
    }
    
    public static String hash(String id, List<Type> argsTypes) {
        // Concatena el nombre de la función con los tipos de sus argumentos
        StringBuilder hashString = new StringBuilder(id).append("(");
        for (int i = 0; i < argsTypes.size(); i++) {
            hashString.append(argsTypes.get(i).toString());
            if (i < argsTypes.size() - 1)
                hashString.append(", ");
        }
        hashString.append(")");
        
        // Aplica una función hash a la cadena resultante
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(hashString.toString().getBytes());
            StringBuilder hashHex = new StringBuilder();
            for (byte b : hashBytes) {
                hashHex.append(String.format("%02x", b));
            }
            return hashHex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
