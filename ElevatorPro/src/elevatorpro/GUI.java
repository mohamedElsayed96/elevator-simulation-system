package elevatorPro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame{

    static final Color moveColor = new Color(0, 0, 255);
    static final Color stopColor = new Color(255, 0, 0);
    static final Color openColor = new Color(0, 255, 0);
    static final Color normalColor = new Color(125, 125, 125);
    
    static JButton[][] jbtnEle = new JButton[20][3];

    JTextField[] jtxt = new JTextField[20];
    
       public GUI(){

        setLayout(new GridLayout(20, 4, 5, 5));
        
        JPanel[] jpnl = new JPanel[20];
        Button[] jbtn = new Button[20];
        

        for(int i = 20; i > 0; --i){

            jpnl[i - 1] = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            jbtn[i - 1] = new Button("floor " + i, i);
            jtxt[i - 1] = new JTextField(2);

            jpnl[i - 1].add(jbtn[i - 1]);
            jpnl[i - 1].add(jtxt[i - 1]);

            add(jpnl[i - 1]);

            for(int j = 0; j < 3; ++j){

                jbtnEle[i - 1][j] = new JButton("Elev " + j +" floor " + i);
                jbtnEle[i - 1][j].setBackground(normalColor);
                add(jbtnEle[i - 1][j]);

            }

            jbtn[i - 1].addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {

                Ele.pprint("Start action");
                int curr = ((Button) e.getSource()).getMyFloor();

                String text= jtxt[curr - 1].getText();

                if(!text.equals("")){

                    int dest = Integer.valueOf(text);

                    if(dest > 20 || dest < 1)
                        dest = (dest % 20) + 1;

                    if (curr != dest)
                        Elevator.check(curr, dest);
            
                        Ele.pprint("end action");
                }
            }
        });
            
        }          
    }


    class Button extends JButton{

        private int myfloor;

        public Button(String name, int myfloor){
            super(name);
            this.myfloor = myfloor;
        }

        int getMyFloor(){return myfloor;}

    }


}
