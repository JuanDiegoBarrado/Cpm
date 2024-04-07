package ast;

public abstract class EUnary extends Expression {
    private Expression opnd;
    public EUnary(Expression opnd) {
      this.opnd = opnd;
    }
    public Expression opnd1() {return this.opnd;}
    public Expression opnd2() {return null;}    
}
