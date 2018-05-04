 package browser;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Menus extends MyJavaFXBrowser
{

	private static MenuBar menuBar;
	private static Menu mnuFile;
	private static Menu mnuSettings;
	private static Menu mnuBookmarks;
	private static Menu mnuHelp;
	private static MenuItem mnuItmRefresh;
	private static MenuItem mnuItmExit;
	private static MenuItem mnuItmToggleAddressBar;
	private static MenuItem mnuItmChangeStartup;
	private static MenuItem mnuItmAddBookmark;
	private static MenuItem mnuItmAbout;
    private static MenuItem mnuItmHistory;
  private static MenuItem mnuItmDisplay;
	public static MenuBar getMenuBar()
	{
		menuBar = new MenuBar ();

		return menuBar;
	}

	public static MenuItem getMnuItmExit()
	{
		mnuItmExit = new MenuItem("_Exit");
	
		mnuItmExit.setOnAction((ActionEvent actionEvent) -> Platform.exit());
		mnuItmExit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		return mnuItmExit;
	}

	public static MenuItem getmnuItmAbout()
	{
		
		mnuItmAbout = new MenuItem("_About");
		
		mnuItmAbout.setOnAction(actionEvent ->
		
		{
		
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			
			alert.setTitle("Information Dialog");
			
			alert.setHeaderText(null);
			
			alert.setContentText("My Name :- Vaibhav Jain \n  My Student No. :-040884087 ");
			
			alert.showAndWait();
		});
      mnuItmAbout.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		return  mnuItmAbout;
	}
	
	public static MenuItem getMnuItmRefresh(WebEngine webEngine)
	{
		mnuItmRefresh = new MenuItem("_Refresh");
		
		mnuItmRefresh.setOnAction(actionEvent -> webEngine.reload());
		mnuItmRefresh.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		return mnuItmRefresh;
	}
	


	public static Menu getMenuFile()
	{
		mnuFile = new Menu("_File");

		return mnuFile;
	}


	public static Menu getMnuSettings()
	{
		mnuSettings=new Menu("Settings ");

		return mnuSettings;
	}

	  public static MenuItem mnuItmAddBookmark(WebEngine webEngine, ArrayList<String> myList, TextField textField)
	    {
	        mnuItmAddBookmark = new MenuItem("Add Bookmark");
	
	        mnuItmAddBookmark.setOnAction((actionEvent)->
	        
	        {
	            String newAdd = textField.getText();
	        
	            myList.add(newAdd);

	            MenuItem menuItem = new MenuItem(newAdd);
	            CustomMenuItem cm = new CustomMenuItem();
	            cm.setContent(new Label(newAdd));
	            
	            cm.getContent().setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						
					
					}
	            	
	            });
	            
	            mnuBookmarks.getItems().addAll(menuItem, cm);
	            menuItem.setOnAction((action )->
	            
	            {
	                textField.setText(newAdd);
	            
	                webEngine.load(newAdd);
	            });
	            
	            ContextMenu contextMenu = new ContextMenu();
	            MenuItem cut = new MenuItem("Cut");
	            
	            
	            
	        });
	        
	        mnuItmAddBookmark.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));
	        return mnuItmAddBookmark;
	    }

	   public static MenuItem mnuItmChangeStartup(TextField textField)
	    {
	        mnuItmChangeStartup = new MenuItem("Change Start-up Page");
	      
	        mnuItmChangeStartup.setOnAction((actionEvent ) ->
	        {
	            ArrayList<String> set = new ArrayList<>();
	        
	            set.add(textField.getText());
	            
	            System.out.println("" + textField.getText());

	            File f = new File("default.web");

	            if(FileUtils.fileExists(f))
	            {
	                FileUtils.saveFileContents(f, set);
	            }
	        });
	        mnuItmChangeStartup.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
	        return mnuItmChangeStartup;
	    }
	
	   public static Menu getMnuBookmarks(WebEngine webEngine, ArrayList<String> myList, TextField textField)
	    {
	        mnuBookmarks = new Menu("Bookmarks ");
	        
	        File f = new File("bookmarks.web");
	        
	        if(!FileUtils.fileExists(f))
	        {
	        
	        	try
	            {
	            
	        		f.createNewFile();
	            
	            } catch (IOException e)
	            
	        	{
	            
	            	e.printStackTrace();
	            }
	        }
	        
	        myList = FileUtils.getFileContentsAsArrayList(f);

	        for(String s : myList)
	        {
	            MenuItem menuItem = new MenuItem();
	    
	            menuItem.setText(s);
	            
	            menuItem.setOnAction((action )->
	            {
	                System.out.println(s);
	            
	                textField.setText(s);
	                
	                webEngine.load(s);
	            });
	            mnuBookmarks.getItems().add(menuItem);
	        }
	        
	        return mnuBookmarks;
	    }


	public static Menu getMnuHelp()
	{
		mnuHelp = new Menu("Help");

		return mnuHelp;
	}

	public static MenuItem mnuItmToggleAddressBar(VBox vBox, HBox hBox) {
		
		mnuItmToggleAddressBar=new MenuItem("Toggle Address Bar");
        mnuItmToggleAddressBar.setOnAction((actionEvent ) ->
        {

            if(vBox.getChildren().contains(hBox))
            {
                vBox.getChildren().remove(hBox);
            }
            else
            {
                vBox.getChildren().add(hBox);
            }
        });
        mnuItmToggleAddressBar.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
        return mnuItmToggleAddressBar;
	}


		public static MenuItem getHistory(BorderPane p, VBox g) {
		mnuItmHistory=new MenuItem("History");
		mnuItmHistory.setOnAction((actionEvent) -> {
			
			if(p.getChildren().contains(g)) {
				p.getChildren().remove(g);
			}
			else {
				p.getChildren().add(g);
			}
		});
		
		mnuItmHistory.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		return mnuItmHistory;
	}
		

	



	

		public static MenuItem getDisplayCode(BorderPane root, WebEngine webEngine, TextField code) {
			mnuItmDisplay = new MenuItem("Display ");
			mnuItmDisplay.setOnAction((actionEvent) -> {
				
				if(root.getChildren().contains(code)) {
					root.getChildren().remove(code);
				}
				else {
					root.getChildren().add(code);
				}
				try {
	                 
					TransformerFactory transformerFactory = TransformerFactory
	                      .newInstance();
	                  Transformer transformer = transformerFactory.newTransformer();
	                  StringWriter stringWriter = new StringWriter();
	                  transformer.transform(new DOMSource(webEngine.getDocument()),
	                      new StreamResult(stringWriter));
	                 String  xml = stringWriter.getBuffer().toString();
	                 code.setText(xml);
	                 
	                  
	                } catch (Exception e) {
	                  e.printStackTrace();
	                }
			});
			
			mnuItmDisplay.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN));
			return mnuItmDisplay;
		}
}
