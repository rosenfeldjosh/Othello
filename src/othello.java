import java.util.ArrayList;
import java.util.HashSet;

public class othello
{
	public static String[][] board = new String [8][8];
	public static boolean turn;
	
	public static void build_new_board(String[][] new_board)
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				new_board[i][j] = ".";
			}
		}
		new_board[new_board.length/2-1][new_board.length/2-1] = "B";
		new_board[new_board.length/2][new_board.length/2-1] = "W";
		new_board[new_board.length/2][new_board.length/2] = "B";
		new_board[new_board.length/2-1][new_board.length/2] = "W";
	}
	public static String[][] board_copy(String[][] board)
	{
		String[][] return_board = new String[8][8];
		for(int i = 0; i < board.length; i++)
		{
			for(int j =0; j < board.length; j++)
			{
				return_board[i][j] = board[i][j];
			}
		}
		return return_board;
	}
	public static void print_board(String[][] board)
	{
		System.out.println("   a  b  c  d  e  f  g  h");
		for(int i = 0; i < 8; i ++)
		{
			System.out.printf("%d ",i+1);
			for(int j = 0; j < 8; j++)
			{
				System.out.printf(" %s ", board[i][j]);
			}
			System.out.println("");
		}
	}
	public static String[][] make_move(int row, int col,String[][] board, HashSet<Move> moveset)
	{
		String[][] current_board = board_copy(board);
		Move comp = new Move(row,col,"");
		ArrayList<String> directions = new ArrayList<String>();
		String turn_color;
		if(turn)
		{
			turn_color = "W";
		}
		else
		{
			turn_color = "B";
		}
		for(Move i : moveset)
		{
			if(comp.equals(i))
			{
				directions.add(i.getDirection());
			}
		}
		if(directions.size() == 0)
		{
			System.out.println("Invalid Move");
		}
		else
		{
			current_board[row][col] = turn_color;
			turn = !turn;
			for(String direction : directions)
			{
				if(direction.equals("UP"))
				{
					for(int i = row-1; i >= 0; i--)
					{
						if(current_board[i][col] != turn_color)
						{
							current_board[i][col] = turn_color;
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("UPRIGHT"))
				{
					int j = col+1;
					for(int i = row -1; i >= 0; i--)
					{
						if(current_board[i][j] != turn_color)
						{
							current_board[i][j] = turn_color;
							if(j < current_board.length)
							{
								j++;
							}
							else
							{
								break;
							}
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("RIGHT"))
				{
					for(int j = col+1; j < current_board.length; j++)
					{
						if(current_board[row][j] != turn_color)
						{
							current_board[row][j] = turn_color;
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("BOTRIGHT"))
				{
					int j = col+1;
					for(int i = row +1; i < current_board.length; i++)
					{
						if(current_board[i][j] != turn_color)
						{
							current_board[i][j] = turn_color;
							if(j < current_board.length)
							{
								j++;
							}
							else
							{
								break;
							}
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("BOT"))
				{
					for(int i = row+1; i < current_board.length; i++)
					{
						if(current_board[i][col] != turn_color)
						{
							current_board[i][col] = turn_color;
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("BOTLEFT"))
				{
					int j = col-1;
					for(int i = row +1; i < current_board.length; i++)
					{
						if(current_board[i][j] != turn_color)
						{
							current_board[i][j] = turn_color;
							if(j > 0)
							{
								j--;
							}
							else
							{
								break;
							}
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("LEFT"))
				{
					for(int j = col-1; j >= 0; j--)
					{
						if(current_board[row][j] != turn_color)
						{
							current_board[row][j] = turn_color;
						}
						else
						{
							break;
						}
					}
				}
				if(direction.equals("UPLEFT"))
				{
					int j = col-1;
					for(int i = row - 1; i > 0 ; i--)
					{
						if(current_board[i][j] != turn_color)
						{
							current_board[i][j] = turn_color;
							if(j > 0)
							{
								j--;
							}
							else
							{
								break;
							}
						}
						else
						{
							break;
						}
					}
				}
				
			}
		}
		return current_board;
	}
	public static String interpret_col(int col)
	{
		String col_string = "";
		 switch (col) {
         case 1:  col_string = "a";
                  break;
         case 2:  col_string = "b";
                  break;
         case 3:  col_string = "c";
                  break;
         case 4:  col_string = "d";
                  break;
         case 5:  col_string = "e";
                  break;
         case 6:  col_string = "f";
                  break;
         case 7:  col_string = "g";
                  break;
         case 8:  col_string = "h";
         default: col_string = "x";
                  break;
     }
		 return col_string;
	}
	public static void main(String[] args)
	{
		build_new_board(board);
		turn = true;
		Agent agent = new Agent();
		String[][] board_c = board_copy(board);
		Move suggested = agent.suggest_move(22, turn, board_c);
		System.out.printf("Move: %d%s\n", suggested.getRow()+1,interpret_col(suggested.getCollumn()+1));
		print_board(board);
	}
}