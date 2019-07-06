package elevatorPro;
//import java.util.HashMap;

import java.util.*;

public class Ele extends Thread{

    private int currentFloor = 1;
    private int destination = 0;
   // private int currentUserDestination = 0; 
  //  private int currentUserLocation = 0;
    private int myNumber;
   // public HashMap <Integer, Integer> dict = new  HashMap <Integer, Integer> ();
    ArrayList <Integer> users = new ArrayList <Integer>();
    ArrayList <Integer> destinations = new ArrayList <Integer>();

    String name;
  
    boolean up, down, stopped = true; 
    boolean UserStateUp = false;

    Ele (String name, int myNumber)
    {
         super(name);
         this.name = name;
         this.myNumber = myNumber;
         (GUI.jbtnEle[0][myNumber]).setBackground(GUI.stopColor);
    }

    @Override
    public void run(){

        Ele.pprint("Thread started" + getName());
        while(true){
             
              if(users.isEmpty() && destinations.isEmpty()){
                      stopped = true;
                      up = false; 
                      down = false; 
              }
              else{
                      stopped = false; 
              }
              try{
                 Thread.sleep(10);
              } 
              catch(Exception e){     

                e.printStackTrace();
            }     


           if(!users.isEmpty() || !destinations.isEmpty()){


               // Integer[] arr = dict.keySet().toArray(new Integer[0]);
             

                 setDestination();

             /*   for(int i = 0; i < users.size(); ++i){
                    
                   //int firstKey = arr[i];
                   setDestination(users.get(i),locations.get(i));
                   users.remove(i);
                   locations.remove(i);

                   // dict.remove(firstKey);
                    Ele.pprint("Thread nextitem" + getName());
                }*/

           }
        }

    }

    private void chooseDirecion(int dest1, int dest2){
                  
        int difference = dest2 - dest1;

         if(difference < 0){

             //down
             down = true;
             up = false;
             GoDown(dest1, dest2);   

         }else if (difference > 0){

            //up 
             up = true;
             down = false; 
             GoUp(dest1, dest2);
        }
         
    }
     public void setDestination(/*int userLoc, int userDes*/){
        if(!users.isEmpty()){
      //   goingToUser = true;   
         int index =0;
       
         if(up) //chhose what user to pick 
                index = chooseUserUp();
         if(down)
                index = chooseUserDown();
       
          int userLoc = users.get(index);
        
         chooseDirecion(currentFloor, userLoc);
      
       if (up)   //check if there is change on the user added when going up
             if(checkChangeUp(userLoc))
                    return;
     
       if (down) //check if there is change on the user added when going down
             if(checkChangeDown(userLoc))
                    return;
     
         users.remove(index);
     //    goingToUser = false;   
      
        System.out.println("I have arrived userLocation " + userLoc);
      

        try{

                (GUI.jbtnEle[userLoc - 1][myNumber]).setBackground(GUI.moveColor);
                Thread.sleep(500);
                (GUI.jbtnEle[userLoc - 1][myNumber]).setBackground(GUI.openColor);
                Thread.sleep(3000);    
                (GUI.jbtnEle[userLoc - 1][myNumber]).setBackground(GUI.moveColor);

         }
         catch(Exception e){     

                 e.printStackTrace();
           }  
        
        }
         if (users.isEmpty() && !destinations.isEmpty() ){
           int i = 0; 
         
           if(up) //choose what destination to go
                i = chooseDestinationUp();
           if(down)
                i = chooseDestinationDown();
          
           int  userDes = destinations.get(i);
           
           chooseDirecion(currentFloor, userDes);
           
        if (up)   //check if there is a user below (userDes) if thers is ,get out of the func and startover
             if(checkChangeUp(userDes))
                    return;
     
       if (down) //check if there is a user above (userDes) if thers is ,get out of the func and startover
             if(checkChangeDown(userDes))
                    return;
           
           destinations.remove(i);
         
           System.out.println("I have arrived userdestination " + userDes);

        try{
            (GUI.jbtnEle[userDes - 1][myNumber]).setBackground(GUI.moveColor);
            Thread.sleep(500);
            (GUI.jbtnEle[userDes - 1][myNumber]).setBackground(GUI.openColor);
            Thread.sleep(3000);    
            (GUI.jbtnEle[userDes - 1][myNumber]).setBackground(GUI.stopColor);

         } catch(Exception e){     

                e.printStackTrace();
           }     

         }
    }    

    
    private void GoUp(int des1, int des2){
            destination = des2;
            stopped = false;
        for (int i = des1; i <= des2; i++){
             
            System.out.println(name + ": " + currentFloor);
           
            if(currentFloor == des2 )
                    break; 
            if(checkChangeUp(des2)) //check if there is a user below (userDes) if thers is ,get out of the func and startover
                    break;

            check(currentFloor); //check if this floor equal to one of the destinations of the user 

          try{
            
                (GUI.jbtnEle[currentFloor - 1][myNumber]).setBackground(GUI.moveColor);
                Thread.sleep(1000);
                (GUI.jbtnEle[currentFloor - 1][myNumber]).setBackground(GUI.normalColor);    

           }catch(Exception e){
            
                e.printStackTrace();
            }

            currentFloor++;        
        }
     
    }
    private void GoDown(int des1, int des2){
        destination = des2;
        stopped = false;
        for (int i = des1; i >= des2; i--){

        System.out.println(name + ": " + currentFloor);
 
        if (currentFloor == des2)
                 break;
        if(checkChangeDown(des2)) //check if there is a user below (userDes) if thers is ,get out of the func and startover
                    break;

            check(currentFloor); //check if this floor equal to one of the destinations of the user 
   
            try{

            (GUI.jbtnEle[currentFloor - 1][myNumber]).setBackground(GUI.moveColor);
            Thread.sleep(1000);
            (GUI.jbtnEle[currentFloor - 1][myNumber]).setBackground(GUI.normalColor);    

           }catch(Exception e){
            
                e.printStackTrace();
            }

            currentFloor--; 
        }
    }

