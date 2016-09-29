package boot;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;


import properties.Properties;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

/**
 * Main class to run the game
 * 
 * @author Or Yanovsky and Lilia Misotchenko
 *
 */
public class Run {

	/**
	 * The main class defines and exports the default properties and configuration.
	 * <BR>
	 * This is also where the MVP model comes together - model, view and presenter are all created and connected, then view initiates.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
				

			
			Properties prop = new Properties();
			prop.setNumOfThreads(10);
			prop.setGenerateMazeAlgorithm("GrowingTree");
			prop.setSolveMazeAlgorithm("bfs");

			XMLEncoder xmlEncoder = null;
			try {
				xmlEncoder = new XMLEncoder(new FileOutputStream("properties.xml"));
				xmlEncoder.writeObject(prop);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				xmlEncoder.close();
			} 
		
		
	
		/*************************************************************************************/
		// In case Command Line Interface is desired, uncomment those lines of code. 
		// Also comment out the existing "view" line.
			
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//PrintWriter out = new PrintWriter(System.out);
				
		//MyView view = new MyView(in, out);
			
		/**************************************************************************************/
		
		MazeWindow view = new MazeWindow();
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
		
	}

}
