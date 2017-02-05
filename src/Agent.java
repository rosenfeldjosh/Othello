import java.util.HashMap;
import java.util.HashSet;

public class Agent {
	private int maxVal;


	public Agent()
	{
		maxVal=Integer.MIN_VALUE;
	}

	private HashSet<Move> create_possible_states(boolean turn,String[][] board)
	{
		HashSet<Move> states = new HashSet<Move>();
		
		String turn_color;
		if(turn)
		{
			turn_color = "W";
		}
		else
		{
			turn_color = "B";
		}
		
		for(int row = 0; row < board.length; row++)
		{
			for(int col = 0; col < board.length; col++)
			{
				if(board[row][col] == ".")
				{
					if(row-2 > 0) //Upper center check
					{
						if(board[row-1][col] != turn_color && board[row-1][col] != ".")
						{
							for(int i = row; i >= 0; i--)
							{
								if(board[i][col] == turn_color)
								{
									states.add(new Move(row,col,"UP"));
									break;
								}
							}
						}
					}
					if(row-2 > 0 && col + 2 < board.length) //Upper right check
					{
						if(board[row-1][col+1] != turn_color && board[row-1][col+1] != ".")
						{
							int j = col;
							for(int i = row; i >= 0; i--)
							{
								if(board[i][j] == turn_color)
								{
									states.add(new Move(row,col,"UPRIGHT"));
									break;
								}
								
								j++;
								if(j > board.length-1)
								{
									break;
								}
							}
						}
					}
						if(col+2 < board.length) // Right check
						{
							if(board[row][col+1] != turn_color && board[row][col+1] != ".")
							{
								for(int j = col; j < board.length; j++)
								{
									if(board[row][j] == turn_color)
									{
										states.add(new Move(row,col,"RIGHT"));
										break;
									}
								}
							}
						}
						if(row+2 <  board.length && col + 2 < board.length) //Bottom right check
						{
							if(board[row+1][col+1] != turn_color && board[row+1][col+1] != ".")
							{
								int j = col;
								for(int i = row; i < board.length; i++)
								{
									if(board[i][j] == turn_color)
									{
										states.add(new Move(row,col,"BOTRIGHT"));
										break;
									}
									
									j++;
									if(j > board.length-1)
									{
										break;
									}
								}
							}
					}
						if(row+2 < board.length) //Bottom center check
						{
							if(board[row+1][col] != turn_color && board[row+1][col] != ".")
							{
								for(int i = row; i < board.length; i++)
								{
									if(board[i][col] == turn_color)
									{
										states.add(new Move(row,col,"BOT"));
										break;
									}
								}
							}
						}
						if(row+2 <  board.length && col - 2 > 0 ) //Bottom left check
						{
							if(board[row+1][col-1] != turn_color && board[row+1][col-1] != ".")
							{
								int j = col;
								for(int i = row; i < board.length; i++)
								{
									if(board[i][j] == turn_color)
									{
										states.add(new Move(row,col,"BOTLEFT"));
										break;
									}
									
									j--;
									if(j < 0)
									{
										break;
									}
								}
							}
						}
							if(col-2 > 0) // Left check
							{
								if(board[row][col-1] != turn_color && board[row][col-1] != ".")
								{
									for(int j = col; j > 0; j--)
									{
										if(board[row][j] == turn_color)
										{
											states.add(new Move(row,col,"LEFT"));
											break;
										}
									}
								}
							}
							if(row-2 > 0 && col -2 > 0) //Upper left check
							{
								if(board[row-1][col-1] != turn_color && board[row-1][col-1] != ".")
								{
									int j = col;
									for(int i = row; i >= 0; i--)
									{
										if(board[i][j] == turn_color)
										{
											states.add(new Move(row,col,"UPLEFT"));
											break;
										}
										
										j--;
										if(j < 0)
										{
											break;
										}
									}
								}
								
								
					}
				}
			}
		}
					
		return states;
					}
	
	public String[][] show_possible_moves(String[][] board)
	{
		HashSet<Move> states = create_possible_states(false,board);
		for(Move p: states)
		{
			board[p.getRow()][p.getCollumn()] = "x";
		}
		return board;
	}
	
	
	public int score_diff(boolean color,String[][] board1,String[][] board2)
	{
		String turn_color;
		int counter = 0;
		if(color)
		{
			turn_color = "W";
		}
		else
		{
			turn_color = "B";
		}
		for(int i = 0; i < board1.length; i++)
		{
			for(int j = 0; j < board1.length; j++)
			{
				if(board1[i][j].equals(turn_color))
				{
					counter --;
				}
				if(board2[i][j].equals(turn_color))
				{
					counter ++;
				}
			}
		}
		return counter;
	}
	
	
	
	private String[][] maxMove(HashSet<Move> possibleMoves, boolean color, String[][] board) {
		String[][] best_board = null;
		int best_score = Integer.MIN_VALUE;
		String[][] current_board = new String[8][8];
		for(Move m: possibleMoves)
		{
			current_board = othello.make_move(m.getRow(), m.getCollumn(),board, possibleMoves);
			if(best_board == null)
			{
				best_board = current_board;
			}
			int score_diff = score_diff(color,best_board,current_board);
			if(score_diff> best_score)
			{
				best_board = current_board;
				best_score = score_diff;
			}
			
		}
//		othello.print_board(current_board);
//		for(Move m : possibleMoves)
//		{
//			System.out.printf("Move: %d,%d,%s\n",m.getRow(),m.getCollumn(),m.getDirection());
//		}
		return best_board;
	}
	
	private String[][] next_Node(String[][]board,boolean color) //takes a board state of a potential move and calculates the best possible move after the opponent goes
	{
		HashSet<Move> move_set = get_moveset(!color,board);
		String[][] best_opponent_move = maxMove(move_set,!color,board);
		move_set = get_moveset(color,best_opponent_move);	
		return maxMove(move_set,color,best_opponent_move);
	}

	public Move suggest_move(int depth, boolean color,String[][] board)
	{
		HashSet<Move> move_set = get_moveset(color,board);
		HashMap<Integer,Move> final_map = new HashMap<Integer,Move>();
		for(Move m : move_set)
		{
			maxVal = Integer.MIN_VALUE;
			final_map.put(analyze_move(othello.make_move(m.getRow(), m.getCollumn(), board, move_set),color,depth), m);
		}
		int highest = 0;
		Move best_move = new Move(0,0,"");
		for(int i : final_map.keySet())
		{
			if(i > highest)
			{
				best_move = final_map.get(i);
			}
		}
		return best_move; //return that value
	}

	private Integer count_color(String[][] board, boolean color)
	{
		String our_color = "";
		int count = 0;
		if(color)
		{
			our_color = "W";
		}
		else{
			our_color = "B";
		}
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if(board[i][j].equals(our_color))
				{
					count++;
				}
			}
		}
		return count;
	}
	private Integer analyze_move(String[][] board, boolean color,int depth) {
		if(depth<=0){
			int valueOfLeaf=count_color(maxMove(get_moveset(color,board),color,board),color);
			if(valueOfLeaf>maxVal){
				return valueOfLeaf;
			}
			return maxVal;
		}else{
			return analyze_move(next_Node(board, color), color, depth-1);
		}
	}

	public HashSet<Move> get_moveset(boolean turn,String[][]board)
	{
		return create_possible_states(turn,board);
	}
}
