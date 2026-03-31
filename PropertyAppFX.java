import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PropertyAppFX extends Application {

    private ManagementCompany mc = new ManagementCompany("Campus Realty", "123-45-6789");

    @Override
    public void start(Stage primaryStage) {
        TextField nameField = new TextField();
        nameField.setPromptText("Property Name");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField rentField = new TextField();
        rentField.setPromptText("Rent Amount");

        TextField ownerField = new TextField();
        ownerField.setPromptText("Owner");

        Button addButton = new Button("Add Property");
        Button showButton = new Button("Show All Properties");
        Button totalButton = new Button("Show Total Rent");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String city = cityField.getText();
                String owner = ownerField.getText();
                double rent = Double.parseDouble(rentField.getText());

                if (name.isEmpty() || city.isEmpty() || owner.isEmpty()) {
                    outputArea.setText("Please fill in all fields.");
                    return;
                }

                Property p = new Property(name, city, rent, owner);
                int result = mc.addProperty(p);

                if (result == -1) {
                    outputArea.setText("Cannot add property. The array is full.");
                } else {
                    outputArea.setText("Property added successfully.");
                }

                nameField.clear();
                cityField.clear();
                rentField.clear();
                ownerField.clear();

            } catch (NumberFormatException ex) {
                outputArea.setText("Rent must be a valid number.");
            }
        });

        showButton.setOnAction(e -> outputArea.setText(mc.toString()));
        totalButton.setOnAction(e -> outputArea.setText("Total Rent: $" + mc.totalRent()));

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                nameField, cityField, rentField, ownerField,
                addButton, showButton, totalButton, outputArea
        );

        Scene scene = new Scene(root, 450, 500);
        primaryStage.setTitle("Property Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}