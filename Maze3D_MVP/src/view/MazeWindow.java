package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay;
	

	
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);				
		
		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		
		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");	
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		btnSolveMaze.addSelectionListener(new SelectionListener(){
			 
			@Override
			 public void widgetDefaultSelected(SelectionEvent arg0) {
			 
			 				
			 }
			 
			 @Override
			 public void widgetSelected(SelectionEvent arg0) {
				 Shell shell=new Shell();
				 shell.setText("enter solving method");
				 shell.setSize(300, 200);
		
				 Composite btnGroup = new Composite(shell, SWT.FILL);
				 RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
				 btnGroup.setLayout(rowLayout);
				 GridLayout layout = new GridLayout(2, false);
				 shell.setLayout(layout); 
	
				 Button solvebfs=  new Button(btnGroup,SWT.RADIO);
				 Button solvedfs=  new Button(btnGroup,SWT.RADIO);
				 solvebfs.setText("Bfs");
				 solvedfs.setText("Dfs");

				 shell.open();
				 solvebfs.addSelectionListener(new SelectionListener(){

					 @Override
					 public void widgetDefaultSelected(SelectionEvent arg0) {
						 // TODO Auto-generated method stub

					 }

					 @Override
					 public void widgetSelected(SelectionEvent arg0) {
						 setChanged();
						 notifyObservers("display_message " + "Bfs selected");
					 }
				 });
			 }
		});
	}
	
	
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
	
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		Text txtName = new Text(shell, SWT.BORDER);
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		Text txtCols = new Text(shell, SWT.BORDER);
		
		
		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		btnGenerate.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(txtName.getText().isEmpty()||txtFloors.getText().isEmpty()||txtRows.getText().isEmpty()||txtCols.getText().isEmpty()) {
					setChanged();
					notifyObservers("display_message " + "all fields must have a value,please enter maze name , floors,cols,rows");
					shell.close();
					return;
				}
				setChanged();
				notifyObservers("generate_maze " + txtName.getText() + " " + txtFloors.getText() + " " + txtRows.getText() + " " + txtCols.getText());
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
//		mazeDisplay = new MazeDisplay(shell, SWT.NONE);
		shell.open();
	}

	
	@Override
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Maze " + name + " is ready");
				msg.open();
				
				setChanged();
				notifyObservers("display " + name);
			}
		});	
	}

	@Override
	public void displayMaze(Maze3d maze) {
		
		Position startPos = maze.getStartPosition();
		
		int[][] mazeData = maze.getCrossSectionByZ(startPos.z);
		
		if(mazeDisplay == null)
			mazeDisplay = new MazeDisplay(shell, SWT.NONE);
			
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
		mazeDisplay.setMazeData(mazeData);
		mazeDisplay.requestLayout();
		
	}

	@Override
	public void print(String str) {
		display.syncExec(new Runnable() {
			 		
			 @Override
			 public void run() {
				MessageBox msg = new MessageBox(shell);
			 	msg.setMessage(str);
			 	msg.open();
			 }
		});
	}

	@Override
	public void printCrossSection(Maze3d maze, int[][] crossSec, int axis1, int axis2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printSolution(Solution<Position> mazeSolution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		run();
	}



}
