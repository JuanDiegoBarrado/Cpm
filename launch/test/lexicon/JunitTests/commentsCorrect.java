package lexicon.JunitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import lexicon.*;


public class commentsCorrect {

    @Test
    public void testcommentsCorrect() throws Exception {
        String[] args = {"launch/test/lexicon/testingCodes/commentsCorrect.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test shouldnt detect any errors in the lexic
        assertEquals(0, ALexOperations.numberLexiconUnits);
        assertEquals(false, ALexOperations.errorDetected);
    }

}