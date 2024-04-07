package ast.preamble;

import java.util.List;

public class ClassFunctions {
    private List<Function> constructors, methods;

    public ClassFunctions(List<Function> constructors, List<Function> methods) {
        this.constructors = constructors;
        this.methods = methods;
    }

    public List<Function> getConstructors() {
        return constructors;
    }

    public List<Function> getMethods() {
        return methods;
    }
}