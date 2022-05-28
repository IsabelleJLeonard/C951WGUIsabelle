package Model;

/**
 * division data
 * @author Isabelle Matthews
 */

public class Division {
    private int divisionID;
    private int countryId;
    private String division;

    public Division(int divisionID, String division){
        this.division = division;
        this.divisionID = divisionID;
        this.countryId = countryId;
    }

    // division
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }

    // country id
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    //division id
    public int getDivisionID() {
        return divisionID;
    }
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    @Override
    public String toString() {
        return division;
    }
}
