package syntax.JunitsTests.Correct;
import syntax.ReconSyntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SYN_functionsCorrect {
    
    @Test
    public void testSYN_functionsCorrect() throws Exception {
        String[] args = { "launch/test/syntax/testingCodes/Correct/SYN_functionsCorrect.cpm" }; // File that will be tested
        ReconSyntax.main(args); // this test shouldnt detect any errors in the syntax
    }
}
