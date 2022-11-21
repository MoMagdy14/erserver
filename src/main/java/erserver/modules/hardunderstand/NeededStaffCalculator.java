package erserver.modules.hardunderstand;

public class NeededStaffCalculator {
    private int[] redStaffRequired;
    private int[] yellowStaffRequired;
    private int[] greenStaffRequired;
    private int redInboundPatientsCount;
    private int yellowInboundPatientsCount;
    private int greenInboundPatientsCount;

    public NeededStaffCalculator(int[] redStaffRequired, int[] yellowStaffRequired, int[] greenStaffRequired,
                                 int redInboundPatientsCount, int yellowInboundPatientsCount,
                                 int greenInboundPatientsCount) {
        this.redStaffRequired = redStaffRequired;
        this.yellowStaffRequired = yellowStaffRequired;
        this.greenStaffRequired = greenStaffRequired;
        this.redInboundPatientsCount = redInboundPatientsCount;
        this.yellowInboundPatientsCount = yellowInboundPatientsCount;
        this.greenInboundPatientsCount = greenInboundPatientsCount;
    }

    public int[] calculate() {
        int[] neededStaff = {0, 0};
        // Calculate the need of doctors for each priority
        neededStaff[0] = redInboundPatientsCount * redStaffRequired[0];
        neededStaff[0] += yellowInboundPatientsCount * yellowStaffRequired[0];
        neededStaff[0] += greenInboundPatientsCount * greenStaffRequired[0];
        // Calculate the need of nurses for each priority
        neededStaff[1] = redInboundPatientsCount * redStaffRequired[1];
        neededStaff[1] += yellowInboundPatientsCount * yellowStaffRequired[1];
        neededStaff[1] += greenInboundPatientsCount * greenStaffRequired[1];
        if (neededStaff[1]  > 5) {
            neededStaff[1] -= 1;
        }
        return neededStaff;
    }
}
