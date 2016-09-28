package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.Solution;
import algorithms.search.State;


public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay;
	String mazeName;
	
	

	
	@Override
	protected void initWidgets() {
		
		final Image small = new Image(shell.getDisplay(),"images/icon_16.png");
		final Image large = new Image(shell.getDisplay(),"images/icon_32.png");
		final Image[] images = new Image[] { small, large };
		shell.setImages(images);
		shell.setSize(800, 600);
		
	    shell.setLocation(shell.getDisplay().getBounds().width / 2 - 400, shell.getDisplay().getBounds().height / 2 - 300);

		GridLayout gridLayout = new GridLayout(3, false);
		
		shell.setLayout(gridLayout);
		
		Composite btnGroup = new Composite(shell, SWT.BORDER);
		
		RowLayout btnRowLayout = new RowLayout(SWT.VERTICAL);
		
		btnGroup.setLayout(btnRowLayout);
		
		btnGroup.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
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
				 shell.setText("Choose solving method");
				 shell.setSize(400, 200);

			     shell.setLocation(shell.getDisplay().getBounds().width / 2 - 200, shell.getDisplay().getBounds().height / 2 - 100);

			    
				 
				 final Image small = new Image(shell.getDisplay(),"images/icon_16.png");
				 final Image large = new Image(shell.getDisplay(),"images/icon_32.png");
				 final Image[] images = new Image[] { small, large };
				 shell.setImages(images);
		
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
		
		Button btnHint = new Button(btnGroup, SWT.PUSH);
		btnHint.setText("Hint");
		
		btnHint.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			//TODO:FIXTHISS
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				mazeDisplay.getMaze().setStartPosition(mazeDisplay.getCharacter().getPos());

				setChanged();
				notifyObservers("give_hint " + mazeDisplay.getCharacter().getPos().toString() + " " + mazeName + " bfs");
				
				

			}
		
		});
		
		  Menu menuBar, fileMenu;
		  MenuItem fileMenuHeader;
		  MenuItem fileExitItem, fileSaveItem, fileLoadItem;
		  
		  Label label;
		  label = new Label(shell, SWT.CENTER);
		  label.setBounds(shell.getClientArea());
		  menuBar = new Menu(shell, SWT.BAR);
		    fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		    fileMenuHeader.setText("&File");

		    fileMenu = new Menu(shell, SWT.DROP_DOWN);
		    fileMenuHeader.setMenu(fileMenu);

		    fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		    fileSaveItem.setText("&Save Maze");
		    
		    fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		    fileLoadItem.setText("&Load Maze");
		    
		    fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		    fileExitItem.setText("&Exit");
		    
		    fileExitItem.addSelectionListener(new SelectionListener(){
		    	
		    	public void widgetSelected(SelectionEvent event) {
		    		 notifyObservers("exit ");
			         shell.close();
			         display.dispose();
			    }

				
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
						
				}
		    });
		    
		    
		    fileSaveItem.addSelectionListener(new SelectionListener(){
		    	
		        public void widgetSelected(SelectionEvent event) {
			          label.setText("Saved");
			        }

			        public void widgetDefaultSelected(SelectionEvent event) {
			          label.setText("Saved");
			        }
		    });
		    
		    fileSaveItem.addSelectionListener(new SelectionListener(){
		        public void widgetSelected(SelectionEvent event) {
			          label.setText("Loaded");
			        }

			        public void widgetDefaultSelected(SelectionEvent event) {
			          label.setText("Loaded");
			        }
		    });
		    
		    shell.setMenuBar(menuBar);


		        
	}
  
	
	
	
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);
		
		shell.setLocation(shell.getDisplay().getBounds().width / 2 - 150, shell.getDisplay().getBounds().height / 2 - 100);
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		final Image small = new Image(shell.getDisplay(),"images/icon_16.png");
		final Image large = new Image(shell.getDisplay(),"images/icon_32.png");
		final Image[] images = new Image[] { small, large };
		shell.setImages(images);
		
		
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
				mazeName=txtName.getText();
				
				if(mazeDisplay != null) {
					mazeDisplay.dispose();
					mazeDisplay = null;
				}
				setChanged();
				notifyObservers("generate_maze " + mazeName + " " + txtFloors.getText() + " " + txtRows.getText() + " " + txtCols.getText());
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
		

		if(mazeDisplay == null)
			mazeDisplay = new MazeDisplay(shell, SWT.NONE);
			
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
		mazeDisplay.setMaze(maze);
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




	@Override
	public void displayHint(Position pos) {
		mazeDisplay.hint.setPos(pos);
		mazeDisplay.setHint();
	}




//
//	@Override
//	public void displayHint(Solution<Position> solution) {
//		Solution<Position> positions = solution;
//		Position firstPos = positions.getStates().get(0).getValue();
//		mazeDisplay.hint.setPos(firstPos);
//		mazeDisplay.setHint();
//	}






}
