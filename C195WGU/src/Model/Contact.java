package Model;

/**
 * contact model
 * @author Isabelle Matthews
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Contact
     * @param contactID contactid
     * @param contactName contactname
     * @param email email
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Get ContactID
     * @return contact ID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Set contact ID
     * @param contactID contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    //contact name

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    //email


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
