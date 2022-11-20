package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;

import java.util.ArrayList;
import java.util.List;

public class InboundPatientProviderDouble implements InboundPatientProvider{
    private List<Patient> inbounds;

    public InboundPatientProviderDouble() {
        this.inbounds = new ArrayList<>();
    }

    public void addNewInbound(Patient newPatient) {
        inbounds.add(newPatient);
    }

    @Override
    public List<Patient> currentInboundPatients() {
        return inbounds;
    }

    @Override
    public void informOfPatientArrival(int transportId) {

    }
}
