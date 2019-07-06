package elevatorPro;

import java.util.ArrayList;
//import java.util.Collection;

import javax.swing.JFrame;
import java.awt.Dimension;

//import java.util.HashMap;
//import java.util.Set;
public class Elevator{

    static Ele[] elevators;
   // public static HashMap <Integer, Integer> dict = new  HashMap <Integer, Integer> ();

    public static void main(String[] args){

     /*   dict.put(20, 60);
        dict.put(80, 90);
        dict.put(100, 120);

        if(!dict.isEmpty()){
                Ele.pprint(dict);

                Ele.pprint("dict begin");
                Integer[] arr = dict.keySet().toArray(new Integer[0]);
                Ele.pprint(arr);
                for(int i = 0; i <= arr.length - 1; ++i){
                    
                    int firstKey = arr[i];
                    Ele.pprint("dict nextitem: " + firstKey + " " + dict.get(firstKey));
                    dict.remove(firstKey);
                    Ele.pprint(dict);
                }

                Ele.pprint("dict end");

        }*/
        

        GUI frame = new GUI();
        Dimension d = new Dimension(1000, 800);

        elevators = new Ele[3];

        for(int i = 0; i < elevators.length; ++i){
             elevators[i] = new Ele("Elevator " + (i + 1), i);
             elevators[i].start();
        }

        frame.setTitle("Kiro and phyzia");
        frame.setSize(d);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
       
       
    }
    
    public static void check(int cur, int dest){
        Ele.pprint("Start Check");

        int dirU = dest - cur;
        ArrayList <Ele> elev = new ArrayList <Ele>();
        int min = 0 ;
        int index = 0;
        
             for(int i = 0; i < elevators.length; ++i){
              
          System.out.print(elevators[i].getDestination());
                   
                 if (dirU > 0 && elevators[i].up &&   
                          elevators[i].getFloor() < cur ){
                          if (!(elevators[i].getDestination() < dest && !elevators[i].UserStateUp))
                           elev.add(elevators[i]);

                    }   
                 else  if (dirU < 0 && elevators[i].down &&
                            elevators[i].getFloor() > cur ){
                       
                     if (!(elevators[i].getDestination() > dest && elevators[i].UserStateUp))
                                     elev.add(elevators[i]); 

                    } 
                  
                else if(elevators[i].stopped)      
                            elev.add(elevators[i]); 
                     
                      /*else
                         elev.add(elevators[i]); */ 
             }
             

       if(!elev.isEmpty())
       {
           min = elev.get(0).getDefference(cur);
            for(int i = 0; i < elev.size(); ++i) 

                 if (elev.get(i).getDefference(cur) < min){

                    min = elev.get(i).getDefference(cur); 
                    index = i;
                }
            elev.get(index).addItemToList(cur, dest);
       }   
    
       //else
           //display a message for the user to wait until there is an elevator available

           }

      
}
