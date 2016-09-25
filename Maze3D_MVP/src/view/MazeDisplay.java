package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
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
			   
			   
				
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener() {

		    @Override
		    public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	if(g.count > 0){
                        System.out.println("up");
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
	}
}
