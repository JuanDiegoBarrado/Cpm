package ast;

import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;

import ast.expressions.Expression;
import ast.expressions.Expression.Operator_T;
import ast.preamble.Argument;
import ast.types.interfaces.Array_Type;
import java_cup.terminal;

public class Josito {
    private static StringJoiner code = new StringJoiner("\n");
    // Just
    // Other
    // Sintax
    // Interpreter
    // Translator
    // Output

    public static final int NUM_FUNC_POINTERS_SIZE = 8;
    
    private int functionWASMId = 1; // The id of the functions of the compiling program
    private Stack<Integer> breakJumpScope = new Stack<Integer>();  // This stack scope controls the break jumps
    private int prevBreakJump;
    private int indentation;
    private int dynamicLink;    // points to the beginning of its dynamic predecessor's frame (who call him)
    private int defaultSize = 4;
    private int stackPointer;   // points to the last occupied position of the memory 
    private int newPointer;     // points to the last occupied position of the heap
    
    public void programHeader(int size) {
        append("(module");
        append ("(type $_sig_i32i32i32 (func (param i32 i32 i32) ))");
        append("(type $_sig_i32 (func (param i32)))");
        append("(type $_sig_ri32 (func (result i32)))");
        append("(type $_sig_void (func ))");
        append("(import \"runtime\" \"exceptionHandler\" (func $exception (type $_sig_i32)))");
        append("(import \"runtime\" \"print\" (func $print (type $_sig_i32)))");
        append("(import \"runtime\" \"read\" (func $read (type $_sig_ri32)))");
        append("(memory 2000)");
        append("(start $init)");
        append("(global $SP (mut i32) (i32.const 0))          ;; start of stack");     // points the next free address (SP)
        append("(global $MP (mut i32) (i32.const 0))          ;; mark pointer");       // points the first address of the current scope (MP)
        append("(global $NP (mut i32) (i32.const 131071996))  ;; heap 2000*64*1024-4");
        append("(global $swap (mut i32) (i32.const 0))");
        append("(global $trash (mut i32) (i32.const 0))");
        append("(global $darr (mut i32) (i32.const 0))");
        /*
         * Function ini
        */
        append("(func $init");
        append("    i32.const %d", size);
        append("    call $reserveStack");
        append("    call $setDynamicLink");
        append("    call $0");
        append("    call $freeStack");
        append("    global.set $trash");
        append(")");

        loadFunctions(code);
    }

    public void closeProgram() {
        append(")");
    }

    public void consumeTrash() {
        append("global.set $trash");
    }

    public void getReturnAddress(int size) { 
        append("global.get $SP");
        append("i32.const %d", size);
        append("i32.sub");
    }

    public void loadFunctions(StringJoiner code) {        
        /*
         * Function that reserves enough memory for the new scope
         */
        append("(func $reserveStack (param $size i32)");  // this argument is the maximunMemoy of the new scope
        append("    (result i32)");                         // returning value will be the Mark Pointer (MP) of the calling scope <-- Dynamic Link
        append("    global.get $MP");
        append("    global.get $SP");
        append("    global.set $MP");
        append("    global.get $SP");
        append("    local.get $size");
        append("    i32.add");
        append("    global.set $SP");
        append("    global.get $SP");
        append("    global.get $NP");
        append("    i32.gt_u");
        append("    if");
        append("        i32.const 3");
        append("        call $exception");
        append("    end");
        append(")");
        
        /*
         * Function that restores the memory when exiting the scope
         */
        append("(func $freeStack (type $_sig_void)");
        append("   global.get $MP");
        append("   global.set $SP"); // SP <-- MP (the first next free address (SP) will be the first address of the current scope that are we freeing (MP))
        append("   global.get $MP"); // MP points to the current DL that points to the previous MP so we restore it
        append("   i32.load");
        append("   global.set $MP");
        append(")");


        append("(func $setDynamicLink");
        append("(param $dynamicLink i32)");
        append("    global.get $MP");
        append("    local.get $dynamicLink");
        append("    i32.store");
        append(")"); 

        /*
         * Function to reserve space in the heap and returns the position to be used next
         */
        append("(func $allocate_heap");
        append("(param $size i32)");
        append("(result i32)");
        append("    global.get $NP");
        append("    local.get $size");
        append("    i32.sub");
        append("    global.set $NP ;; we have brought the NP back size bytes");
        
        append("    global.get $SP");
        append("    global.get $NP");
        append("    i32.gt_u");
        append("    if");
        append("        i32.const 3");
        append("        call $exception ;; the SP has passed the NP");
        append("    end");

        append("    global.get $NP");
        append("    i32.const 4");
        append("    i32.add ;; NP + 4 is the position to be occupied now, so we leave it in the stack");
        append(")");

        /*
        * Copy n
        */
        append("(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest");
        append("    (param $src i32)");           // source
        append("    (param $dest i32)");          // destination
        append("    (param $size i32)");          // size elements
        append("    (local $n i32)");
        append("    local.get $size");
        append("    i32.const 4");
        append("    i32.div_s");
        append("    local.set $n");
        append("    block");
        append("        loop");
        append("        local.get $n");
        append("        i32.eqz");
        append("        br_if 1");
        append("        local.get $n");
        append("        i32.const 1");
        append("        i32.sub");
        append("        local.set $n");
        append("        local.get $dest");
        append("        local.get $src");
        append("        i32.load");
        append("        i32.store");
        append("        local.get $dest");
        append("        i32.const 4");
        append("        i32.add");
        append("        local.set $dest");
        append("        local.get $src");
        append("        i32.const 4");
        append("        i32.add");
        append("        local.set $src");
        append("        br 0");
        append("        end");
        append("    end");
        append(")");

        /*
         * Power function (base^exp)
        */
        append("(func $exponentiation (param $base i32)(param $exponent i32)(result i32)");
        append("    (local $result i32)");
        append("    i32.const 1");
        append("    local.set $result");
        append("    block");
        append("    loop");
        append("        local.get $exponent");
        append("        i32.const 0");
        append("        i32.gt_u");
        append("        i32.eqz");
        append("        br_if 1");
        append("            local.get $base");
        append("            local.get $result");
        append("            i32.mul");
        append("            local.set  $result");
        append("");
        append("            local.get $exponent");
        append("            i32.const 1");
        append("            i32.sub");
        append("            local.set $exponent");
        append("        br 0");
        append("    end");
        append("    end");
        append("    local.get $result");
        append(")");
    }

