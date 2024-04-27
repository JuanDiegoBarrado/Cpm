package ast.operators;

import ast.EBin;
import ast.Expression;
import ast.KindE;

public class Mult_Op extends EBin {
   public Mult_Op(Expression opnd1, Expression opnd2) {
     super(opnd1,opnd2);  
   }     
   public KindE kind() {return KindE.MUL;}
   public String toString() {return opnd1().toString()+ " * " + opnd2().toString();}

   @Override
   public void bind() {
      opnd1().bind();
      opnd2().bind();
    }
}
