package business.customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CustomerForm {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String ccNumber;
    private String ccMonth;
    private String ccYear;
    private Date ccExpDate;

    private boolean hasNameError = false;
    private boolean hasAddressError = false;
    private boolean hasPhoneError = false;
    private boolean hasEmailError = false;
    private boolean hasCCNumberError = false;
    private boolean hasCCExpDateError = false;

    public CustomerForm() {
        this("", "", "", "", "", "", "");
    }

    public CustomerForm(String name, String address, String phone, String email, String ccNumber, String ccMonth, String ccYear) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.ccNumber = ccNumber;
        this.ccMonth = ccMonth;
        this.ccYear = ccYear;
        ccExpDate = getCcExpDate(ccMonth, ccYear);
        validate();
    }

    // Get methods for fields
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public String getCcMonth() {
        return ccMonth;
    }

    public String getCcYear() {
        return ccYear;
    }

    public Date getCcExpDate() {
        return ccExpDate;
    }

    public boolean getHasNameError() {
        return hasNameError;
    }

    public boolean isHasAddressError() {
        return hasAddressError;
    }

    public boolean isHasPhoneError() {
        return hasPhoneError;
    }

    public boolean isHasEmailError() {
        return hasEmailError;
    }

    public boolean isHasCCNumberError() {
        return hasCCNumberError;
    }

    public boolean isHasCCExpDateError() {
        return hasCCExpDateError;
    }

    public boolean getHasFieldError() {
        return hasNameError || hasAddressError || hasCCExpDateError || hasCCNumberError || hasEmailError || hasPhoneError;
    }

    public String getNameErrorMessage() {
        return "Valid name required (ex: Bilbo Baggins)";
    }

    public String getAddressErrorMessage() {
        return "Valid address required (ex: 123 Fake St. Springfield, VA)";
    }

    public String getPhoneErrorMessage() {
        return "Valid phone number required (ex: 703-555-1234)";
    }

    public String getEmailErrorMessage() {
        return "Valid email address required (ex: name@address.com)";
    }

    public String getCCNumberErrorMessage() {
        return "Valid CC number required (ex: 4001-0000-0000-0001)";
    }

    public String getCCExpDateErrorMessage() {
        return "Valid CC expiration date required (ex: no past dates, 01/2020)";
    }


    /**
     * All fields (including name and address): should be present and non-null
     */
    private void validate() {
        hasNameError = false;
        hasAddressError = false;
        hasPhoneError = false;
        hasEmailError = false;
        hasCCNumberError = false;
        hasCCExpDateError = false;


        if (name == null || name.equals("") || name.length() > 45) {
            hasNameError = true;
        }

        if (address == null || address.equals("")) {
            hasAddressError = true;
        }

        if (phone == null || phone.equals("") || !isValidPhone(phone)) {
            hasPhoneError = true;
        }

        if (email == null || email.equals("") || !isValidEmail(email)) {
            hasEmailError = true;
        }

        if (ccNumber == null || ccNumber.equals("") || !isValidCC(ccNumber)) {
            hasCCNumberError = true;
        }

        if (ccMonth == null || ccMonth.equals("") || ccYear == null ||
                ccYear.equals("") || !isValidExpDate()) {
            hasCCExpDateError = true;
        }

    }


    // Returns a Java date object with the specified month and year
    private Date getCcExpDate(String monthString, String yearString) {

        if (monthString == null || monthString.equals("") ||
                yearString == null || yearString.equals("")) return null;


        // TODO: Implement this method
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, Integer.parseInt(monthString) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.YEAR, Integer.parseInt(yearString));

        System.out.println("GETCCEXPDATE CONVERSION:  month: "
                + monthString + ", year: " + yearString + "; "
                + new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));

        return c.getTime();
    }

    //* phone: after removing all spaces, dashes, and parens from the string it should have exactly 10 digits
    private boolean isValidPhone(String s) {
        String ph = s.replace(" ", "")
                .replace("(", "")
                .replace(")", "")
                .replace("-", "");

        if (ph.length() != 10) return false;

        try {
            Float.parseFloat(ph);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    //* email: should not contain spaces; should contain a "@"; and the last character should not be "."
    private boolean isValidEmail(String e) {
        if (e.contains(" ")) return false;

        if (!e.contains("@")) return false;

        if (e.lastIndexOf(".") == e.length() - 1) return false;

        return true;
    }

    //* credit card number: after removing spaces and dashes, the number of characters should be between 14 and 16.
    private boolean isValidCC(String c) {
        //4444 3333 2222 1111

        String cc = c.replace(" ", "")
                .replace("-", "");
        if (cc.length() < 14 || cc.length() > 16) return false;
        return true;
    }

    //expiration date: the month and year should be this month and year or later
    private boolean isValidExpDate() {
        Calendar c = Calendar.getInstance();
        int thisYear = c.get(Calendar.YEAR);
        int thisMonth = c.get(Calendar.MONTH);

        Calendar cc = Calendar.getInstance();
        cc.setTime(ccExpDate);
        int ccYear = cc.get(Calendar.YEAR);
        int ccMonth = cc.get(Calendar.MONTH);

        if (ccYear < thisYear) return false;
        if (ccYear == thisYear) {
            if (ccMonth < thisMonth) {
                return false;
            }
        }

        return true;
    }
}