    public void funcHeader(Integer WASMId) {
        append("(func $%d", WASMId);
        indentation++;
    }

    public void funcResult() {
        append("(result i32)");
    }

    public void funcTail() {
        indentation--;
        append(")");
    }

    public void createConst(int val) {
        append("i32.const %d", val);
    }

    public void reserveStackCall() {
        append("call $reserveStack");
    }

    public void freeStackCall() {
        append("call $freeStack");
    }

    public void allocate_heap(int size) {
        append("i32.const %d", size);
        append("call $allocate_heap");
    }

    public void copy_to_heap(int size) { // After the execution we also want the position in the heap to remain at the top of the stack
        // The address of the ConstructorCall is already in the stack
        allocate_heap(size);                        // Now the top of the stack is the address where the content has to be copied to
        append("global.set $swap");     // We store the address in the heap for later
        append("global.get $swap");     // We still need it though
        createConst(size);                          // We now push the size
        copy_n();
        append("global.get $swap");     // Finally we bring back to the top of the stack the position in the heap
    }

    public void getLocalDirUsingMP(int delta) {     // Get the local direction of a identifier using MP
        append("i32.const %d", delta);
        append("global.get $MP");
        append("i32.add");
    }

    public void getLocalDirUsingSP(int delta) {     // Get the local direction of a identifier using MP
        append("i32.const %d", delta);
        append("global.get $SP");
        append("i32.add");
    }

    public void getLocalDirUsingRef(int delta) {    // Get the local direction of a identifier using reference
        append("global.get $MP"); 
        append("i32.const 4");          // Address MP + 4 contains the reference value
        append("i32.add");
        append("i32.load");             // The stack now contains the address of the instance of the Class/Struct you are in
        append("i32.const %d", delta);
        append("i32.add");
    }

    public void setDynamicLink(){
        append("call $setDynamicLink");
    }

    
    public void setReferenceConstructor(int tamConstructor) { // This uses the size of the constructor type to point SP - constructor type size
        append("global.get $MP");
        append("i32.const 4");
        append("i32.add");
        append("global.get $SP");
        append("i32.const %d", tamConstructor);
        append("i32.sub");
        append("i32.store");
    } 

    public void returnReference() {                 // This uses the size of the constructor type to point SP - constructor type size
        append("global.get $MP");
        append("i32.const 4");
        append("i32.add");
        append("i32.load");
    } 

    public void setReference(int value) {
        if (value == 0) { // Function Call
            append("global.get $MP");
            append("i32.const 4");
            append("i32.add");
            append("i32.const 0");
            append("i32.store");
        }
        else if (value != 0) { // Method Call
            append("global.set $swap");
            append("global.get $MP");
            append("i32.const 4");
            append("i32.add");
            append("global.get $swap");
            append("i32.store");
        }
    }

    public void callFunction(int WASMId) {
        append("call $%d", WASMId);
    }

    public void load() {
        append("i32.load");
    }

    public void loadDynamicArray() {
        append("global.set $darr");
        append("global.get $darr");
        load(); // Starting position of the actual array
    }

