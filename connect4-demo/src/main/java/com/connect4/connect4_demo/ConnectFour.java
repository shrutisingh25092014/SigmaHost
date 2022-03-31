package com.connect4.connect4_demo;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This class encapsulate the Connect Four Game.  
 * 2 player play in turns and win if either straight,horizontal
 * or diagonal of 4 same colors on board.
 */

public class ConnectFour {

	//Two dimensional matrix to represent Board
	private  static char MATRIX[][];
	//Two players 'G' and 'Y' playing in alternate turns .
	private static final char [] PLAYER= {'G','Y'} ;
	//height ,width of board
	private final int width;
	private final int height;

	ConnectFour(int w,int h)
	{
		width=w;
		height=h;
		MATRIX=new char[height][width];
	}

	//Initialize Board with Blank ' '
	public void initializeMatrix()
	{
		for (char[] row: MATRIX)
			Arrays.fill(row, ' ');
	}

	//Display Board after each valid turn 
	public  void displayMatrix(){
		System.out.println(" 0 1 2 3 4 5 6");
		for (int row = 0; row < MATRIX.length; row++){
			System.out.print("|");
			for (int col = 0; col < MATRIX[0].length; col++){
				System.out.print(MATRIX[row][col]);
				System.out.print("|");
			}
			System.out.println();
		}
	}

	//Check if the turn is a valid turn else return error
	public static boolean checkValidTurn(int column,int width){

		if (column < 0 || column >width-1){
			System.out.println("Column must be between 0 and " + (width - 1));
			return false;
		}
		if (MATRIX[0][column] != ' '){
			System.out.println("Column " + column +" is full.");
			return false;
		}

		return true;
	}

	//to check winner after every turn
	public  boolean checkWinner(int row ,int col,char player,int height,int width)
	{

		if(vertical(col,player,height) || horizontal(row,player,width) 
				|| upDiagonal(player) || downDiagonal(player))
		{
			return true;
		}
		return false;		
	}

	//check vertical sequence of 4 for player  
	public boolean vertical(int col,char player,int height)
	{
		int count=0;
		for(int i=0;i<height;i++)
		{
			if(count<4)
			{
				if(MATRIX[i][col]==player)
				{
					count++;
				}
				else
					count=0;
			}
		}
		if(count>=4)
			return true;
		return false;	
	}

	//check horizontal sequence of 4 for player  
	public boolean horizontal(int row,char player,int width)
	{
		int count=0;
		for(int i=0;i<width;i++)
		{
			if(count<4)
			{
				if(MATRIX[row][i]==player)
				{
					count++;
				}
				else
					count=0;
			}
		}
		if(count>=4)
			return true;
		return false;
	}

	//check upward diagonal
	public boolean upDiagonal(char player)
	{
		for(int row = 3; row < MATRIX.length; row++){
			for(int col = 0; col < MATRIX[0].length - 3; col++){
				if (MATRIX[row][col] == player   && 
						MATRIX[row-1][col+1] == player &&
						MATRIX[row-2][col+2] == player &&
						MATRIX[row-3][col+3] == player){
					return true;
				}
			}
		}
		return false;
	}

	//check downward diagonal
	public boolean downDiagonal(char player)
	{
		for(int row = 0; row < MATRIX.length - 3; row++){
			for(int col = 0; col < MATRIX[0].length - 3; col++){
				if (MATRIX[row][col] == player   && 
						MATRIX[row+1][col+1] == player &&
						MATRIX[row+2][col+2] == player &&
						MATRIX[row+3][col+3] == player){
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {

		int width=7;
		int height=6;
		int rowNum=0;

		ConnectFour connect =new ConnectFour(width,height);
		Scanner in = new Scanner(System.in);

		//initialize board
		connect.initializeMatrix();
		//display board
		connect.displayMatrix();		

		int turn = 1;
		int player=0;
		boolean winner = false;		

		//play a turn
		int turns=width*height;
		while(winner==false)
		{
			for ( player = 0; turns-- > 0; player = 1 - player)		
			{
				boolean validPlay;
				int column;
				do {
					System.out.print("Player " + PLAYER[player] + ", choose a column: ");
					column = in.nextInt();

					validPlay = checkValidTurn(column,width);

				}while (validPlay == false);

				//Push the color to the top of column
				for (int row = MATRIX.length-1; row >= 0; row--){
					if(MATRIX[row][column] == ' '){
						MATRIX[row][column] = PLAYER[player];
						rowNum=row;
						break;
					}
				}
				connect.displayMatrix();

				//determine if there is a winner
				winner = connect.checkWinner(rowNum,column,PLAYER[player],height,width);
				if(winner)
					break;
			}
		}
		connect.displayMatrix();

		if (winner){ 
			System.out.println("\nPlayer " + PLAYER[player] + " wins!");

		}else{
			System.out.println("Game Over. No winner.");
		}

	}
}
