import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.IOException;
import com.github.javaparser.ParseException;
import java.rmi.activation.*;
import java.awt.event.*;



//import com.jgoodies.forms.factories.DefaultComponentFactory;


public class StartUpGUI  extends JPanel implements ActionListener{
    
    //Declaring variables
	public JFrame frame = new JFrame ("Java2UML 1.0");
	public JButton btn1, btn2, btnApply, btnExit;
	public JMenuItem menuItem1,menuItem2, menuItem3, menuItem4, menuItem5;
 	public JCheckBox chckbxNewCheckBox, chckbxNewCheckBox_1;
 	public File[] files;
   	public File fileSave;
   	public JFileChooser fc, sc;

   	JTextArea log= null;
   	String format1 = "", format2 = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartUpGUI window = new StartUpGUI();
 	
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    
	/**
	 * Create the application.
	 */
	public StartUpGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileName());
        
        sc = new JFileChooser();
        sc.setAcceptAllFileFilterUsed(true);

        //definding a frame
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
        //definding checkboxes
        chckbxNewCheckBox = new JCheckBox("yUML");
        chckbxNewCheckBox.setBounds(16, 6, 128, 23);
        frame.getContentPane().add(chckbxNewCheckBox);
        
        chckbxNewCheckBox_1 = new JCheckBox("Plantuml");
        chckbxNewCheckBox_1.setBounds(16, 29, 128, 23);
        frame.getContentPane().add(chckbxNewCheckBox_1);
        
        //definding Open button
		btn1 = new JButton("Select java fiile");
		btn1.setBounds(121, 5, 117, 29);
		frame.getContentPane().add(btn1);
		
        //definding Save button
		btn2 = new JButton("Save to");
		btn2.setBounds(250, 5, 117, 29);
		frame.getContentPane().add(btn2);
		
        //definding Apply button
		btnApply = new JButton("Apply");
		btnApply.setBounds(379, 5, 117, 29);
		frame.getContentPane().add(btnApply);
		
        //definding Exit button
		btnExit = new JButton("Exit");
		btnExit.setBounds(520, 5, 117, 29);
		frame.getContentPane().add(btnExit);
        
        //add buttons to ActionListener
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btnExit.addActionListener(this);
        btnApply.addActionListener(this);
		
        //image
		JLabel label = new JLabel("");
		Image img = new ImageIcon("../rsz_java2uml.png").getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(50, 60, 600,350);
		frame.getContentPane().add(label);

        //menu bars and items
		JMenuBar menuBar = new JMenuBar();
   		JMenu menu = new JMenu("File");
        JMenu menu1 = new JMenu("Help");
        menuBar.add(menu);
        menuBar.add(menu1);
        
        menuItem1 = new JMenuItem("Exit");
		menuItem2 = new JMenuItem("About");
        menuItem3 = new JMenuItem("jar2Java");
        menuItem4 = new JMenuItem("Planuml");
        menuItem5 = new JMenuItem("yUML");

        menu.add(menuItem2);
   		menu.add(menuItem1);
        menu1.add(menuItem3);
        menu1.add(menuItem4);
        menu1.add(menuItem5);
        
        frame.setJMenuBar(menuBar);

        
    //Actions for menuItems
        
    //exit
	menuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.exit(JFrame.EXIT_ON_CLOSE);

            }

        });
        
        
    //about
	menuItem2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent eh) {
 		try {
	    		File file = new File("../about.txt");
	    		Desktop.getDesktop().browse(file.toURI());
	    	} catch (Exception e) {
	    		System.out.println("Something went horribly wrong: " + e);
	       		e.printStackTrace();
	    	}


        }

        });


        //jar2Java
        menuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eh) {
                try {
                    File file = new File("../jar2class.txt");
                    Desktop.getDesktop().browse(file.toURI());
                } catch (Exception e) {
                    System.out.println("Something went horribly wrong: " + e);
                    e.printStackTrace();
                }
                
                
            }
            
        });
        
        //plantuml
        menuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eh) {
                try {
                    File file = new File("../planuml.txt");
                    Desktop.getDesktop().browse(file.toURI());
                } catch (Exception e) {
                    System.out.println("Something went horribly wrong: " + e);
                    e.printStackTrace();
                }
                
                
            }
            
        });
        
        
        //yuml
        menuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eh) {
                try {
                    File file = new File("../yuml.txt");
                    Desktop.getDesktop().browse(file.toURI());
                } catch (Exception e) {
                    System.out.println("Something went horribly wrong: " + e);
                    e.printStackTrace();
                }
                
                
            }
            
        });

		
		
	}

    
    
    //Actions for buttons
	 public void actionPerformed(ActionEvent event){
        
        JButton buttonPressed = (JButton)event.getSource();
        
        
        //Open dialog
        if(buttonPressed == btn1){
            
            //open existing
            fc.setMultiSelectionEnabled(true);
            int returnVal = fc.showOpenDialog(StartUpGUI.this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION)
                files = fc.getSelectedFiles();
            
            if(returnVal == JFileChooser.CANCEL_OPTION);
                //log.append("command cancelled by user." + "\n");

            this.setVisible(false);
            //log.setCaretPosition(log.getDocument().getLength());
            //System.exit(JFrame.EXIT_ON_CLOSE);
            
        }//if
         
         
         
        //Save dialog
        else if (buttonPressed == btn2){
            
            int saveVal = sc.showSaveDialog(StartUpGUI.this);
            
            
            if(saveVal == JFileChooser.APPROVE_OPTION){
              
                fileSave = sc.getSelectedFile();
                System.out.println("SAVE" + fileSave + ".txt");
                //System.out.println("PATH" + fileSave.toString());
                //log.append("Saving: " + fileSave.getName() + "." + "\n");
                
            }//if
            
            if(saveVal == JFileChooser.CANCEL_OPTION);
                //log.append("Save command cancelled by user." + "\n");
                
             
            this.setVisible(false);
            //log.setCaretPosition(log.getDocument().getLength());
            //System.exit(JFrame.EXIT_ON_CLOSE);
            
            
        }//else if
        
        
        
        //Apply
        else if(buttonPressed == btnApply){
            try{
                
                if(chckbxNewCheckBox.isSelected())
                    format1 = "yuml";
                
                
                if(chckbxNewCheckBox_1.isSelected())
                    format2 = "plantuml";
                    
                Parser p = new Parser(files, fileSave, format1, format2);
                
            }catch(ParseException | IOException e){
                e.printStackTrace();
            }
            
            this.setVisible(false);
            System.exit(JFrame.EXIT_ON_CLOSE);
            
        }//else if

        
        //Exit
        else if(buttonPressed == btnExit)
            System.exit(JFrame.EXIT_ON_CLOSE);
         
        
    }//actionPerformed
}

