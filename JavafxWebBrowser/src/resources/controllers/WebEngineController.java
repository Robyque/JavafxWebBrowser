package controllers;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.UrlValidator;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.stage.StageStyle;

public class WebEngineController extends StackPane{

 @FXML
 private WebView StackPaneWebPage;
 
 @FXML
 private TextField SearchBar;
 
 @FXML
 private Button StackPaneBackButton,StackPaneForwardButton,StackPaneRefreshButton,StackPaneHomeButton;
 
 @FXML
 private WebEngine webEngine;
 private WebHistory history;
 private ObservableList<WebHistory.Entry> historyEntryList;
 
 double WebZoom;
 
 public HomePageController homePageController;

 private final NewHomeTabController newHomeTabController;
 private Tab tab;
 private Slider slider;
 private String SearchUrl;
 private final Logger logger = Logger.getLogger(getClass().getName());
//----------------------


 
 
 public WebEngineController(NewHomeTabController newHomeTabController,Tab tab, String SearchUrl) {
	 	this.newHomeTabController = newHomeTabController;
		this.tab = tab;
		this.SearchUrl = SearchUrl;
		this.tab.setContent(this);
		
		//---<FXMLoader>
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/StackPaneWebEngineTab.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
	}
 
//----------------------
@FXML
private void initialize() {
	//--Web Engine
		StackPaneWebPage.setOnMouseMoved(a -> SetTabName());
		StackPaneWebPage.setOnMouseClicked(a -> SetTabName());
		webEngine = StackPaneWebPage.getEngine();
		WebZoom = 1;
		//--Making custom context menu for web engine page
		ContextMenu webEngineContextMenu = new ContextMenu();
		CheckMenuItem cookieStorage = new CheckMenuItem();
		webEngineContextMenu.getItems().add(cookieStorage);
		//--Disabling default context menu 
		StackPaneWebPage.setContextMenuEnabled(false);
		//--Setting custom context menu for web page
		StackPaneWebPage.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
            	webEngineContextMenu.show(StackPaneWebPage, e.getScreenX(), e.getScreenY());
            } else {
            	webEngineContextMenu.hide();
            }
        });
	//--History
		setHistory(webEngine.getHistory());
		historyEntryList = getHistory().getEntries();
		SimpleListProperty<Entry> list = new SimpleListProperty<>(historyEntryList);
	//--Search Bar
		webEngine.getLoadWorker().runningProperty().addListener((observable , oldValue , newValue) -> {			
			if (!newValue) // if !running
				SearchBar.textProperty().unbind();
			else
				SearchBar.textProperty().bind(webEngine.locationProperty());
		});
		
		SearchBar.setOnAction(a -> loadWebSite(SearchBar.getText()));
		SearchBar.focusedProperty().addListener((observable , oldValue , newValue) -> {
			if (newValue)
				Platform.runLater(() -> SearchBar.selectAll());
		});
		
		loadWebSite(SearchUrl);
		SetTabName();
		//--Buttons
		StackPaneHomeButton.setOnAction(a -> HomeTab());
		//StackPaneSideMenuButton.setOnAction(a -> SetTabName());
		StackPaneBackButton.setOnAction(a -> PageBack());
		StackPaneForwardButton.setOnAction(a -> PageForward());
		StackPaneRefreshButton.setOnAction(a -> RefreshPage());
		//--Button icons
		ImageView StackPaneBackButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/left-arrow-16px.png").toExternalForm(),
                16, 16, false, true));
		ImageView StackPaneForwardButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/next-16px.png").toExternalForm(),
                16, 16, false, true));
		ImageView StackPaneRefreshButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/reload-16px.png").toExternalForm(),
                16, 16, false, true));
		ImageView StackPaneHomeButtonGraphic = new ImageView(new Image(getClass().getResource("../icons/16px/house-16px.png").toExternalForm(),
                16, 16, false, true));
		
		StackPaneBackButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		StackPaneForwardButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		StackPaneRefreshButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		StackPaneHomeButton.getStylesheets().add(getClass().getResource("../css/TabButton.css").toExternalForm());
		
		StackPaneBackButton.setGraphic(StackPaneBackButtonGraphic);
		StackPaneForwardButton.setGraphic(StackPaneForwardButtonGraphic);
		StackPaneRefreshButton.setGraphic(StackPaneRefreshButtonGraphic);
		StackPaneHomeButton.setGraphic(StackPaneHomeButtonGraphic);		
		//--Slider
		
		//--Cookie manager
		cookieStorage.selectedProperty().addListener((observable , oldvalue , newvalue) -> {
			if (newvalue) {
				CookieManager manager = new CookieManager();
				manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
				CookieHandler.setDefault(manager);
				
				//it can save cookie on disk upon exit of application for next startup you can retrieve
				CookieStore store = manager.getCookieStore();
				try {
					URI uriadd = new URI(getHistory().getEntries().get(getHistory().getCurrentIndex()).getUrl());
					store.add(uriadd, new HttpCookie("name", "value"));
					
					
				} catch (URISyntaxException e1) {
					
					e1.printStackTrace();
				}
				
				//get cookie implementation
				try {
					
					URI getcookie = new URI(getHistory().getEntries().get(getHistory().getCurrentIndex()).getUrl());
					store.get(getcookie);
				} catch (URISyntaxException e1) {
					
					e1.printStackTrace();
				}
				
			} else {
				CookieManager manager = new CookieManager();
				manager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initStyle(StageStyle.UTILITY);
				alert.setTitle("COOKIES STATUS");
				alert.setHeaderText(null);
				alert.setContentText("Browser DISABLED COOKIES NO TRACKING");
				
				alert.showAndWait();
			}
			
		});
		//---<WebPageShortcuts>
		StackPaneWebPage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    final KeyCombination keyComb = new KeyCodeCombination(KeyCode.ENTER);
		    		public void handle(KeyEvent ke) {
		    			if (keyComb.match(ke)) {
		    				displayHistory();
		    				ke.consume();
		    			}
		    		}
		});
		
		StackPaneWebPage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    final KeyCombination keyComb = new KeyCodeCombination(KeyCode.ADD,KeyCombination.CONTROL_ANY);
		    		public void handle(KeyEvent ke) {
		    			if (keyComb.match(ke)) {
		    				zoomIn();
		    				ke.consume();
		    			}
		    		}
		});
		
		StackPaneWebPage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    final KeyCombination keyComb = new KeyCodeCombination(KeyCode.SUBTRACT,KeyCombination.CONTROL_ANY);
		    		public void handle(KeyEvent ke) {
		    			if (keyComb.match(ke)) {
		    				zoomOut();
		    				ke.consume();
		    			}
		    		}
		});
}
//----------------------

