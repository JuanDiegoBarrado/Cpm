package syntax.JunitTests.Correct;

import syntax.ReconSyntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SYN_arraysCorrect {

    @Test
    public void testSYN_arraysCorrect() throws Exception {
        String[] args = { "launch/test/syntax/testingCodes/Correct/SYN_arraysCorrect.cpm" }; // File that will be tested
        ReconSyntax.main(args); // this test shouldnt detect any errors in the syntax
    }

}