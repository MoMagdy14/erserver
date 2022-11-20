package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertScannerTest {

    @Test
    public void shouldAlertForRedPriorityPatient() {
        InboundPatientProviderDouble inboundDouble = new InboundPatientProviderDouble();
        inboundDouble.addNewInbound(CreateTestPatient(1, Priority.RED, "sth"));
        inboundDouble.addNewInbound(CreateTestPatient(2, Priority.YELLOW, "sth"));
        AlertScannerTestingSubClass scanner = new AlertScannerTestingSubClass(inboundDouble);
        scanner.scan();
        assertEquals(1, scanner.patientsAlertedFor.size());
        assertEquals(1, scanner.patientsAlertedFor.get(0).getTransportId());
    }

    @Test
    public void shouldAlertForYellowPriorityPatientWithHeartArrhythmia() {
        InboundPatientProviderDouble inboundDouble = new InboundPatientProviderDouble();
        inboundDouble.addNewInbound(CreateTestPatient(1, Priority.RED, "sth"));
        inboundDouble.addNewInbound(CreateTestPatient(2, Priority.YELLOW, "heart arrhythmia"));
        AlertScannerTestingSubClass scanner = new AlertScannerTestingSubClass(inboundDouble);
        scanner.scan();
        assertEquals(2, scanner.patientsAlertedFor.size());
        assertEquals(1, scanner.patientsAlertedFor.get(0).getTransportId());
    }
    private Patient CreateTestPatient(int transportId, Priority priority, String condition) {
        Patient criticalPatient = new Patient();
        criticalPatient.setTransportId(transportId);
        criticalPatient.setPriority(priority);
        criticalPatient.setCondition(condition);
        return criticalPatient;
    }
}