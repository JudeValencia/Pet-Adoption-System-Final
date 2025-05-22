package Adoption_Management_Package;

/**
 * Represents a customer entity with specific details such as contact
 * information, address, account credentials, and additional personal details.
 *
 * This class extends the General_Information class to incorporate
 * general personal information fields, providing a comprehensive representation
 * of a customer. It enables the storage, retrieval, and updating of customer-specific data.
 *
 * Fields included in this class allow for tracking a customer's
 * occupation, home type, and other relevant attributes essential for managing customers.
 * Additionally, this class includes fields for account management,
 * such as account ID and password, suitable for systems requiring user authentication.
 *
 * Methods:
 * - Getter and setter methods for customer details like address, email, occupation, home type,
 *   contact number, account, password, and customer ID.
 * - A boolean field to store information about whether the customer owns other pets.
 */
public class Customer extends General_Information {
    String address, email, occupation, homeType, contactNumber, account, password;
    int costumerID;
    boolean hasOtherPets;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomerID() {
        return costumerID;
    }

    public void setCustomerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean getHasOtherPets() {
        return hasOtherPets;
    }

    public void setHasOtherPets(boolean hasOtherPets) {
        this.hasOtherPets = hasOtherPets;
    }
}