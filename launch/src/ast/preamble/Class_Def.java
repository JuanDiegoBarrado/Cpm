package ast.preamble;

import java.util.ArrayList;
import java.util.List;

import ast.Utils;
import ast.expressions.operators.MethodCall;
import ast.types.Defined_Type;
import ast.types.Type;
import ast.types.Type.Type_T;
import exceptions.DuplicateDefinitionException;
import exceptions.UndefinedFunctionException;

public class Class_Def extends Struct {
    public Class_Def(String name, List<Attribute> attributes, ClassFunctions functions, int row) {
        super(name, attributes, row);
        this.functions = functions;
    }

    @Override
    public String toString() {
        String sup = super.toString();
        StringBuilder str = new StringBuilder(sup);
        for (Method f : functions.getMethods())
            str.append('\n' + f.toString());
        return str.toString();
    }

    @Override
    public void bind() {
        Program.symbolsTable.setCurrentDefinition(this.id);
        Program.symbolsTable.newScope();
        try {
            Program.symbolsTable.insertDefinitions(this.id, this);
            for (Attribute d : attributes)
                    d.bind();
            
            for (Constructor c : functions.getConstructors()) {
                if (c.getId() != this.id) {
                    System.out.println("The constructor's name doesn't match the name of the class");
                    continue;
                }
                c.bind();
            }
            // We don't call the super method so that we don't have to close the scope
            for (Method m : functions.getMethods())
                m.bind();
        } catch (DuplicateDefinitionException e) {
            System.out.println(e);
            Utils.printErrorRow(row);
        }
        Program.symbolsTable.closeScope();
        Program.symbolsTable.setCurrentDefinition(""); // empty String to represent that we are outside the class
    }

    @Override // TODO hacer las revisiones de public y privado
    public Type checkType() throws Exception { // TODO revisar aqui la sobrecarga de funciones
        this.definedType = new Defined_Type(id, row, Type_T.CLASS, this);
        try {
            for (Attribute a : attributes)
                a.checkType();
            for (Constructor c : functions.getConstructors())
                c.checkType();
            for (Method f : functions.getMethods())
                f.checkType();
            functions.checkForDuplicates(); // We check if the constructors and methods are well defined or if there are duplicates (in which case we remove the duplicated definitions)
        } catch (Exception e) {
            System.out.println("Class " + id + " definition typing failed");
        }
        return this.definedType;
    }

    @Override
    public Type_T checkKind() {
        return Type_T.CLASS;
    }

    @Override
    public Method hasMethod(MethodCall mc) throws Exception {
        List<Function> casting = new ArrayList<>();
        for (Method met : functions.getMethods())
            casting.add((Function)met);
        Function f = mc.matchWith(casting);
        if (f != null)
            return (Method) f;
        throw new UndefinedFunctionException("There is no methods that match " + mc.toString());
    }

}

/*
FIXME

typedef long long ll
typedef ll larguito
typedef larguito hola
typedef hola holita

clas Alumno {
    holita a;
    larguito d;
    ll b;
    hola c;
}

func mein(): int {
    int Alumno;
    int Alumno = 0;
    ll a = 0;
    int ll = 0;
    Alumno a = 0;
    Alumno a = new Alumno(1);
    if(true) {
        Alumno a = new Alumno(2);
    }
    return 0;
}


int a =  0;
if (...) {
    int a = 1;
    ceaut(a);
}
*/