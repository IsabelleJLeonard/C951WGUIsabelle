package Model;

/**
 * Appointment report for Report table form.
@author Isabelle Matthews
 */

/** class Appointment report and their values */
public class AppointmentReport {
    private int appointmentTotal;
    private String type;
    private int month;

    /** get appointment total, get and set */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }

    public void setAppointmentTotal(int appointmentTotal) {
        this.appointmentTotal = appointmentTotal;
    }

    /** type, get and set */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** month, get and set */
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    /** appointment and their int and string values */
    public AppointmentReport(String type, int month, int appointmentTotal) {
        this.type = type;
        this.month = month;
        this.appointmentTotal = appointmentTotal;

    }
}
