package Model;
/*
@author Isabelle Matthews
 */

import java.time.LocalDateTime;

/** Class of Appointment to set their values */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;

    /**
     * Appointment and their own int and string values
     * @param appointmentID appointmentID
     * @param title title
     * @param description description
     * @param location location
     * @param contactID contactID
     * @param type type
     * @param start start
     * @param end end
     * @param customerID customerID
     * @param userID userID
     */
    public Appointment(int appointmentID, String title, String description, String location, int contactID, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
    }

    /**
     * Appointment and their own string and int values
     * @param appointment_id appointmentID
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start
     * @param end end
     * @param customer_id customerid
     * @param user_id userid
     * @param contact_id contactid
     * @param contact_name contactname
     */
    public Appointment(int appointment_id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id, String contact_name) {
        this.appointmentID = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contact_id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.userID = user_id;
        this.customerID = customer_id;
    }

    /**
     * appointment and their own string and int values, it is for report contactID table
     * @param appointmentID appointmentID
     * @param title title
     * @param description descritption
     * @param type type
     * @param start start time
     * @param end end time
     * @param customerID customerID
     */
    public Appointment(int appointmentID, String title, String description, String type, LocalDateTime start, LocalDateTime end, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    /**
     * appointment ID, set and get
     * @return appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Set Appointment ID
     * @param appointmentID appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * title, set and get
     * @return title tile
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set string title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * description, get and set
     * @return description description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set String Description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * location, get, and set
     * @return location location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set Location
     * @param location location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * contact, get and set
     * @return contactID contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * set contactID
     * @param contactID contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * type, get and set
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set Type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * start time, get and set
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * This will set Start.
     * @param start start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * end time, get and set
     * @return end end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * This will set End localdatetime.
     * @param end end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * customerID, get and set
     * @return customerID customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set CustomerID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * user id, get and set
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set userID
     * @param userID userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

}
