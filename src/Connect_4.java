import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Connect_4 {
    
    static int width;//to store width of the board
    static int height;//to store height of the board
    static int pieces;//to store the number of pieces to connect
    static int moves;//to store move made by the player
    static int[][] gameBoard;// static 2D array to store all the moves made by players
    

    public static void main(String args[])throws IOException{
    	
        BufferedReader sc=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n\nEnter the width");   
        width=Integer.parseInt(sc.readLine());// take in the width from the user
        if(width>0)// make sure width is non-zero integer
        {
        	 System.out.println("\n\nWidth "+width);
        }
        else {
        	while(!(width>0))
            {
            	 System.out.println("Please choose a non-zero integer for number as width ");
            	 width=Integer.parseInt(sc.readLine());
            	 if(width>0)
            		 break;
            }
        }
        System.out.println("\n\nEnter the height");
        height=Integer.parseInt(sc.readLine());//take in the height from the user
        if(height>0 && height>=width)// make sure height is non-zero integer
        {
        	 System.out.println("\n\nheight "+height);
        }
        else {
        	while(!(height>0))
            {
            	 System.out.println("Please choose a non-zero integer greater than width for number as height ");
            	 height=Integer.parseInt(sc.readLine());
            	 if(height>0 && height>=width)
            		 break;
            }
        	while(!(height>width))
            {
            	 System.out.println("Please choose a non-zero integer greater than width for number as height ");
            	 height=Integer.parseInt(sc.readLine());
            	 if(height>0 && height>=width)
            		 break;
            }
        }
        System.out.println("\n\nEnter the pieces");
        pieces=Integer.parseInt(sc.readLine());//take in the height from the user
        if(pieces>0)// make sure connection is non-zero integer
        {
        	 System.out.println("\n\n Pieces selected for connection "+pieces);
        }
        else {
        	while(!(pieces>0))
            {
            	 System.out.println("Please choose a non-zero integer for number of pieces to connect");
            	 pieces=Integer.parseInt(sc.readLine());
            	 if(pieces>0)
            		 break;
            }
        }
        char a,b;// to store color selected by players
        gameBoard=new int[height][width];// Initialize the 2D array with required dimensions  
        moves=0;
    	Connect_4 connectFour=new Connect_4();
        System.out.println("Player 1 wants r/y");
        a=sc.readLine().charAt(0);
        if(a=='r' || a=='y') {
        	  System.out.println("Player 1 is " +a);
        }
        else {
        while(a!='r' || a!='y')
        {
        	 System.out.println("Please choose between r or y");
        	 a=sc.readLine().charAt(0);
        	 if(a=='r'||a=='y')
        		 break;
        }
        }
        if(a=='r')b='y';//if player 1 is red then player 2 is yellow
        else b='r';
        System.out.println("Player 2 is " +b);
        System.out.println();
        connectFour.print();
        loop:
        while(true){
            int column=0;
            //For making moves of player 1.
            while(true){
            System.out.println("\n\nPlayer 1,what column do you want to put your piece?:");
            column=Integer.parseInt(sc.readLine());//User input for column
                if(connectFour.isLegal(column)){
                    if(connectFour.currentMove(column, a)){
                        connectFour.print();
                        System.out.println("\n\nPlayer 1 wins");
                        System.out.println("\n\nDo you want to play again? 0 -yes, 1- no");
                        int q =Integer.parseInt(sc.readLine());
                        if(q==0) main(args);
                        else
                        break loop;
                    }
                    break;
                }
                else
                    System.out.println("Column "+column+" is already full!!");
            }
            connectFour.print();
            
            //For making moves of player 2    
            while(true){
            System.out.println("\n\nPlayer 2,what column do you want to put your piece?");
            column=Integer.parseInt(sc.readLine());//User input for column
                if(connectFour.isLegal(column)){
                    if(connectFour.currentMove(column, b)){
                        connectFour.print();
                        System.out.println("\n\nPlayer 2 wins!!!");
                        System.out.println("\n\nDo you want to play again? 0 -yes, 1- no");
                        int q =Integer.parseInt(sc.readLine());
                        if(q==0) main(args);
                        else
                        break loop;
                    }
                    break;
                }
                else
                    System.out.println("Column "+column+" is full");
            }
            connectFour.print();
            
            if(connectFour.max()){
                System.out.print("Match draw ");
                break;
            }
        }
    }
      // print the game board          
    public void print(){
        for(int i=0;i<gameBoard.length;i++){
            for(int j=0;j<gameBoard[0].length;j++){
                if(gameBoard[i][j] == 0)
                    System.out.print("|  ");
                else
                    System.out.print((char)gameBoard[i][j]+"  ");
               
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
    // to store the current move of the player in 2D array
    public boolean currentMove(int column, char num){
        int i=0;
        for(i=0;i<height;i++){
            if(gameBoard[i][column] == 'r' || gameBoard[i][column] == 'y'){
                gameBoard[i-1][column]=num;
                break;
            }
        }
        if(i == height)
            gameBoard[i-1][column]=num;
        
        moves++;
        return isWinning(i-1,column);
    }
    // to make sure the move played by the player is possible
    public boolean isLegal(int column){
        return gameBoard[0][column] == 0; 
    }
 //to identify the maximum possible moves
    public boolean max(){
        return moves == height*width;
    }
    // to find out if 4 connected patterns are present
    public boolean isWinning(int x, int y){
        int result=gameBoard[x][y];
        int count=0;//Initialize the count to 0 to count the connected patterns of same color to determine who is winning
        int track=y;
        
        //if player is winning in horizontal pattern.
        while(track<width && gameBoard[x][track] == result){
            count++;
            track++;
        }
        track=y-1;
        while(track>=0 && gameBoard[x][track] == result){
            count++;
            track--;
        }
        if(count == pieces)// if the count is equal to connections and all result holding the same color return true
            return true;
        
        //if player is winning in vertical pattern..
        count=0;
        int j=x;
        while(j<height && gameBoard[j][y] == result){
            count++;
            j++;
        }
        if(count == pieces)// if the count is equal to connections and all result holding the same color return true
            return true;
        
        //if player is winning in diagonal pattern..
        count=0;
        track=x;
        j=y;
        while(track<width && j<height && gameBoard[track][j] == result){
            count++;
            track++;
            j++;
        }
        track=x-1;
        j=y-1;
        while(track>=0 && j>=0 && gameBoard[track][j] == result){
            count++;
            track--;
            j--;
        }
        if(count == pieces)// if the count is equal to connections and all result holding the same color return true
            return true;
        
        //if player is winning in leading diagonal pattern.
        count=0;
        track=x;
        j=y;
        while(track<width && j>=0 && gameBoard[track][j] == result){
            count++;
            track++;
            j--;
        }
        track=x-1;
        j=y+1;
        while(track>=0 && j<height && gameBoard[track][j] == result){
            count++;
            track--;
            j++;
        }
        if(count == pieces)
            return true;// if the count is equal to connections and all result holding the same color return true
        
        return false;
    }
}