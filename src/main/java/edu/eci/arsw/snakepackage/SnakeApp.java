package edu.eci.arsw.snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import edu.eci.arsw.enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jd-
 *
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    public static Object monitor = new Object();
    
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2)};
    
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        frame.add(board,BorderLayout.CENTER);
        
        JPanel actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());
        actionsBPabel.add(new JButton("Action"));
        
        JButton startB = new JButton("Start");
        startB.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
        	  for (int i = 0; i != MAX_THREADS; i++) {
        		  if(!thread[i].isAlive()) {
        			  thread[i].start();
        		  }
              }
          }
        });
        
        JButton pauseB = new JButton("Pause");
        pauseB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	int longestSnakeSize=-1, longestSnakeIndex=-1;
            	Date worstSnakeDate=null; int worstSnakeIndex=-1;
            	for(int i=0; i!=MAX_THREADS; i++) {
            		snakes[i].setVisible(false);
            		snakes[i].setSnakeSleep(true);
            		
            		int SnakeSZ = snakes[i].getBody().size();
            		if(SnakeSZ>longestSnakeSize) {
            			longestSnakeSize = SnakeSZ;
            			longestSnakeIndex = i;
            		}
            		if(snakes[i].isSnakeEnd()) {
            			if(worstSnakeDate == null) {
            				worstSnakeDate = snakes[i].getDeathDate();
            			}else {
            				if(worstSnakeDate.after(snakes[i].getDeathDate())) {
            					worstSnakeDate = snakes[i].getDeathDate();
            					worstSnakeIndex = i;
            				}
            			}
            		}
            		
            	}
            	snakes[longestSnakeIndex].setVisible(true);
            	snakes[worstSnakeIndex].setVisible(true);
            }
        });
        
        JButton resumeB = new JButton("Resume");
        
        resumeB.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	synchronized (monitor) {
            		for(int i=0; i!=MAX_THREADS; i++) {
                		snakes[i].setSnakeSleep(false);
                		snakes[i].setVisible(true);
                	}
					monitor.notifyAll();
				}
            }
        });
        
        actionsBPabel.add(startB);
        actionsBPabel.add(pauseB);
        actionsBPabel.add(resumeB);
        frame.add(actionsBPabel,BorderLayout.SOUTH);
        
    }
   

    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void init() {
        
        
        for (int i = 0; i != MAX_THREADS; i++) {
            
            snakes[i] = new Snake(i + 1, spawn[i], i + 1);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
            //thread[i].start();
        }

        frame.setVisible(true);

            
        while (true) {
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
            }
            if (x == MAX_THREADS) {
                break;
            }
        }


        System.out.println("Thread (snake) status:");
        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("["+i+"] :"+thread[i].getState());
        }
        

    }
    
    public static SnakeApp getApp() {
        return app;
    }

}
