package ast.operators;

import ast.EBin;
import ast.Expression;
import ast.KindE;

public class Less_Op extends EBin {
   public Less_Op(Expression opnd1, Expression opnd2) {
     super(opnd1,opnd2);  
   }     
   public KindE kind() {return KindE.SUMA;}
   public String toString() {return opnd1().toString() + " < " +opnd2().toString();}

   @Override
   public void bind() {
      opnd1().bind();
      opnd2().bind();
    }
}
