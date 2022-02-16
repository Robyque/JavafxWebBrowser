package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.validator.Arg;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXNodesList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomePageController extends BorderPane{
	@FXML
	private Button HomePageTab,BackButton,ForwardButton,RefreshButton,HomeButton,HistoryButton,LoadButton,AddNewTab,SettingsButton;
	
	@FXML
	private TextField SearchBar;
	
	@FXML
	private TabPane MainPageTabPane;
	
	@FXML
	private JFXDrawer JfxDrawer;
	
	@FXML
	private VBox LeftSideMenu;
	
	@FXML 
	private Slider ZoomSlider;
	
	@FXML
	private Parent root;
	
	public WebEngineController webEngineController;
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public HomePageController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}
	
	//--Initializeing area
	@FXML
	private void initialize() {
		//--Button icons
		ImageView AddNewTabGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/add-16px.png").toExternalForm(),
                16, 16, false, true));
		ImageView HistoryButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/wall-clock-16px.png").toExternalForm(),
                16, 16, false, true));
		
		ImageView SettingsButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/24px/settings-24px.png").toExternalForm(),
                16, 16, false, true));
		
		SettingsButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		AddNewTab.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		HistoryButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		
		SettingsButton.setGraphic(SettingsButtonGraphic);
		AddNewTab.setGraphic(AddNewTabGraphic);
		HistoryButton.setGraphic(HistoryButtonGraphic);
		
		//-Clear tabs if any and then creating a new one
		MainPageTabPane.getTabs().clear();
		CreateAndAddNewTab();
		
		//--Mapping button actions
		SettingsButton.setOnAction(a -> ShowSettingsWindow());
		HistoryButton.setOnAction(a -> ShowHistoryWindow());
		AddNewTab.setOnAction(a -> CreateAndAddNewTab());
		
	}
	//--Functions
	

	

	public void ShowSettingsWindow() {
		Stage SideMenuWindow = new Stage();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(new SettingsWindowController());
		Scene Menu = new Scene(borderPane);
		SideMenuWindow.initModality(Modality.APPLICATION_MODAL);
		SideMenuWindow.setScene(Menu);
		SideMenuWindow.show();
	}
	public void ShowHistoryWindow(){
		//Popup window code
		try {
		Stage SideMenuWindow = new Stage();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(new HistoryWindowController());
		Scene Menu = new Scene(borderPane);
		SideMenuWindow.initModality(Modality.APPLICATION_MODAL);
		SideMenuWindow.setScene(Menu);
		SideMenuWindow.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Adding new created tab
	public NewHomeTabController CreateAndAddNewTab() {
		NewHomeTabController newHomeTab = CreateNewTab();
		MainPageTabPane.getTabs().add(newHomeTab.getTab());
		return newHomeTab;
	}
	//Create new tab
	public NewHomeTabController CreateNewTab() {
		Tab tab = new Tab("Start Page");
		NewHomeTabController newTab = new NewHomeTabController(this, tab);
		
		tab.setOnCloseRequest(c -> {
			
			//Check the tabs number
			if (MainPageTabPane.getTabs().size() == 1)
				CreateAndAddNewTab();
			
		});
		
		return newTab;
		
	}
	
	public int TabCount() {
		return MainPageTabPane.getTabs().size();
	}	
}
