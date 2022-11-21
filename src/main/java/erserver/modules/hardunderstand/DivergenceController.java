package erserver.modules.hardunderstand;

import erserver.modules.dependencies.*;
import erserver.modules.dependencies.vendorpagersystem.PagerSystem;
import erserver.modules.dependencies.vendorpagersystem.PagerTransport;
import erserver.modules.testtypes.Patient;

import java.util.List;

public class DivergenceController {

   private static final String ADMIN_ON_CALL_DEVICE = "111-111-1111";

   private boolean redDivergence;
   private boolean yellowDivergence;
   private boolean greenDivergence;
   private int redCount;
   private int yellowCount;
   private int greenCount;
   private int allowedCount;
   private int redBedMergin;
   private int yellowBedMergin;
   private int greenBedMergin;

   public DivergenceController() {
      this.redDivergence = false;
      this.yellowDivergence = false;
      this.greenDivergence = false;
      this.redCount = 0;
      this.yellowCount = 0;
      this.greenCount = 0;
      this.allowedCount = 3;
      this.redBedMergin = 0;
      this.yellowBedMergin = 1;
      this.greenBedMergin = 4;
   }

   public void check() {
      StaffAssignmentManager manager = new ERServerMainController().getStaffAssignmentManager();
      InboundPatientController controller = new ERServerMainController().getInboundPatientController();
      int[] redStaffRequired = {1, 2};
      int[] yellowStaffRequired = {1, 1};
      int[] greenStaffRequired = {0, 1};
      boolean redIncremented = false;
      boolean yellowIncremented = false;
      boolean greenIncremented = false;
      List<Patient> patients = controller.currentInboundPatients();
      List<Staff> staff = manager.getAvailableStaff();
      List<Bed> beds = manager.getAvailableBeds();
      int redInboundPatientsCount = 0;
      int yellowInboundPatientsCount = 0;
      int greenInboundPatientsCount = 0;
      int[] availableStaff = {0, 0};


      // Calculate number of critical care beds
      int criticalBedsAvailable = CalculateCriticalBeds(beds);

      // Count number of inbound patients of each priority
      for (Patient patient : patients) {
         if (Priority.RED.equals(patient.getPriority())) {
            redInboundPatientsCount++;
         }
         else if (Priority.YELLOW.equals(patient.getPriority())) {
            yellowInboundPatientsCount ++;
         }
         else if (Priority.GREEN.equals(patient.getPriority())) {
            greenInboundPatientsCount ++;
         }
      }

      // Count the number of doctors and nurses
      for (Staff cur : staff) {
         if (StaffRole.DOCTOR.equals(cur.getRole())) {
            availableStaff[0]++;
         }
         else if (StaffRole.NURSE.equals(cur.getRole())) {
            availableStaff[1]++;
         }
      }

      // Checks and counts the number of red priority patients over capacity
      if (redInboundPatientsCount > (criticalBedsAvailable + redBedMergin)) {
         redCount++;
         redIncremented = true;
      } // Checks and counts the number of normal priority patients over capacity
      if (yellowInboundPatientsCount + greenInboundPatientsCount > (beds.size() - criticalBedsAvailable + yellowBedMergin + greenBedMergin)) {
         if ( (greenInboundPatientsCount > (beds.size() - criticalBedsAvailable + greenBedMergin)) && (yellowInboundPatientsCount <= (beds.size() - criticalBedsAvailable + yellowBedMergin)) ) {
            greenCount++;
            greenIncremented = true;
         } else {
            greenCount++;
            yellowCount++;
            greenIncremented = true;
            yellowIncremented = true;
         }
      }
      // Calculate the need of doctors for each priority
      int[] neededStaff = CalculateNeedStaff(redStaffRequired, yellowStaffRequired, greenStaffRequired,
              redInboundPatientsCount, yellowInboundPatientsCount, greenInboundPatientsCount);
      // Calculate shortage of doctors
      if (neededStaff[0] > availableStaff[0]) {
         int diff = neededStaff[0] - availableStaff[0];
         if ((greenInboundPatientsCount * greenStaffRequired[0]) >= diff)  {
            if (!greenIncremented) {
               greenIncremented = true;
               greenCount++;
            }
         }
         else {
            int both = (yellowInboundPatientsCount * yellowStaffRequired[0]) + (greenInboundPatientsCount * greenStaffRequired[0]);
            if (both >= diff) {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
            }
            else {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
               if (!redIncremented) {
                  redIncremented = true;
                  redCount++;
               }
            }
         }
      }
      // Calculate shortage for nurses
      if (neededStaff[1] > availableStaff[1]) {
         int diff = neededStaff[1] - availableStaff[1];
         if ((greenInboundPatientsCount * greenStaffRequired[1]) >= diff)  {
            if (!greenIncremented) {
               greenIncremented = true;
               greenCount++;
            }
         }
         else {
            int both = (yellowInboundPatientsCount * yellowStaffRequired[1]) + (greenInboundPatientsCount * greenStaffRequired[1]);
            if (both >= diff) {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
            }
            else {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
               if (!redIncremented) {
                  redIncremented = true;
                  redCount++;
               }
            }
         }
      }

      // Make a call to divergence if shortage occurs
      EmergencyResponseService transportService = new EmergencyResponseService("http://localhost", 4567, 1000);
      if (redIncremented) {
         if ((redCount > allowedCount) && !redDivergence) {
            redDivergence = true;
            transportService.requestInboundDiversion(Priority.RED);
            sendDivergencePage("Entered divergence for RED priority patients!", true);
            redCount = 0;
         }
      } else {
         redCount = 0;
         if (redDivergence) {
            transportService.removeInboundDiversion(Priority.RED);
            sendDivergencePage("Ended divergence for RED priority patients.", false);
            redDivergence = false;
         }
      }
      if (yellowIncremented) {
         if ((yellowCount > allowedCount) && !yellowDivergence) {
            yellowDivergence = true;
            transportService.requestInboundDiversion(Priority.YELLOW);
            sendDivergencePage("Entered divergence for YELLOW priority patients!", true);
            yellowCount = 0;
         }
      } else {
         yellowCount = 0;
         if (yellowDivergence) {
            transportService.removeInboundDiversion(Priority.YELLOW);
            sendDivergencePage("Ended divergence for YELLOW priority patients.", false);
            yellowDivergence = false;
         }
      }
      if (greenIncremented) {
         if ((greenCount > allowedCount) && !greenDivergence) {
            greenDivergence = true;
            transportService.requestInboundDiversion(Priority.GREEN);
            sendDivergencePage("Entered divergence for GREEN priority patients!", true);
            greenCount = 0;
         }
      } else {
         greenCount = 0;
         if (greenDivergence) {
            transportService.removeInboundDiversion(Priority.GREEN);
            sendDivergencePage("Ended divergence for GREEN priority patients.", false);
            greenDivergence = false;
         }
      }
   }

