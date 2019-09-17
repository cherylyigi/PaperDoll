import java.io.*;
import java.util.*;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;

import java.sql.Struct;



public class MenuView extends JPanel{

    private JButton Reset = new JButton("Reset (Ctrl-R)");
    private JMenu Menu = new JMenu("File");
    private JButton Quit = new JButton("Quit (Ctrl-Q)");
    private JButton Scaling = new JButton("Scaling");
    public Vector<Sprite> children = new Vector<Sprite>(); 
    Canvas canvas = new Canvas();

    /**
     * Create a new View.
     */
    public MenuView(Vector<Sprite> children, Canvas canvas) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.layoutView();
        this.children = children;
        this.canvas = canvas;
        this.registerControllers();

    }

    private void layoutView() {
		// Box is an easy-to-create JPanel with a BoxLayout
		Box b = Box.createHorizontalBox();
        

        Menu.add(Reset);
        Menu.addSeparator();
        Menu.add(Quit);
        //Menu.addSeparator();
        //Menu.add(Scaling);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(Menu);
        b.add(menuBar);
        this.add(b);
    }

    private void registerControllers() {
    
		this.Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				for (Sprite sprite : children) {
                    sprite.reset();
                    
                }
                canvas.repaint();
			}
        });
        
        
        this.Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
        });

        this.Scaling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				for (Sprite sprite : children) {
                    sprite.enable();  
                }
                canvas.repaint();
			}
        });
		
    }
     
}
