package coding.JUnitTests;

import org.junit.Test;

public class code005 extends code {
    public static final String NAME = "code005";                  // nombre del test sin el java
    public static final String ANSWER = "3628800";                 // Escribir la respuesta (cada vez que wasm imprime hace un salto de linea)

    public code005() {
        super(NAME, ANSWER);
    }

    @Test
    public void testCode() {
        this.testGeneracionEjecucion();
    }
}