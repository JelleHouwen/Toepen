package Toepen;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jelle on 18-6-2017.
 */
public class SpelerTest extends TestCase {

    private Speler s = new Speler();

    @Test
    public void testSetGebruikersnaam()
    {
        String stringExpected = "test";
        s.setGebruikersnaam("test");

        assertEquals(stringExpected,s.getGebruikersnaam());

    }

}