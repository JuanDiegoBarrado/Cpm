package lexicon.test.JunitTests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import lexicon.src.*;


public class functionsCorrect {

    @Test
    public void testfunctionsCorrect() throws FileNotFoundException, IOException {
        String[] args = {"lexicon/test/testingCodes/functionsCorrect.cpm"}; // File that will be tested
        ReconLexicon.main(args);
        
        // this test shouldnt detect any errors in the lexic
        assertEquals(false, ALexOperations.errorDetected);
    }

}