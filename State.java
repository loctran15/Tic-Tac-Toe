import java.util.ArrayList;
/**
 * Write a description of SquareMatrix here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class State {
    public int numOfSquares;
    public int hashValue;
    public Square[][] squares;
    public double value;
    public ArrayList<Square> ListOfUsedSquares = new ArrayList<Square>();
    int newPositionX, newPositionY;
    public State(double value, Square[][] squares)
    {
        this.value = value;
        this.squares = squares;
        numOfSquares = squares[0].length * squares.length;
    }
    
    public State()
    {
    }
    
    public void copy(State anotherState)
    {
       hashValue = anotherState.hashValue;
       numOfSquares = anotherState.numOfSquares;
       this.value = anotherState.value;
       squares = new Square[anotherState.squares.length][anotherState.squares[0].length];
       this.copyBoardFrom(anotherState);
       ListOfUsedSquares.clear();
       for (int i = 0; i < squares.length; i++)
       {
           for (int j = 0; j < squares.length; j++)
           {
               if (squares[i][j] != null)
               {
                   ListOfUsedSquares.add(squares[i][j]);
                }
           }
        }
    }
    
    public State(Square[][] squares)
    {
        this.squares = squares;
        numOfSquares = squares[0].length * squares.length;
    }
    
    public void copyValueFrom (State anotherState)
    {
        this.value = anotherState.value;
    }
    
    public void copyBoardFrom (State anotherState)
    {
        for (int i = 0; i < this.squares.length; i++)
        {
            for (int j = 0; j < this.squares[0].length; j++)
            {
                if(anotherState.squares[i][j] != null)
                {
                    Square square = new Square();
                    square.copy(anotherState.squares[i][j]);
                    this.squares[i][j] = square;
                }
                else
                {
                    this.squares[i][j] = null;
                }
            }
        }
    }
    
    public boolean equals(State anotherState)
    {
        for (int i = 0; i < this.squares.length; i++)
        {
            for (int j = 0; j < this.squares[0].length; j++)
            {
                if(this.squares[i][j] == null && anotherState.squares[i][j] != null || this.squares[i][j] != null && anotherState.squares[i][j] == null)
                {
                    return false;
                }
                if(this.squares[i][j] == null && anotherState.squares[i][j] == null)
                {
                    continue;
                }
                if(!this.squares[i][j].equals(anotherState.squares[i][j]))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
