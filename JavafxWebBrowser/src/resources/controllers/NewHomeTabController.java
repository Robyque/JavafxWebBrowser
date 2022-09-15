package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXNodesList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NewHomeTabController extends StackPane{
	
	private final Tab tab;
	private final HomePageController homePageController;
	private  WebEngineController webEngineController;
	
	private String WebSite;
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	@FXML
	private HBox HomeTopSearchBar;
	
	@FXML
	private TextField SearchBar,WelcomePageSearchBar;
	
	@FXML
	private Button HomePageSearchIconButton,HomePageBrowserIconButton,TabBackButton,TabForwardButton,TabReloadButton,TabHomeButton;
	@FXML
	private Button HomePageFacebookButton,HomePageInstagramButton,HomePageRedditButton,HomePageTwitterButton,HomePageYoutubeButton,AddSpeedDialWebSite;
	//----
	public NewHomeTabController(HomePageController homePageController, Tab tab) {
		this.homePageController = homePageController;
		this.tab = tab;
		//this.WebSite = webSite;
		this.tab.setContent(this);
		this.tab.setGraphic(createTabButton());
		//--fxml-loader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/StackPaneHomePageTab.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);

		}
	}
	//----
	//--Functions
	private Button createTabButton() {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResource("../icons/16px/close-16px.png").toExternalForm(),
                16, 16, false, true));
        button.setGraphic(imageView);
        button.maxHeight(16);
        button.maxWidth(16);
        button.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
        button.setOnAction(a -> CloseTab());
        //button.getStyleClass().add("tab-button");
        return button;
    }
	
	public void SearchLink(String url) {
		tab.setContent(createNewSearchTab(url));
	}
	
	public WebEngineController createNewSearchTab(String webSite) {
		//Tab tabID = (Tab) tab.getTabPane().getTabs();
		WebEngineController webEngine = new WebEngineController(this,getTab(),webSite);
		return webEngine;
	}
	
	public void CloseTab() {

		if((homePageController.TabCount()-1)==0) {
			System.exit(0);
		}
		tab.getTabPane().getTabs().remove(tab);
	}
	
	//--Initializing functions
	@FXML
	private void initialize() {
		//--Other actions
		
		SearchBar.setOnAction(a -> SearchLink(SearchBar.getText()));
		WelcomePageSearchBar.setOnAction(a -> SearchLink(WelcomePageSearchBar.getText()));
		//--Items creation
		
		
		TabBackButton.setDisable(true);
		TabForwardButton.setDisable(true);
		TabHomeButton.setDisable(true);
		
		//--Buttons actions
		HomePageSearchIconButton.setOnAction(a -> SearchLink(WelcomePageSearchBar.getText()));
		HomePageBrowserIconButton.setOnAction(a -> SearchLink("www.google.com"));
		HomePageFacebookButton.setOnAction(a -> SearchLink("www.facebook.com"));
		HomePageInstagramButton.setOnAction(a -> SearchLink("www.instagram.com"));
		HomePageRedditButton.setOnAction(a -> SearchLink("www.reddit.com"));
		HomePageTwitterButton.setOnAction(a -> SearchLink("www.twitter.com"));
		HomePageYoutubeButton.setOnAction(a -> SearchLink("www.youtube.com"));
		
		//--Buttons icons
		
		ImageView TabBackButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/left-arrow-16px.png").toExternalForm(),
                16, 16, false, true));
		
		ImageView TabForwardButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/next-16px.png").toExternalForm(),
                16, 16, false, true));
		
		ImageView TabReloadButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/reload-16px.png").toExternalForm(),
                16, 16, false, true));
		
		ImageView TabHomeButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/house-16px.png").toExternalForm(),
                16, 16, false, true));
		
		
		TabBackButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		TabForwardButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		TabReloadButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		TabHomeButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		
		TabBackButton.setGraphic(TabBackButtonGraphic);
		TabForwardButton.setGraphic(TabForwardButtonGraphic);
		TabReloadButton.setGraphic(TabReloadButtonGraphic);
		TabHomeButton.setGraphic(TabHomeButtonGraphic);		
		
	}
		
	public Tab getTab() {
		return tab;
	}
	
}
