package lexicon.JunitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import lexicon.*;


public class englishSymbolsError {

    @Test
    public void testenglishSymbolsError() throws Exception {
        String[] args = {"launch/test/lexicon/testingCodes/englishSymbolsError.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test should detect some errors in the lexic
        assertEquals(22, ALexOperations.numberErrors);
        assertEquals(true, ALexOperations.errorDetected);
    }

}
