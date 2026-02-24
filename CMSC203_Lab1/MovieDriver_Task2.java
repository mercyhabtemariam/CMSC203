import java.util.Scanner;

public class MovieDriver_Task2 {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);

		String choice = "y";

		while (choice.equalsIgnoreCase("y")) {

			Movie movie = new Movie();

			System.out.print("Enter the title of a movie: ");
			String title = keyboard.nextLine();
			movie.setTitle(title);

			System.out.print("Enter the movie's rating: ");
			String rating = keyboard.nextLine();
			movie.setRating(rating);

			System.out.print("Enter the number of tickets sold at a (unnamed) theater: ");
			int soldTickets = keyboard.nextInt();
			movie.setSoldTickets(soldTickets);

			keyboard.nextLine(); // clears leftover line

			System.out.println("\n" + movie.toString());

			System.out.print("\nDo you want to enter another movie? (y/n): ");
			choice = keyboard.nextLine();
			System.out.println();
		}

		System.out.println("Goodbye!");
		keyboard.close();
	}
}
