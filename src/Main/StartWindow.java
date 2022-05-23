package Main;

import java.awt.event.*;
import javax.swing.*;
public class StartWindow extends JPanel implements ActionListener {
    // JTextField
    static JTextField t;

    // JFrame


    // JButton
    static JButton b;

    // label to display text
    static JLabel l;

    // default constructor
    JPanel p = new JPanel();

    // main class
   public StartWindow(){
         // create a label to display text
         l = new JLabel("nothing entered");

         // create a new button
         b = new JButton("submit");

         // create a object of the text class
         StartWindow te = new StartWindow();

         // addActionListener to button
         b.addActionListener(te);

         // create a object of JTextField with 16 columns
         t = new JTextField(16);

         // create a panel to add buttons and textfield


         // add buttons and textfield to panel
         p.add(t);
         p.add(b);
         p.add(l);

       p.setVisible(true);
     }

    public static void main(String[] args)
    {
        // create a new frame to store text field and button
       JPanel f = new JFrame("textfield");

        f.add(p);

        // set the size of frame
        f.setSize(300, 300);

        f.show();
    }
    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("submit")) {
            // set the text of the label to the text of the field
            l.setText(t.getText());

            // set the text of field to blank
            t.setText(" ");
        }
    }
}
