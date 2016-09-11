package boot;

import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args) throws Exception {
		
	
		MyController controller = new MyController();
		Model model = new MyModel(controller);
		MyView view = new MyView(controller);
		controller.setModelAndView(model, view);
		view.start();

	}

}