    public void generateDynamicArrayInformation(Argument declared_arg, Expression arg, int terminal_size) throws Exception { // declared_arg is a dynamic array
        // First we get the position of the dynamic_array
        this.getLocalDirUsingSP(declared_arg.getOffset());
        // We get the address of the static array
        arg.generateAddress(this);
        // And now we make the dynamic array "point" to the static one
        store();

        // Now we update the size
        this.getLocalDirUsingSP(declared_arg.getOffset());
        createConst(4);
        append("i32.add");
        createConst(arg.getType().getSize());
        store();

        // Finally we save the size of every dimenssion
        List<Expression> args = ((Array_Type)arg.getType()).getDimenssions();
        for (int i = args.size() - 1; i >= 0; i--) {
            this.getLocalDirUsingSP(declared_arg.getOffset());
            createConst(8 + 4 * i);
            append("i32.add");      // The address where we want to save the size
            if (i == args.size() - 1) {
                createConst(terminal_size);
                store();
            }
            else {
                this.getLocalDirUsingSP(declared_arg.getOffset());
                createConst(8 + 4 * (i + 1));
                append("i32.add");  // Next address, where dim(i + 1) is stored
                load();
                args.get(i + 1).generateValue(this);
                mul();                          // dim(i) = args(i + 1) * dim(i + 1)
                store();
            }
        }
    }

    public void dynamicArrayAccess(Expression e, int dim_pos) throws Exception {
        append("global.get $darr");
        createConst(4 * (dim_pos + 1));
        append("i32.add");  // Address where the length of the dimenssion "dim_pos" is stored
        load();                         // We load the dim
        e.generateValue(this);
        mul();                          // We multiply it with the value of "e"
        append("i32.add");  // Finally we add to what we had in the stack previously
    }

    public void store() {
        append("i32.store");
    }

    public void copy_n() {
        append("call $copyn");
    }

    public void mul() {
        append("i32.mul");
    }

    public void ifInit() {
        append("if");
    }

    public void elseInit() {
        append("else");
    }

    public void endBlock() {
        append("end");
    }

    public void loopInit() {
        append("block");
        append("loop");
    }

    public void eqZero() {
        append("i32.eqz");
    }

    public void conditionalJump(int label) { // Conditional_jump to the label
        append("br_if %d", label);
    }

    public void jump(int label) { // Inconditional_jump to the label
        append("br %d", label);
    }

    public void jumpTable(List<Integer> br_table_values) { // Inconditional_jump to the label
        StringBuilder jump_list = new StringBuilder();
        for (Integer i : br_table_values) 
            jump_list.append(String.format("%d ", i));
        append("br_table %s", jump_list);
    }

    public void multipleBlocks(int num) { // Inconditional_jump to the label
        for (int i = 0; i < num; i++) 
            append("block");
    }

    public void switchVar(int min) {
        append("i32.const %d", min);
        append("i32.sub");
    }

    public void printCall() { // Inconditional_jump to the label
        append("call $print");
    }

    public void readCall() { // Inconditional_jump to the label
        append("call $read");
    }

    public void exponentiation() {
        append("call $exponentiation");
    }

    public void translateOperator(Operator_T operator) {
        switch (operator) {
            case AND:
                append("i32.and");
                break;
            case DIV:
                append("i32.div_s");
                break;
            case EQ:
                append("i32.eq");
                break;
            case GREATER:
                append("i32.gt_s");
                break;
            case GEQ:
                append("i32.ge_s");
                break;
            case LESS:
                append("i32.lt_s");
                break;
            case LEQ:
                append("i32.le_s");
                break;
            case MOD:
                append("i32.rem_s");
                break;
            case MINUS: // Take into account that there is no break
                append("i32.const -1");
            case MULT:
                append("i32.mul");
                break;
            case NEQ:
                append("i32.ne");
                break;
            case NOT:
                append("i32.eqz");
                break;
            case OR:
                append("i32.or");
                break;
            case PTR:
                break;
            case POW:
                exponentiation();
                break;
            case REFERENCE:
                break;
            case SUB:
                append("i32.sub");
                break;
            case SQ_BRACKET: // Take into account that there is no break
                append("i32.mul");
            case FIELD_ACCESS: // Field Access only need to calculate Code_D(left_part) + delta(field) 
            case ADD:
                append("i32.add");
                break;
        }
    }

    public String toString() {
        return code.toString();
    }

    public int getAndIncrementId() {
        int ret = this.functionWASMId;
        functionWASMId++;
        return ret;
    }

    public int getBreakJumpScope() {
        return this.breakJumpScope.peek();
    }

    public void popBreakJumpScope() {
        this.breakJumpScope.pop();
    }

    public void pushBreakJumpScope(int value) {
        this.breakJumpScope.push(value);
    }

    public void updateBreakJumpScopeTop(int value){  // This function only used in the switch to pop the current top and insert new Value
        this.breakJumpScope.pop();
        this.breakJumpScope.push(value);
    }
    
    private String indentate(String instruction) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++)
            sb.append("    ");
        sb.append(instruction);
        return sb.toString();
    }

    private void append(String instruction) {
        code.add(indentate(instruction));
    }

    private void append(String instruction, Object... args) {
        append(String.format(instruction, args));
    }
}
