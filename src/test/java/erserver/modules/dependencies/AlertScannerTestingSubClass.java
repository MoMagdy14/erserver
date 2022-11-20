package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;

import java.util.ArrayList;

public class AlertScannerTestingSubClass extends AlertScanner{

    public ArrayList<Patient> patientsAlertedFor;

    public AlertScannerTestingSubClass(InboundPatientProvider inboundPatientProvider) {
        super(inboundPatientProvider);
        patientsAlertedFor = new ArrayList<>();
    }

    @Override
    protected void alertForNewCriticalPatient(Patient patient) {
        patientsAlertedFor.add(patient);
    }
}
