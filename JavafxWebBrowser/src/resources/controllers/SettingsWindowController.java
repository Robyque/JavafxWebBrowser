package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class SettingsWindowController extends BorderPane{
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	@FXML 
	private Button GeneralTabButton,AppearanceButton,OthersTabButton;
	@FXML
	private TabPane SettingsTabPane;
	@FXML
	private Tab GeneralTab,AppearanceTab,OthersTab;
public SettingsWindowController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/SettingsWindow.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}
@FXML
private void initialize() {
	GeneralTabButton.setOnAction(a -> SettingsTabPane.getSelectionModel().select(GeneralTab));
	AppearanceButton.setOnAction(a -> SettingsTabPane.getSelectionModel().select(AppearanceTab));
	OthersTabButton.setOnAction(a -> SettingsTabPane.getSelectionModel().select(OthersTab));
}


}


