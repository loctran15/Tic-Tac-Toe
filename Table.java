
/**
 * Write a description of Table here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Table {
    State[] array;
    int count;
    ZobristHashing zobristHashing;
    public Table(State state)
    {
        array = new State[100000000];
        count = 0;
        zobristHashing = new ZobristHashing(state);
    }
    
    public void add(State input)
    {
        input.hashValue = getHashValue(input);
        int arrayIndex = input.hashValue;
        count++;
        if(array[arrayIndex] == null || array[arrayIndex].ListOfUsedSquares.size() > input.ListOfUsedSquares.size())
        {
            State newState = new State();
            newState.copy(input);
            array[arrayIndex] = newState;
        }
    }
    
    public boolean contains(State input)
    {
        input.hashValue = getHashValue(input);
        if(array[input.hashValue] == null)
        {
            return false;
        }
        if(array[input.hashValue].equals(input))
        {
            return true;
        }
        return false;
    }
    
    public int getHashValue(State input)
    {
        return zobristHashing.getHashValue(input);
    }
    
    public State getState(State input)
    {
        if(contains(input))
        {
            return array[getHashValue(input)];
        }
        return null;
    }
    
    public int count()
    {
        int count = 0;
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                count ++;
            }
        }
        return count;
    }
}
