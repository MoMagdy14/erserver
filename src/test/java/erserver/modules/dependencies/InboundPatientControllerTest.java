package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InboundPatientControllerTest {
    @Test
    public void testInboundXmlConversion() {
        InboundPatientController controller = new InboundPatientController(null);
        String xml =
           "<Inbound>\n" +
           "    <Patient>\n" +
           "    <TransportId>1</TransportId>\n" +
           "    <Name>John Doe</Name>\n" +
           "    <Condition>heart disease</Condition>\n" +
           "    <Priority>YELLOW</Priority>\n" +
           "    <Birthdate></Birthdate>\n" +
           "    </Patient>\n" +
           "</Inbound>";
        List<Patient> patients = InboundPatientController.getPatients(xml);
        assertNotNull(patients);
        assertEquals(1, patients.size());
        Patient patient = patients.get(0);
        assertEquals("John Doe", patient.getName());
        assertEquals(1, patient.getTransportId());
        assertEquals(Priority.YELLOW, patient.getPriority());
        assertEquals("heart disease", patient.getCondition());
        assertNull(patient.getBirthDate());
    }
}