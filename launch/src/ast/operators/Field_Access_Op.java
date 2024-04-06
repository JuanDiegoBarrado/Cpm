package ast.operators;

import ast.EBin;
import ast.Expression;
import ast.KindE;

public class Field_Access_Op extends EBin {
   public Field_Access_Op(Expression opnd1, Expression opnd2) {
     super(opnd1,opnd2);  
   }     
   public KindE kind() {return KindE.SUMA;}
   public String toString() {return "." + opnd1().toString(); };
}