public void HomeTab() {
	NewHomeTabController homeTab = new NewHomeTabController(null, getTab());
	Tab tab = getTab();
	tab.setContent(homeTab);
	tab.setText("Start Page");
}


//--Setting website name to tab
public void SetTabName(){
	history = webEngine.getHistory();
	ObservableList<WebHistory.Entry> entries = history.getEntries();
	Tab tab = getTab();
	for(WebHistory.Entry entry : entries) {
		String url = entry.getUrl();
		int pos = url.indexOf(".");
		String tabName = url.substring(pos+1);
		int pos2 = tabName.indexOf(".");
		String newTabName = tabName.substring(0 ,pos2);
		tab.setText(newTabName);
		
	}


    
}
//--Search on web function
public void loadWebSite(String UrlLink) {
	//--Web Engines
	String BingEngine = "http://www.bing.com/search?q=";
	String DuckDuckGoEngine = "https://duckduckgo.com/?q=";
	String GoogleEngine = "https://www.google.com/search?q=";
	String YahooEngine = "https://search.yahoo.com/search?p=";
	//--Valid website checking
		String load = !new UrlValidator().isValid(UrlLink) ? null : UrlLink;
	//--Checking if Url link has https://
	int pos = UrlLink.indexOf("w");
	if(pos != -1) {
		try {
			webEngine.load(
					load != null ? load : "https://"+ URLEncoder.encode(UrlLink, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}else {
		try {
			webEngine.load(
					load != null ? load : GoogleEngine + URLEncoder.encode(UrlLink, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
	}
	SetTabName();
	displayHistory();
 }

//--Refresh web page function
 public void RefreshPage() {
	webEngine.reload();
 }
 //--Page Zoom
 public void zoomIn() {
	 WebZoom += 0.25;
	 StackPaneWebPage.setZoom(WebZoom);
 }
 
 public void zoomOut() {
	 WebZoom -= 0.25;
	 StackPaneWebPage.setZoom(WebZoom);
 }
 
 
 //--Database 
 public static void insert(String Link, String Date) {
	//---Database Connection
	 Connection con = DBConnection.connect();
	 PreparedStatement ps = null;
	 try {
		//----
		 String sql = "INSERT INTO History(Link,Date) VALUES(?,?)";
		 ps = con.prepareStatement(sql);
		 ps.setString(1, Link);
		 ps.setString(2, Date);
		 ps.execute();
	 }catch(SQLException e){
		 System.out.println(e.toString());
	 }
 }
 //--Show history in console 
 public void displayHistory() {
	//---
	history = webEngine.getHistory();
	ObservableList<WebHistory.Entry> entries = history.getEntries();
	//---	  
	for(WebHistory.Entry entry : entries) {
		String url = entry.getUrl();
		Date date = entry.getLastVisitedDate();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		String strDate = dateFormat.format(date);  
		insert(url,strDate);
		}
		 	 	 
 }
 
 public void PageBack() {
	 getHistory().go(historyEntryList.size() > 1 && getHistory().getCurrentIndex() > 0 ? -1 : 0);
	 
	 if(historyEntryList.size() == 2) {
		 NewHomeTabController homeTab = new NewHomeTabController(null, getTab());
		 Tab tab = getTab();
		 tab.setContent(homeTab);
		 tab.setText("Start Page");
	 }
	 
 }
 
 public void PageForward() {
	 getHistory().go(historyEntryList.size() > 1 && getHistory().getCurrentIndex() < historyEntryList.size() - 1 ? 1 : 0);
 }
 

 public WebView getWebView() {
		return StackPaneWebPage;
	}
 
 public Tab getTab() {
		return tab;
	}
 
 public Slider getSlider() {
	 return slider;
 }
 
 public WebHistory getHistory() {
		return history;
	}
 
 public void setHistory(WebHistory history) {
		this.history = history;
	}
 
}
