package erserver.modules.hardunderstand;

import org.junit.Test;

import static org.junit.Assert.*;

public class DivergenceReportBuilderTest {

    private String exceptedReport = "Situation Report:\n" +
            "Available Doctors: 3";

    @Test
    public void testReport() {
        DivergenceReportBuilder reportBuilder = new DivergenceReportBuilder(1, 2, 4, new int[]{3, 5}, new int[]{3, 8}, 10 , 4);
        assertEquals(exceptedReport, reportBuilder.buildReport());
    }
}