package erserver.modules.testtypes;

import java.time.LocalDate;

public class PatientForTesting extends Patient{

    private LocalDate currentDate;
    public PatientForTesting() {
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public LocalDate getCurrentDate() {
        return this.currentDate;
    }
}
