package erserver.modules.hardunderstand;

public class DivergenceReportBuilder {
    private int redInboundPatientsCount;
    private int yellowInboundPatientsCount;
    private int greenInboundPatientsCount;
    private int[] availableStaff;
    private int[] neededStaff;
    private int bedsSize;
    private int criticalBedsAvailable;

    public DivergenceReportBuilder(int redInboundPatientsCount, int yellowInboundPatientsCount,
                                   int greenInboundPatientsCount, int[] availableStaff, int[] neededStaff,
                                   int bedsSize, int criticalBedsAvailable) {
        this.redInboundPatientsCount = redInboundPatientsCount;
        this.yellowInboundPatientsCount = yellowInboundPatientsCount;
        this.greenInboundPatientsCount = greenInboundPatientsCount;
        this.availableStaff = availableStaff;
        this.neededStaff = neededStaff;
        this.bedsSize = bedsSize;
        this.criticalBedsAvailable = criticalBedsAvailable;
    }

    public String buildReport() {
        return "Situation Report:\n" +
               "Available Doctors: " + availableStaff[0];
    }
}
