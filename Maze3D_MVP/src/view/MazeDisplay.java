package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	private Character character;
	Position startPos;
	
	public void setMazeData(int[][] mazeData, Position startPos) {
		this.mazeData = mazeData;
		this.startPos = startPos;
		this.redraw();
	}
	
	public MazeDisplay(Shell parent, int style) {
		super(parent, style);		
		
		

		this.addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				if (mazeData == null)
					return;
			
			   e.gc.setForeground(new Color(null,0,0,0));
			   e.gc.setBackground(new Color(null,96,31,31));
			   
			   int width=getSize().x;
			   int height=getSize().y;

			   int w=width/mazeData[0].length;
			   int h=height/mazeData.length;

			   for(int i=0;i<mazeData.length;i++)
			      for(int j=0;j<mazeData[i].length;j++){
			          int x=j*w;
			          int y=i*h;
			          if(mazeData[i][j]!=0)
			              e.gc.fillRectangle(x,y,w,h);
			      }
			   
			   if(character==null)
			   {
				   
				   character = new Character();
					character.setPos(startPos);   
			   }
			  character.draw(w, h, e.gc);
				
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener() {

		    @Override
		    public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	if(g.count > 0){
                        
                        int width = getSize().x;
                        int height = getSize().y;

                        setSize((int)(width * 1.05), (int)(height * 1.05));
                        redraw();

                    }
                    else {
                        

                        int width = getSize().x;
                        int height = getSize().y;

                        setSize((int)(width * 0.95), (int)(height * 0.95));
                        redraw();
                        }
		        }
		    }

		});
		
		this.addKeyListener(new KeyListener() {
		      
		    @Override
		    public void keyReleased(KeyEvent arg0) {
		      // TODO Auto-generated method stub
		  
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
		  switch (e.keyCode) {
		  
		  case SWT.ARROW_RIGHT: {
		    if(character.getPos().x < mazeData[0].length-1 && mazeData[(character.getPos().y)][(character.getPos().x)+1] == 0){
		      character.moveRight();
		      redraw();
		      break;
		    }
		    else
		      break;
		
		  }
		
		  
		  case SWT.ARROW_LEFT: {
		    if(character.getPos().x > 0 && mazeData[(character.getPos().y)][(character.getPos().x)-1] == 0)
		        {
		          character.moveLeft();
		          redraw();
		          break;
		        }
		        else
		          break;
		      }
		      
		      
		      case SWT.ARROW_UP:
		        if(character.getPos().y > 0 && mazeData[(character.getPos().y-1)][(character.getPos().x)] == 0)
		        {
		          character.moveForward();
		          redraw();
		          break;
		        }
		        else
		          break;
		        
		      case SWT.ARROW_DOWN:
		        if(character.getPos().y <  mazeData.length-1 && mazeData[(character.getPos().y+1)][(character.getPos().x)] == 0) {
		          character.moveBack();
		          redraw();
		          break;	
		        }
		        else
		          break;
		        
		      case SWT.PAGE_UP:
		        character.moveUp();
		        redraw();
		        break;			
		      case SWT.PAGE_DOWN:
		        character.moveDown();
		        redraw();
		        break;
		      }
		    }
		  });
		}
	}
