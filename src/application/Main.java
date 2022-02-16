package application;
	
import controllers.HomePageController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class Main extends Application {
	public void start(Stage window) {
		try {
			//--Setting BorderPane
			BorderPane root = new BorderPane();
			root.setCenter(new HomePageController());
			//--Setting Scene
			Scene scene = new Scene(root,getVisualScreenWidth() /1.2,getVisualScreenHeight()/ 1.2);
			//--Setting Stage
			window.setTitle("Web Browser");
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//---<Getting screen with and height for perfect size on the screen>
	public static double getVisualScreenWidth() {
		return Screen.getPrimary().getVisualBounds().getWidth();
	    }

	
	public static double getVisualScreenHeight() {
		return Screen.getPrimary().getVisualBounds().getHeight();
	    }
	public static void main(String[] args) {
		launch(args);
	}
}
