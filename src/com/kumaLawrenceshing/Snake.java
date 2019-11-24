package com.kumaLawrenceshing;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Kuma Lawrence
 */
public class Snake extends JFrame{
    public Snake(){
        add(new Game_Board());
        //
        setResizable(false);
        pack();
        
        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[]  args){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame ex = new Snake();
            }
        });
    }
}
