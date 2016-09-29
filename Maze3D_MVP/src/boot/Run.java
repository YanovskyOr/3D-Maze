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


public class Run {

	public static void main(String[] args) {
		
				
		/**************************************************************************************/
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

		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//PrintWriter out = new PrintWriter(System.out);
				
		//MyView view = new MyView(in, out);
		
		MazeWindow view = new MazeWindow();
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		view.start();
		
	}

}
