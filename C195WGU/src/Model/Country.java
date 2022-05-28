package Model;

/**
 * Country Model
 * @author Isabelle Matthews
 */
public class Country {
    private int countryId;
       private String countryName;

    /**
     * Country java
     * @param countryId country id
     * @param countryName country name
     */
    public Country(int countryId, String countryName){
        this.countryName = countryName;
        this.countryId = countryId;
    }

    /**
     * Get string country name
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set Country name
     * @param countryName country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get country ID
     * @return country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Set country ID
     * @param countryId country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){

        return (countryName);
    }
}
