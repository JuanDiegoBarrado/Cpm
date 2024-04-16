package lexicon.JunitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import lexicon.*;


public class pointersCorrect {
    
    @Test
    public void testpointersCorrect() throws Exception {
        String[] args = {"launch/test/lexicon/testingCodes/pointersCorrect.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test shouldnt detect any errors in the lexic
        assertEquals(false, ALexOperations.errorDetected);
    }
}
