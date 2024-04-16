package lexicon.JunitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import lexicon.*;

public class spanishSymbolsError {

    @Test
    public void testspanishSymbolsError() throws Exception {
        String[] args = {"launch/test/lexicon/testingCodes/spanishSymbolsError.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test shouldnt detect any errors in the lexic
        assertEquals(6, ALexOperations.numberErrors); //6 double errors
        assertEquals(true, ALexOperations.errorDetected);
    }

}

