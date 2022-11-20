package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;

import java.util.List;

public interface InboundPatientProvider {
    List<Patient> currentInboundPatients();

    void informOfPatientArrival(int transportId);
}
