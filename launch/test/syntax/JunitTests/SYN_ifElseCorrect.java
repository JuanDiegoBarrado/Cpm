package syntax.JunitTests;
import syntax.ReconSyntax;

import org.junit.Test;

public class SYN_ifElseCorrect {
    
    @Test
    public void testSYN_ifElseCorrect() throws Exception {
        String[] args = { "launch/test/syntax/testingCodes/Correct/SYN_ifElseCorrect.cpm" }; // File that will be tested
        ReconSyntax.main(args); // this test shouldnt detect any errors in the syntax
    }
}

