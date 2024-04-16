package syntax.JunitsTests.Correct;
import syntax.ReconSyntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SYN_assignationsCorrect {

    @Test
    public void testSYN_assignationsCorrect() throws Exception {
        String[] args = { "launch/test/syntax/testingCodes/Correct/SYN_assignationsCorrect.cpm" }; // File that will be tested
        ReconSyntax.main(args); // this test shouldnt detect any errors in the syntax
    }
}
