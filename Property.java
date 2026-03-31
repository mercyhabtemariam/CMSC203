public class Property {
    private String propertyName;
    private String city;
    private double rentAmount;
    private String owner;

    public Property() {
        propertyName = "";
        city = "";
        rentAmount = 0.0;
        owner = "";
    }

    public Property(String propertyName, String city, double rentAmount, String owner) {
        this.propertyName = propertyName;
        this.city = city;
        this.rentAmount = rentAmount;
        this.owner = owner;
    }

    public Property(Property other) {
        this.propertyName = other.propertyName;
        this.city = other.city;
        this.rentAmount = other.rentAmount;
        this.owner = other.owner;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String toString() {
        return "Property Name: " + propertyName +
               "\nCity: " + city +
               "\nRent Amount: $" + rentAmount +
               "\nOwner: " + owner;
    }
}