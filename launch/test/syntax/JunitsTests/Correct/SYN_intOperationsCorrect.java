package syntax.JunitsTests.Correct;
import syntax.ReconSyntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SYN_intOperationsCorrect {
    
    @Test
    public void testSYN_intOperationsCorrect() throws Exception {
        String[] args = { "launch/test/syntax/testingCodes/Correct/SYN_intOperationsCorrect.cpm" }; // File that will be tested
        ReconSyntax.main(args); // this test shouldnt detect any errors in the syntax
    }
}

