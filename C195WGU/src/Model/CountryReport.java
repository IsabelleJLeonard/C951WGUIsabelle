package Model;
/*
@author Isabelle Matthews
 */
public class CountryReport {
    private String countryName;
    private int customerTotal;

    /**
     * Country Report
     * @param countryName country name
     * @param customerTotal customer total
     */
    public CountryReport(String countryName, int customerTotal) {
        this.countryName = countryName;
        this.customerTotal = customerTotal;
    }

    /**
     * Get string country name
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * set country name
     * @param countryName country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * get customer total
     * @return custome total
     */
    public int getCustomerTotal() {
        return customerTotal;
    }

    /**
     * Set customer total for report table
     * @param customerTotal customer total
     */
    public void setCustomerTotal(int customerTotal) {
        this.customerTotal = customerTotal;
    }
}
