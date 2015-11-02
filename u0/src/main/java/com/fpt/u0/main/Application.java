package com.fpt.u0.main;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Application
 *
 * @author senycorp
 */
public class Application extends javafx.application.Application{

	private Button[][] btnArray;
	private Label lblOn;
	private Label lblOff;

	public static void main(String[] args) {
		System.out.println("Launching application...");
		javafx.application.Application.launch(args);
	}

	@Override
	/**
	 * Test sufian
	 * Start GUI-Application
	 */
	public void start(Stage primaryStage) throws Exception {
		this.btnArray = new Button[5][5];

		GridPane frame = new GridPane();

		frame.setHgap(1.5);
		frame.setVgap(1.5);
		frame.setPadding(new Insets(0, 0, 0, 0));

		// Create buttons in array
		for (int i = 0 ; i < 5 ; i++) {
			for (int j = 0 ; j < 5 ; j++) {
				// Create button
				this.btnArray[i][j] = new Button();
				// Add it to the frame
				frame.add(this.btnArray[i][j], i+1, j);

				// Setting some style properties
				this.btnArray[i][j].setMinSize(60, 70);
				this.btnArray[i][j].setStyle("-fx-background-color:   Blue");

				// Set coordinates of button as user data
				ButtonState btnState = new ButtonState(i, j , false);
				this.btnArray[i][j].setUserData(btnState);

				// Set action handler
				this.btnArray[i][j].setOnAction(e -> {btnClick(e);});
			}
		}

		// Introduce reset button
		Button btnReset = new Button("Reset");
		btnReset.setOnAction(e -> {
			// Create buttons in array
			for (int i = 0 ; i < 5 ; i++) {
				for (int j = 0 ; j < 5 ; j++) {
					((ButtonState)this.btnArray[i][j].getUserData()).setState(false);
				}
			}

			this.drawBtns();
		});

		// Introduce labels
		this.lblOn = new Label("0");
		this.lblOff = new Label("25");

		frame.add(btnReset, 1, 7);
		frame.add(lblOn, 4, 7);
		frame.add(lblOff, 5, 7);

		// Setting some Styling
		lblOff.setStyle("-fx-background-color:   Blue");
		lblOn.setStyle("-fx-background-color:   yellow");

		// Create scene and prepare stage
		Scene scene = new Scene(frame,311,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hello World");
		primaryStage.show();
	}

	/**
	 * Event handler for buttons in grid pane
	 *
	 * @param e
	 */
	public void btnClick(Event e) {
		Object source = e.getSource();

		if (source instanceof Button) { //should always be true in your example
		    Button clickedBtn = (Button) source; // that's the button that was clicked
		    ButtonState btnState = ((ButtonState)clickedBtn.getUserData());
		    System.out.println(clickedBtn.getUserData());

		    // Check for false state of clicked button
		    //if (btnState.getState() == false) {
		    	// Set to true
		    	btnState.invert();

			    // Set states of neighbours
			    if ((btnState.getRow() - 1) >= 0) {
			    	((ButtonState) this.btnArray[btnState.getRow() - 1][btnState.getColumn()].getUserData()).invert();
			    	System.out.println((btnState.getRow()) +" "+ (btnState.getColumn()+1));
			    }

			    if ((btnState.getRow() + 1) <= 4) {
			    	((ButtonState)this.btnArray[btnState.getRow() + 1][btnState.getColumn()].getUserData()).invert();
			    	System.out.println((btnState.getRow() + 2) +" "+ (btnState.getColumn()+1));
			    }

			    if ((btnState.getColumn() - 1) >= 0) {
			    	((ButtonState)this.btnArray[btnState.getRow()][btnState.getColumn()-1].getUserData()).invert();
			    	System.out.println((btnState.getRow() + 1) +" "+ (btnState.getColumn()));
			    }

			    if ((btnState.getColumn() + 1) <= 4) {
			    	((ButtonState)this.btnArray[btnState.getRow()][btnState.getColumn()+1].getUserData()).invert();
			    	System.out.println((btnState.getRow() + 1) +" "+ (btnState.getColumn()+2));
			    }
		    //}

		    // Draw buttons
		    this.drawBtns();
		}
	}

	/**
	 * Mark Buttons
	 */
	private void drawBtns() {
		int on = 0;

		// Iterate over all buttons an count the states
		for (int i = 0 ; i < 5 ; i++) {
			for (int j = 0 ; j < 5 ; j++) {
					if (((ButtonState)(this.btnArray[i][j].getUserData())).getState() == true ) {
						this.btnArray[i][j].setStyle("-fx-background-color:   yellow");
						on++;
					} else {
						this.btnArray[i][j].setStyle("-fx-background-color:   Blue");
					}
			}
		}

		// Set label text
		this.lblOff.setText(Integer.toString(25-on));
		this.lblOn.setText(Integer.toString(on));
	}

}
