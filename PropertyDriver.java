public class PropertyDriver {
    public static void main(String[] args) {
        ManagementCompany mc = new ManagementCompany("Campus Realty", "123-45-6789");

        Property p1 = new Property("Apt 1", "Rockville", 1200.0, "Bob");
        Property p2 = new Property("Apt 2", "Silver Spring", 1500.0, "Carol");
        Property p3 = new Property("Apt 3", "Bethesda", 1800.0, "David");

        mc.addProperty(p1);
        mc.addProperty(p2);
        mc.addProperty(p3);

        System.out.println(mc.toString());
        System.out.println("\nProgrammer Name: Mercy");
    }
}