package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;



/**
 * <h1>Basic Window is an interface that is an Observable and implements runnable </h1>
 *  other windows will implement basic Window
 *  @param display and shell are protcted
 *  
 *  abstract method initWidgets
 * Override method run initializes display and shell, runs the initWidgets method,opens shell(window)
 * a while loop that runs while the window isn't closed readAndDispatch: read events, put then in a queue.
		    dispatch the assigned listener.if there are no events in Queue display goes to sleep
 * <BR>
 * 
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 * @version 1.0
 * @since 2016-09-29
 */

public abstract class BasicWindow extends Observable implements Runnable {

	
	
	
	protected Display display;
	protected Shell shell;
	
	
	
	protected abstract void initWidgets();
	
	@Override
	public void run() {
		display = new Display();  // our display
		shell = new Shell(display); // our window

		initWidgets();
		
		shell.open();
		
		// main event loop
		while(!shell.isDisposed()){ // while window isn't closed
		
		   // 1. read events, put then in a queue.
		   // 2. dispatch the assigned listener
		   if(!display.readAndDispatch()){ 	// if the queue is empty
		      display.sleep(); 			// sleep until an event occurs 
		   }
		
		} // shell is disposed

		display.dispose(); // dispose OS components
	}

}