    public int  getFloor(){ return currentFloor; }

    public int  getDestination(){ return destination; }

    public int  getDefference(int usrCurr){ return Math.abs(usrCurr - currentFloor); }

    public void addItemToList (int key, int item){        
    users.add(key);
    destinations.add(item);
    UserStateUp = destinations.get(0) - users.get(0) > 0; 
    }

    private int chooseDestinationUp() //choose what destination to go when going up
    {  
        int index = 0 ;
       
        int min = destinations.get(0);
      for(int i = 0; i < destinations.size(); ++i)    
         if (destinations.get(i) < min){
           min = destinations.get(i);
           index = i;
         }
        return index ;
    }
  
    private int chooseDestinationDown() //choose what destination to go when going Down
    {  
        int index = 0 ;
       
        int max = 0;
        for(int i = 0; i < destinations.size(); ++i)    
          if (destinations.get(i) > max){
            max = destinations.get(i);
            index = i;
         }
        return index ;
    }
  
    private int chooseUserUp() //choose what User to pick up when going up
    {         
        int index = 0 ;        
        int min = users.get(0);
      for(int i = 0; i < users.size(); ++i)    
         if (users.get(i) < min){
           min = users.get(i);
           index = i;
         }
    
           return index ;
    } 
   
    private int chooseUserDown() //choose what User to pick up when going down
    {         
        int index = 0 ;        
        int max = 0;
      for(int i = 0; i < users.size(); ++i)    
         if (users.get(i) > max){
           max = users.get(i);
           index = i;
         }
    
           return index ;
   }
 
    private void check(int f){ // when picking up users this check if there is a floor when going up or down equal to one of the destinations of the users
    
        for(int i = 0; i < destinations.size(); ++i)    
         if (destinations.get(i) == f && users.size() !=  destinations.size())
         {
             destinations.remove(i);   
             try{ // stop to let the user out
            (GUI.jbtnEle[f - 1][myNumber]).setBackground(GUI.moveColor); 
            Thread.sleep(500);
            (GUI.jbtnEle[f - 1][myNumber]).setBackground(GUI.openColor);
            Thread.sleep(3000);    
            (GUI.jbtnEle[f - 1][myNumber]).setBackground(GUI.moveColor);
         } 
             catch(Exception e){     

                e.printStackTrace();
           }     
         }
    }
     
    private boolean checkChangeUp(int d){ // this check if there is a user is below the destinatiom of the elevator or not 
        if(!users.isEmpty()){
        int min = users.get(0);
      
         for(int i = 0; i < users.size(); ++i)    
       
            if (users.get(i) < min)
            min = users.get(i);
      
              return min < d ;
        }
        else 
            return false;
    }
 
    private boolean checkChangeDown(int d){ // this check if there is a user is above the destinatiom or not 
       if(!users.isEmpty()){ 
        int max = 0;
     
        for(int i = 0; i < users.size(); ++i)    
         if (users.get(i) > max)
            max = users.get(i);
      
              return max > d;
    }else 
        
           return false;
    
  }
    public static void pprint(Object obj){ System.out.println(obj); } 

    //public String getName(){return name;}

}
