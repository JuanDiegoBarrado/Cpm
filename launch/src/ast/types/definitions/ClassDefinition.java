package ast.types.definitions;

import java.util.ArrayList;
import java.util.List;

import ast.Utils;
import ast.expressions.operands.FunctionCall;
import ast.preamble.Attribute;
import ast.preamble.Function;
import ast.preamble.Method;
import ast.types.interfaces.Defined_Type;
import ast.types.interfaces.Type.Type_T;
import exceptions.UndefinedFunctionException;

public class ClassDefinition extends ObjectDefinition {
    
    public ClassDefinition(String name, List<Attribute> attributes, ClassFunctions functions, int row) {
        super(name, attributes, row);
        this.functions = functions;
    }

    @Override
    public String toString() {
        if(this.indentation == null)
            this.propagateIndentation(0);
        else
            this.propagateIndentation(this.indentation);
        StringBuilder str = new StringBuilder();
        Utils.appendIndent(str, indentation);
        str.append("Class: " + definitionName + "\n");
        str.append(super.toString());
        return str.toString();
    }

    @Override
    public void checkType() throws Exception {
        this.type = new Defined_Type(this.definitionName, row, Type_T.CLASS, this);
        super.checkType();
    }

    public Method hasMethod(FunctionCall mc) throws Exception {
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