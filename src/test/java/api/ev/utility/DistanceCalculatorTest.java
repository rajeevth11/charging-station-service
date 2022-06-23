package api.ev.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceCalculatorTest
{
    /**
     * Distance calculate between Baththaramulla & Hikkaduwa locations.
     */
    @Test
    public void calculateDistanceTest() {
        assertEquals(86.32557508455928, DistanceCalculator.calculateDistance( 6.896206869866274, 6.140855443547163, 79.9203476035877 , 80.10083414181895 ), 0);
    }

    /**
     * Distance calculate between Homagama & Kaduwela locations.
     */
    @Test
    public void calculateDistanceTestColombo() {
        assertEquals(10.138731111513696, DistanceCalculator.calculateDistance( 6.835896998536912, 6.925199733162458, 80.0068873166711 , 79.98834788796016 ), 0);
    }
}