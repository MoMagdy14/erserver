package erserver.modules.dependencies;

import java.util.ArrayList;
import java.util.List;

public class StaffProviderDouble implements StaffProvider {

    private List<Staff> staffList;

    public StaffProviderDouble() {
        this.staffList = new ArrayList<>();
    }

    public void addStaff(Staff staff){
        this.staffList.add(staff);
    }

    @Override
    public List<Staff> getShiftStaff() {
        return staffList;
    }
}
