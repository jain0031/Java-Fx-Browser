package browser;
/**
 @author:- Vaibhav jain  (040884087)   */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.scene.web.WebHistory.Entry;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MyJavaFXBrowser extends Application
{
	public ObservableList<WebHistory.Entry> list;
	public ListView<WebHistory.Entry> historyList;
	 final public String xml="";
	public ArrayList<String> myList = new ArrayList<>();

	final String defaultfile = "default.web";
	final String bookmarks = "bookmarks.web";

	@Override
	public  void start(Stage primaryStage)
	{

		WebPage currentPage = new WebPage();
		WebView webView = currentPage.getWebView();
		WebEngine webEngine = currentPage.createWebEngine(primaryStage);
		
		
		Label label = new Label("Enter the address");
		
		TextField textField = new TextField();
		
		textField.setPrefWidth(650);
		textField.setText(loadDefault(webEngine));

		
		Button goButton = new Button("Go!");
		
		Button gofo=new Button("Forward");
		
		Button gobo = new Button("Backward");
		Button display=new Button ("Display code");
		
		gofo.setOnAction((event) -> {
			
			goForward(webEngine);
			
		});
		
		gobo.setOnAction((event)->{
		
			goBack(webEngine);
		});
		
		goButton.setOnAction(event ->
		{
		
			
			
			String s =  textField.getText();
			
			if(s.contains("http://www."))
			
			{
				webEngine.load(s);
			}
			else
			{
				if(s.contains("www."))
				{
					s = "http://" + textField.getText();
				}
				else
				{
					s = "http://www." + textField.getText();
				}
				webEngine.load(s);
				textField.setText(s);
			}
		});
	
		textField.setOnKeyPressed(
				(event)->{
				if(event.getCode() == KeyCode.ENTER){
				String http = textField.getText();
			     if(http.contains("http://www."))
						
					{
						webEngine.load(http);
					}
					else
					{
						if(http.contains("www."))
						{
							http = "http://" + textField.getText();
						}
						else
						{
							http= "http://www." + textField.getText();
						}
				
				
				webEngine.load(http.toString());	
				textField.setText(http);
					}
				}
				});

		list = FXCollections.observableArrayList();
	 ListView<Entry>historyList = new ListView<WebHistory.Entry>(list);
		webEngine.getLoadWorker().stateProperty().addListener(

				( ov, oldState, newState)-> {

				// This if statement gets run if the new page load succeeded.
				if (newState == State.SUCCEEDED) {	

				//sets the WebHistory list to items from WebEngine history 
				list.setAll(webEngine.getHistory().getEntries());

				//displays current url in searchBar textField
				textField.setText( webEngine.getLocation());

				//enables or disables the back button depending on page history index
				if(list.size() >= 1 && webEngine.getHistory().getCurrentIndex() != 0){
				gobo.setDisable(false);
				}else{
				gobo.setDisable(true);
				}
				//enables or disables the forward button depending on page history index
				if(webEngine.getHistory().getCurrentIndex() < (list.size() - 1 ) ){
				gofo.setDisable(false);	
				}else {
				gofo.setDisable(true);
				}	
				//enables or disables bookmark button depending on whether current url is an id for
				//MenuItem in observable list for Menu	
					
				}
				});
		display.setOnAction((action) -> {
			
			
		});
		
		try {
			URL url=new URL(textField.getText());
		} catch (MalformedURLException e) {
	
			e.printStackTrace();
		}
		TextField  code=new TextField();
		code.setPrefHeight(80);

		HBox hBox = new HBox();
		HBox hbox1=new HBox();
		
		
		hBox.getChildren().addAll(label, textField, goButton);
       hbox1.getChildren().addAll(gofo,gobo);       
		HBox.setHgrow(textField, Priority.ALWAYS);
		
		VBox vBox= new VBox();
	
		VBox vbox1=new VBox();
		vbox1.getChildren().addAll(historyList,hbox1);
		
		MenuBar menuBar = Menus.getMenuBar();
		
		Menu menuFile = Menus.getMenuFile();
		
		MenuItem refreshItem = Menus.getMnuItmRefresh(webEngine);
		
		MenuItem exitItem = Menus.getMnuItmExit();
	
		
		menuFile.getItems().addAll(refreshItem, exitItem);
		
		Menu menuSettings = Menus.getMnuSettings();
		
		MenuItem toggleAddressBarItem = Menus.mnuItmToggleAddressBar(vBox,hBox);
		
		MenuItem changeStartupItem = Menus.mnuItmChangeStartup(textField);

		
		Menu menuBookmarks = Menus.getMnuBookmarks(webEngine, myList, textField);
		
		MenuItem addBookmarkItem = Menus.mnuItmAddBookmark(webEngine, myList, textField);
		
		menuBookmarks.getItems().add(0, addBookmarkItem);
		
		menuBookmarks.getItems().add(1, new SeparatorMenuItem());
		
		Menu menuHelp = Menus.getMnuHelp();
		
		MenuItem aboutItem = Menus.getmnuItmAbout();
		
		menuHelp.getItems().addAll(aboutItem);
		
		menuBar.getMenus().addAll(menuFile, menuSettings, menuBookmarks, menuHelp);
		
		vBox.getChildren().addAll(menuBar,hBox);
		BorderPane root = new BorderPane();
		
		
     
		MenuItem history=Menus.getHistory(root,vbox1);

		MenuItem display1= Menus.getDisplayCode(root,webEngine,code);
		
		menuSettings.getItems().addAll(toggleAddressBarItem, changeStartupItem,history,display1);
		
		root.setTop(vBox);
		
		root.setRight(vbox1);
		
		root.setBottom(code);
		
		root.setCenter(webView);
     
	
		Scene scene = new Scene(root, 800, 500);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();	

	}
	
	private void addBookmarksToFile(ArrayList<String> myList, String file)
	{
		File f = new File(file);
		
		if(!FileUtils.fileExists(file))
		
		{
		
			try
			
			{
			
				f.createNewFile();
			
			}
			
			catch (IOException e) {
			
				e.printStackTrace();
			
			}
		}
		
		FileUtils.saveFileContents(f, myList);
	}


	private String loadDefault(WebEngine webEngine){
		
		File f = new File(defaultfile);
		
		if(FileUtils.fileExists(f))
		
		{
		
			ArrayList<String> linList = FileUtils.getFileContentsAsArrayList(f);
			
			if(linList != null)
			
			{
			
				try{
				
					webEngine.load(linList.get(0));
					
					return linList.get(0);
				
				} catch(Exception e){
				
					System.out.println("Error in reading the file " + e.getMessage());
					
					webEngine.load("http://google.ca");
				}
				
			}
		}
		else
		{
			try{
				f.createNewFile();
				ArrayList<String> arrayList = new ArrayList<>();
				arrayList.add("http://google.ca");
				FileUtils.saveFileContents(f, arrayList);
				webEngine.load(arrayList.get(0));
				return arrayList.get(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static void goForward(WebEngine engine){

		final WebHistory history=engine.getHistory();
		ObservableList<WebHistory.Entry> entryList=history.getEntries();
		int currentIndex=history.getCurrentIndex();

		if(currentIndex + 1 < entryList.size()){	
		//This is a no-parameter Lambda function run();
		Platform.runLater( () -> { 
		history.go(1); 
		final String nextAddress = history.getEntries().get(currentIndex + 1).getUrl();
		});
		} 
		}
		
	public static void goBack(WebEngine engine){
		
		final WebHistory history=engine.getHistory();
		ObservableList<WebHistory.Entry> entryList=history.getEntries();
		int currentIndex=history.getCurrentIndex();

		if(currentIndex > 0){
		//This is a no-parameter Lambda function run();
		Platform.runLater( () -> { 
		history.go(-1); 
		final String nextAddress = history.getEntries().get(currentIndex - 1).getUrl();
		});
		}
		}

	@Override
	public void stop()
	{
		addBookmarksToFile(myList, "bookmarks.web");
	}

	public static void main(String[] args) {
		Application.launch(args);
	}


}
