import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeBookTest
{
   private GradeBook g1;
   private GradeBook g2;

   @BeforeEach
   public void setUp() throws Exception
   {
      g1 = new GradeBook(5);
      g2 = new GradeBook(5);

      g1.addScore(50);
      g1.addScore(75);

      g2.addScore(88);
      g2.addScore(92);
      g2.addScore(100);
   }

   @AfterEach
   public void tearDown() throws Exception
   {
      g1 = null;
      g2 = null;
   }

   @Test
   public void testAddScore()
   {
      assertTrue(g1.toString().equals("50.0 75.0 "));
      assertEquals(2, g1.getScoreSize());

      assertTrue(g2.toString().equals("88.0 92.0 100.0 "));
      assertEquals(3, g2.getScoreSize());
   }

   @Test
   public void testSum()
   {
      assertEquals(125.0, g1.sum(), 0.0001);
      assertEquals(280.0, g2.sum(), 0.0001);
   }

   @Test
   public void testMinimum()
   {
      assertEquals(50.0, g1.minimum(), 0.0001);
      assertEquals(88.0, g2.minimum(), 0.0001);
   }

   @Test
   public void testFinalScore()
   {
      assertEquals(75.0, g1.finalScore(), 0.0001);
      assertEquals(192.0, g2.finalScore(), 0.0001);
   }
}