   private static int[] CalculateNeedStaff(int[] redStaffRequired, int[] yellowStaffRequired, int[] greenStaffRequired,
                                           int redInboundPatientsCount, int yellowInboundPatientsCount, int greenInboundPatientsCount) {
      int[] neededStaff = {0, 0};
      neededStaff[0] = redInboundPatientsCount * redStaffRequired[0];
      neededStaff[0] += yellowInboundPatientsCount * yellowStaffRequired[0];
      neededStaff[0] += greenInboundPatientsCount * greenStaffRequired[0];
      // Calculate the need of nurses for each priority
      neededStaff[1] = redInboundPatientsCount * redStaffRequired[1];
      neededStaff[1] += yellowInboundPatientsCount * yellowStaffRequired[1];
      neededStaff[1] += greenInboundPatientsCount * greenStaffRequired[1];
      return neededStaff;
   }

   private static int CalculateCriticalBeds(List<Bed> beds) {
      int criticalBedsAvailable = 0;
      for (Bed bed : beds) {
         if (bed.isCriticalCare()) {
            criticalBedsAvailable ++;
         }
      }
      return criticalBedsAvailable;
   }

   private void sendDivergencePage(String text, boolean requireAck) {
      try {
         PagerTransport transport = PagerSystem.getTransport();
         transport.initialize();
         if (requireAck) {
            transport.transmitRequiringAcknowledgement(ADMIN_ON_CALL_DEVICE, text);
         } else {
            transport.transmit(ADMIN_ON_CALL_DEVICE, text);
         }
      } catch (Throwable t) {
         t.printStackTrace();
      }

   }

}
