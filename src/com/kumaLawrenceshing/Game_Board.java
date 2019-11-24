package com.kumaLawrenceshing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Kuma Lawrence Shing
 */
public class Game_Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 300;                          //size Of Board
    private final int B_HEIGHT = 300;                         //size Of Board
    private final int DOT_SIZE = 10;                         //size of the apple and dot of snake
    private final int ALL_DOTS = 900;                        //definition of max number of possible dots on board
    private final int RANDOM_POSITION = 29;                  //use to calculate random position of apple
    private final int DELAY = 140;                           //determines the speed of the game
    
    
    //store the x and y coordinates of all joints of snake
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    
    private int dots;
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    public Game_Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        loadImages();
        initGame();
    }
    
    //method to get images for the game
    private void loadImages(){
        ImageIcon iid = new ImageIcon("dot.png");
        ball = iid.getImage();
        
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        
        ImageIcon iih = new ImageIcon("head.png");
        head = iih.getImage();
        
    }
    // method that creates the snake, randomly locates the apple and starts the game timer
    private void initGame(){
        dots = 3;
        
        for (int z = 0; z<dots;z++){
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        locateApple();
        
        timer = new Timer(DELAY,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            
            for(int z = 0;z<dots;z++){
                if(z==0){
                    g.drawImage(head,x[z],y[z],this);
                }else{
                    g.drawImage(ball,x[z],y[z],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    private void gameOver(Graphics g){
        String msg = "Game Over";
        Font small = new Font("Helvetica",Font.BOLD,14);
        FontMetrics metr = getFontMetrics(small);
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH - metr.stringWidth(msg))/2, B_HEIGHT/2);
    }
    
    private void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();  
        }
    }
    //method to move snake 
    private void move(){
        
        for(int z = dots; z>0;z--){
          x[z] = x[(z-1)];
          y[z] = y[(z-1)];
        }
        
        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
        if(rightDirection){
            x[0] -= DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }
    //Method to check collision of head with apple or with wall
    private void checkCollision(){
        for (int z = dots;z>0;z--){
            if((z>4) && (x[0] ==x[z]) && (y[0] == y[z])){
                inGame = false;
            }
        }
        if (y[0] >= B_HEIGHT){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(x[0] >= B_WIDTH){
            inGame = false;
        }
        if(x[0]< 0){
            inGame = false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    //This method will randomly position a new apple object after collision
    private void locateApple(){
        int r  = (int)(Math.random() * RANDOM_POSITION);
        apple_x = ((r * DOT_SIZE));
        
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = ((r * DOT_SIZE));    
    }
    @Override
    public void actionPerformed(ActionEvent e){
        
      if(inGame){
          checkApple();
          checkCollision();
          move();
      }
      repaint();         
    }
    private class TAdapter extends KeyAdapter{
        
       public void KeyPressed(KeyEvent e){
           int key = e.getKeyCode();
           
           if((key == KeyEvent.VK_LEFT) && (!rightDirection)){
              leftDirection = true;
              upDirection = false;
              downDirection = false;
           }
           if((key == KeyEvent.VK_RIGHT) && (!leftDirection)){
              rightDirection = true;
              upDirection = false;
              downDirection = false;
           }
           if((key == KeyEvent.VK_UP) && (!downDirection)){
              upDirection = true;
              rightDirection = false;
              leftDirection = false;
           }
           if((key == KeyEvent.VK_DOWN) && (!upDirection)){
              downDirection = true;
              rightDirection = false;
              leftDirection = false;
           }
       }
    }
}
