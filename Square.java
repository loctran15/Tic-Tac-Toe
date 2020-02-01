
/**
 * Write a description of Square here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
enum Type
{
    X, O
}
public class Square {
    public int positionX;
    public int positionY;
    public Type type;
    public Square(int positionX, int positionY, Player player)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        if (player.type == Type.X)
        {
            type = Type.X;
        }
        else
        {
            type = type.O;
        }
    }
    
    public Square()
    {
        
    }
    
    public void copy(Square anotherSquare)
    {
        if (anotherSquare != null)
        {
            this.positionX = anotherSquare.positionX;
            this.positionY = anotherSquare.positionY;
            this.type = anotherSquare.type;
        }
    }
    
    public boolean equals(Square anotherSquare)
    {
        if(this.positionX == anotherSquare.positionX && this.positionY == anotherSquare.positionY && this.type == anotherSquare.type)
        {
            return true;
        }
        return false;
    }
}
