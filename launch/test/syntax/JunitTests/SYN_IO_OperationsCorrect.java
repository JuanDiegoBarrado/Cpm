package syntax.JunitTests;
import syntax.ReconSyntax;

import org.junit.Test;

public class SYN_IO_OperationsCorrect {
    
    @Test
    public void testSYN_IO_OperationsCorrect() throws Exception {
        String args ="launch/test/syntax/testingCodes/Correct/SYN_IO_OperationsCorrect.cpm"; // File that will be tested
        ReconSyntax r = new ReconSyntax();
r.run(args); // this test shouldnt detect any errors in the syntax
    }
}

