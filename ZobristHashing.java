import java.lang.Math;
/**
 * Write a description of ZobristHashing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZobristHashing {
    public long[][][] bitStrings;
    private int nRow, nColumn, nValues;
    public ZobristHashing(State state)
    {
        nRow = state.squares.length;
        nColumn = state.squares[0].length;
        nValues = 2;
        bitStrings = new long[nRow][nColumn][nValues];
        for (int i = 0; i < nRow; i++)
        {
            for (int j = 0; j < nColumn; j++)
            {
                for (int k = 0; k < nValues; k++)
                {
                    bitStrings[i][j][k] = (long) (Math.random() * Long.MAX_VALUE) & 0xFFFFFFFF;
                }
            }
        }
    }
    
    public int getHashValue(State state)
    {
        long hashValue = 0;
        for (int i = 0; i < nRow; i++)
        {
            for (int j = 0; j < nColumn; j++)
            {
                if(state.squares[i][j] == null)
                {
                    continue;
                }
                else if(state.squares[i][j].type == Type.X)
                {
                    hashValue = hashValue ^ bitStrings[i][j][0];
                }
                else if(state.squares[i][j].type == Type.O)
                {
                    hashValue = hashValue ^ bitStrings[i][j][1];
                }

            }
        }
        return (int) (hashValue % 10000000);
    }
}
