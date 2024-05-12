package ast.sentences.declarations;

import java.util.List;

import ast.Delta;
import ast.Josito;
import ast.expressions.Expression;
import ast.expressions.operands.VariableID;
import ast.preamble.Argument;
import ast.preamble.Program;
import ast.sentences.Sentence;
import ast.types.interfaces.Array_Type;
import ast.types.interfaces.Const_Type;
import ast.types.interfaces.Type;
import ast.types.interfaces.Type.Type_T;
import exceptions.DuplicateDefinitionException;
import utils.GoodInteger;
import utils.Utils;

public class Declaration extends Sentence {
    protected VariableID varname;
    protected int indentation;
    protected Integer position;

    public Declaration(Type type, String id, int row) {
        this.type = type;
        this.varname = new VariableID(id, row);
        this.row = row;
    } 

    public Declaration(Declaration d) {
        this.type = d.type;
        this.varname = d.varname;
        this.row = d.row;
    }

    public String getVarname() {
        return this.varname.toString();
    }

    public Type getType() {
        return this.type;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append(this.type.toString()
        + " " + varname.toString() + '\n');
        return str.toString();
    }

    @Override
	public void bind() {
        type.bind();
        try {
            Program.symbolsTable.insertSymbol(varname.getValue(), this);
        }
        catch (DuplicateDefinitionException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
        }
	}

    @Override
    public void checkType() throws Exception {
        this.type.checkType();
        if (this.type != null && type.getKind() == Type_T.ARRAY) {
            Array_Type array_type = (Array_Type) type;
            if (!(this instanceof Argument)) {
                if (array_type.getDim() == null) {
                    System.out.println("NoDimensionalArrayException: you can't declare the array " + varname.toString()  + " with no dimenssion");
                    Utils.printErrorRow(row);
                }
                if (!Const_Type.class.equals(array_type.getDim().getType().getClass())) {
                    System.out.println("InvalidDimensionalArrayException: you can't declare the array " + varname.toString()  + " with variable dimension");
                    Utils.printErrorRow(row);
                }
                List<Expression> dimenssions = array_type.getDimenssions();
                for (Expression e : dimenssions) {
                    if (e.getType().getKind() != Type_T.INT) {
                        System.out.println("InvalidDimensionalArrayException: you can't declare the array " + varname.toString()  + " with no numeric dimension");
                        Utils.printErrorRow(row);
                    }
                }
            }
        }
    }

    public static Declaration manageDeclaration(Type t, String id, Array_Type array, int row) {
        return new Declaration(manageType(t, array), id, row);
    }

    public static Type manageType(Type t, Array_Type array) {
        if (array != null) {
            array.setInnerType(t);
            return array.recoverBiggestArray(t, 1);
        }
        else
            return t;
    }

    @Override
    public void maxMemory(GoodInteger c, GoodInteger max) {
        type.maxMemory(null, null); // this will calculate the size needed for that type and update his internal size value
        maximumMemory.setValue(type.getSize());
        c.setValue(c.toInt() + maximumMemory.toInt());
    }

    @Override
    public void computeOffset(Delta delta) {
        this.position = delta.getAndUpdateOffset(maximumMemory.toInt());
    }

    public Integer getOffset() {
        return this.position;
    }

    @Override // TODO esta sin hacer nada, porque deberian ser las asignaciones las que cambian la memoria lineal y una declaracion como tal no afecta a nada, ni si quiera haria los locals, haria que todo fuese con asignaciones y stores
    public void generateCode(Josito jose) { 
        // Nothing to do
    }
}