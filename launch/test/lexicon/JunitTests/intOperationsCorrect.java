package lexicon.JunitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lexicon.*;


public class intOperationsCorrect {

    @Test
    public void testintOperationsCorrect() throws Exception {
        String[] args = {"launch/test/lexicon/testingCodes/intOperationsCorrect.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test shouldnt detect any errors in the lexic
        assertEquals(false, ALexOperations.errorDetected);
    }

}
