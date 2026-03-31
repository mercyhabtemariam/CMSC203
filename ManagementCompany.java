public class ManagementCompany {
    public static final int MAX_PROPERTIES = 5;

    private String name;
    private String taxId;
    private Property[] properties;
    private int propertyCount;

    public ManagementCompany(String name, String taxId) {
        this.name = name;
        this.taxId = taxId;
        properties = new Property[MAX_PROPERTIES];
        propertyCount = 0;
    }

    public ManagementCompany(ManagementCompany other) {
        this.name = other.name;
        this.taxId = other.taxId;
        this.properties = new Property[MAX_PROPERTIES];
        this.propertyCount = other.propertyCount;

        for (int i = 0; i < other.propertyCount; i++) {
            this.properties[i] = new Property(other.properties[i]);
        }
    }

    public int addProperty(Property p) {
        if (propertyCount >= MAX_PROPERTIES) {
            return -1;
        }

        properties[propertyCount] = new Property(p);
        propertyCount++;
        return propertyCount - 1;
    }

    public double totalRent() {
        double total = 0;

        for (int i = 0; i < propertyCount; i++) {
            total += properties[i].getRentAmount();
        }

        return total;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public Property getProperty(int index) {
        if (index >= 0 && index < propertyCount) {
            return properties[index];
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getTaxId() {
        return taxId;
    }

    public String toString() {
        String result = "Management Company: " + name + "\n";
        result += "Tax ID: " + taxId + "\n";
        result += "Properties:\n";

        for (int i = 0; i < propertyCount; i++) {
            result += "\nProperty " + (i + 1) + ":\n";
            result += properties[i].toString() + "\n";
        }

        result += "\nTotal Rent: $" + totalRent();
        return result;
    }
}