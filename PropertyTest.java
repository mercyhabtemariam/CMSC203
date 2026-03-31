import static org.junit.Assert.*;
import org.junit.Test;

public class PropertyTest {

    @Test
    public void testAddPropertyAndTotalRent() {
        ManagementCompany mc = new ManagementCompany("Campus Realty", "123-45-6789");

        Property p1 = new Property("Apt 1", "Rockville", 1200.0, "Bob");
        Property p2 = new Property("Apt 2", "Rockville", 1500.0, "Carol");

        int index1 = mc.addProperty(p1);
        int index2 = mc.addProperty(p2);

        assertEquals(0, index1);
        assertEquals(1, index2);
        assertEquals(2, mc.getPropertyCount());
        assertEquals(2700.0, mc.totalRent(), 0.001);
    }

    @Test
    public void testAddPropertyWhenFull() {
        ManagementCompany mc = new ManagementCompany("Campus Realty", "123-45-6789");

        for (int i = 0; i < ManagementCompany.MAX_PROPERTIES; i++) {
            mc.addProperty(new Property("P" + i, "City", 1000.0 + i, "Owner"));
        }

        int result = mc.addProperty(new Property("Extra", "City", 2000.0, "OwnerX"));
        assertEquals(-1, result);
    }

    @Test
    public void testManagementCompanyCopyConstructor() {
        ManagementCompany mc1 = new ManagementCompany("Campus Realty", "123-45-6789");

        mc1.addProperty(new Property("Apt 1", "Rockville", 1200.0, "Bob"));
        mc1.addProperty(new Property("Apt 2", "Rockville", 1500.0, "Carol"));

        ManagementCompany mc2 = new ManagementCompany(mc1);

        assertEquals(mc1.getName(), mc2.getName());
        assertEquals(mc1.getTaxId(), mc2.getTaxId());
        assertEquals(mc1.getPropertyCount(), mc2.getPropertyCount());
        assertEquals(mc1.totalRent(), mc2.totalRent(), 0.001);

        // deep copy check
        assertNotSame(mc1.getProperty(0), mc2.getProperty(0));
    }

    @Test
    public void testPropertyCopyConstructor() {
        Property p1 = new Property("Apt 1", "Rockville", 1200.0, "Bob");
        Property p2 = new Property(p1);

        assertEquals(p1.getPropertyName(), p2.getPropertyName());
        assertEquals(p1.getCity(), p2.getCity());
        assertEquals(p1.getRentAmount(), p2.getRentAmount(), 0.001);
        assertEquals(p1.getOwner(), p2.getOwner());
        assertNotSame(p1, p2);
    }
}