package erserver.modules.hardunderstand;

import org.junit.Test;

import static org.junit.Assert.*;

public class NeededStaffCalculatorTest {

    int[] redStaffRequired = {1, 2};
    int[] yellowStaffRequired = {1, 1};
    int[] greenStaffRequired = {0, 1};
    @Test
    public void staffCalculatedCorrectly() {
        NeededStaffCalculator neededStaffCalculator = new NeededStaffCalculator(redStaffRequired, yellowStaffRequired,
                greenStaffRequired, 1, 3, 3);
        int[] staffNeeded = neededStaffCalculator.calculate();
        assertEquals(4, staffNeeded[0]);
        assertEquals(8, staffNeeded[1]);
    }
}