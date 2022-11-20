package erserver.modules.dependencies;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StaffAssignmentManagerTest {
    @Test
    public void shouldGetPhysiciansAndResidencesOnCallingPhysicianOnDuty() {
        StaffProviderDouble staffProviderDouble = new StaffProviderDouble();
        staffProviderDouble.addStaff(new Staff(1, "Mohamed", StaffRole.DOCTOR));
        staffProviderDouble.addStaff(new Staff(2, "Ahmed", StaffRole.RESIDENT));
        StaffAssignmentManager staffAssignmentManager = new StaffAssignmentManager(staffProviderDouble, new BedProviderDouble());
        List<Staff> physiciansOnDuty = staffAssignmentManager.getPhysiciansOnDuty();
        assertNotNull(physiciansOnDuty);
        assertEquals(2, physiciansOnDuty.size());
        assertEquals(1, physiciansOnDuty.get(0).getStaffId());
        assertEquals(2, physiciansOnDuty.get(1).getStaffId());
    }
}