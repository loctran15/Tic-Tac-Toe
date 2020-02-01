import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random; 
import java.lang.Object;
import java.io.File;
import java.io.*;
import java.lang.Math;
/**
 * Write a description of TicTacToe here.
 * 
 * @author (Loc Tran - Chinh Tran) 
 * @version (a version number or a date)
 */
enum PlayerType
{
    human, random, AI, AITable, error;
}
public class TTT {
    //static int count;
    public static void main(String[] args) throws IOException
    {
        int nrRows = Integer.parseInt(args[0]);
        int nrCols = Integer.parseInt(args[1]);
        int nrToWin = Integer.parseInt(args[2]);
        PlayerType player1Type = convertStringToPlayerType(args[3]);
        PlayerType player2Type = convertStringToPlayerType(args[4]);
   
        int depth = Integer.parseInt(args[5]);
        
        Player player1 = new Player(player1Type, Type.X);
        Player player2 = new Player(player2Type, Type.O);
        

        Square[][] initialMatrix = initialize(nrRows, nrCols, nrToWin);
        
        State initialState = new State(initialMatrix);
        
        
        String fileName = "test";
        play(player1, player2, nrRows, nrCols, nrToWin, depth, initialState, fileName);
        
        
    }
    
    public static Square[][] initialize(int nrRows, int nrCols, int nrToWin)
    {
        Square[][] squares = new Square[nrRows][nrCols];
        return squares;
    }
    
    
    public static void play (Player player1, Player player2, int nRows, int nColumns, int nTowin, int depth, State initialState, String fileName) throws IOException
    {
        PrintWriter outputFile = new PrintWriter(fileName);
        State state = initialState;
        Player player = player1;
        while(!terminate(state, nTowin, player))
        {
            printState(state, outputFile);
            generate(state, player, swapPlayer(player1, player2, player), nTowin, depth, outputFile);
            player = swapPlayer(player1, player2, player);
        }
        printState(state, outputFile);
        
        double value = evaluate(state, nTowin, player1);
        
        if (value == 1000000)
        {
            outputFile.println("First player is the winner");
            System.out.println("First player is the winner");
        }
        
        else if (value == -1000000)
        {
            outputFile.println("Second player is the winner");
            System.out.println("Second player is the winner");
        }
        
        else
        {
            outputFile.println("------TIE--------"); 
            System.out.println("------TIE--------");
        }
        outputFile.close();
    }
    
    
    public static boolean terminate(State state, int nTowin, Player player)
    {
        if (evaluate(state, nTowin, player) == 1000000)
        {
            return true;
        }
        
        if (evaluate(state, nTowin, player) == -1000000)
        {
            return true;
        }
        
        if (state.ListOfUsedSquares.size() == state.numOfSquares)
        {
            return true;
        }
        return false;
    }
    
    public static double evaluate(State state, int nTowin, Player player)
    {
        int count = 1;
        int discreteCount = 1;
        double heuristic = 0.0;
        boolean continueCount = true;
        boolean isRestricted = false;
        
        double[] heuristicArray = new double[(int)max(state.squares.length, state.squares[0].length) + 1];
        
        int nullSpace = 0;
        int nullSpace2 = 0;
        
        Square[][] matrix = state.squares;
        for (Square i: state.ListOfUsedSquares)
        {
                isRestricted = false;
                discreteCount = 1;
                nullSpace = 0;
                nullSpace2 = 0;
                continueCount = true;
                count = 1;
                int xFixed = i.positionX;
                int yFixed = i.positionY;
                for(int y = yFixed; y < matrix[0].length; y++)
                {
                    if (matrix[xFixed][y] == null)
                    {
                        nullSpace ++;
                        continueCount = false;
                    }
                    else if(matrix[xFixed][y].type == i.type)
                    {
                        
                        if (y != yFixed)
                        {
                            if(!continueCount)
                            {
                                if(nullSpace + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }
                    
                }
                continueCount = true;
                for (int y = yFixed; y >= 0; y--)
                {
                    if (matrix[xFixed][y] == null)
                    {
                        nullSpace2 ++;
                        continueCount = false;
                    }
                    else if(matrix[xFixed][y].type == i.type)
                    {
                        if (y != yFixed)
                        {
                            if(!continueCount)
                            {
                                if(nullSpace2 +discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }                    
                }
                if(count == nTowin)
                {
                    if (i.type == player.type) { return 1000000;}
                    else {return -1000000;}
                }
                else
                {
                    if(nullSpace + discreteCount + nullSpace2 >= nTowin)
                    {
                        heuristicArray[discreteCount] ++;
                        if (isRestricted)
                        {
                            heuristicArray[discreteCount] -= 0.5;
                        }
                    }
                }
                continueCount = true;
                isRestricted = false;
                discreteCount = 1;
                nullSpace = 0;
                nullSpace2 = 0;
                count = 1;
                for(int x = xFixed; x < matrix.length; x++)
                {
                    if (matrix[x][yFixed] == null)
                    {
                        nullSpace ++;
                        continueCount = false;
                    }
                    else if(matrix[x][yFixed].type == i.type)
                    {
                        if (x != xFixed)
                        {
                            if(!continueCount)
                            {
                                if(nullSpace + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }
                }
                continueCount= true;
                for (int x = xFixed; x >= 0; x--)
                {
                    if (matrix[x][yFixed] == null)
                    {
                        nullSpace2 ++;
                        continueCount = false;
                    }
                    else if(matrix[x][yFixed].type == i.type)
                    {
                        if (x != xFixed)
                        {
                            if(!continueCount)
                            {
                                if(nullSpace2 + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }                    
                }
                if(count == nTowin)
                {
                    if (i.type == player.type) { return 1000000;}
                    else {return -1000000;}
                }
                else
                {
                    if(nullSpace + discreteCount + nullSpace2 >= nTowin)
                    {
                        heuristicArray[discreteCount] ++;
                        if(isRestricted)
                        {
                            heuristicArray[discreteCount] -= 0.1;
                        }
                            
                    }
                }
                isRestricted = false;
                continueCount = true;
                discreteCount = 1;
                nullSpace = 0;
                nullSpace2 = 0;
                count = 1;
                int x = xFixed;
                int y = yFixed;
                while(x < matrix.length && x >= 0 && y < matrix[0].length && y >= 0)
                {
                    if (matrix[x][y] == null)
                    {
                        nullSpace ++;
                        x++;
                        y++;
                        continueCount = false;
                    }
                    else if(matrix[x][y].type == i.type)
                    {
                        if (x != xFixed && y != yFixed)
                        {
                            if(!continueCount)
                            {
                                if(nullSpace + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        x++;
                        y++;
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }
                }
                continueCount = true;
                x = xFixed;
                y = yFixed;
                while(x < matrix.length && x >= 0 && y < matrix[0].length && y >= 0)
                {
                    if (matrix[x][y] == null)
                    {
                        nullSpace2 ++;
                        x--;
                        y--;
                        continueCount = false;
                    }
                    else if(matrix[x][y].type == i.type)
                    {
                        if (x != xFixed && y != yFixed) 
                        {
                            if(!continueCount)
                            {
                                if(nullSpace2 + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        x--;
                        y--;
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }                    
                }
                if(count == nTowin)
                {
                    if (i.type == player.type) { return 1000000;}
                    else {return -1000000;}
                }
                else
                {
                    if(nullSpace + discreteCount + nullSpace2 >= nTowin)
                    {
                        heuristicArray[discreteCount] ++;
                        if(isRestricted)
                        {
                            heuristicArray[discreteCount] -= 0.5;
                        }
                    }
                }
                isRestricted = false;
                discreteCount = 1;
                continueCount = true;
                nullSpace = 0;
                nullSpace2 = 0;
                count = 1;
                
                x = xFixed;
                y = yFixed;
                while(x < matrix.length && x >= 0 && y < matrix[0].length && y >= 0)
                {
                    if (matrix[x][y] == null)
                    {
                        nullSpace ++;
                        x++;
                        y--;
                        continueCount = false;
                    }
                    else if(matrix[x][y].type == i.type)
                    {
                        if (x != xFixed && y != yFixed) 
                        {
                            if(!continueCount)
                            {
                                if(nullSpace + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        x++;
                        y--;
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }
                }
                continueCount = true;
                x = xFixed;
                y = yFixed;
                while(x < matrix.length && x >= 0 && y < matrix[0].length && y >= 0)
                {
                    if (matrix[x][y] == null)
                    {
                        nullSpace2 ++;
                        x--;
                        y++;
                        continueCount = false;
                    }
                    else if(matrix[x][y].type == i.type)
                    {
                        if (x != xFixed && y != yFixed) 
                        {
                            if(!continueCount)
                            {
                                if(nullSpace2 + discreteCount <  nTowin)
                                {
                                    discreteCount++;
                                }
                            }
                            else
                            {
                                discreteCount ++;
                                count += 1;
                            }
                        }
                        x--;
                        y++;
                        continue;
                    }
                    else
                    {
                        if(continueCount)
                        {
                            isRestricted = true;
                        }
                        break;
                    }                    
                }
                if(count == nTowin)
                {
                    if (i.type == player.type) { return 1000000;}
                    else {return -1000000;}
                }
                else
                {
                    if(nullSpace + discreteCount + nullSpace2 >= nTowin)
                    {
                        heuristicArray[discreteCount] ++;
                        if(isRestricted)
                        {
                            heuristicArray[discreteCount] -= 0.5;
                        }
                    }
                }
                
                
                if(i.type == player.type)
                {
                    heuristic += calcHeuristic(heuristicArray, nTowin);
                }
                else
                {
                    heuristic -= calcHeuristic(heuristicArray, nTowin);
                }
                refreshArray(heuristicArray);
        }
        return heuristic;
    }
    
    public static double calcHeuristic(double[] heuristicArray, int nToWin)
    {
        double heuristic = 0;
        for(int i = 0; i <= nToWin; i++)
        {
            double a;
            heuristic += heuristicArray[i]*Math.pow(i,nToWin);
        }
        return heuristic;
    }
    
    public static void refreshArray(double[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            array[i] = 0.0;
        }
    }
    // in progress
    public static void generate(State state, Player mainPlayer, Player minimaxPlayer, int nTowin, int depth, PrintWriter outputFile) throws IOException
    {
        if (mainPlayer.playerType == PlayerType.human)
        {
           
            // input value (positionX, positionY)
            Scanner keyboard = new Scanner(System.in);
            int posX;
            int posY;
            do
            {
            System.out.println("enter an x: ");
            posX = keyboard.nextInt();
            System.out.println("enter an y: ");
            posY = keyboard.nextInt();
            } while(!isValidPosition(posX, posY, state));
            
            // check if the position is Valid or not
            // create Square with positionX, positionY)
            Square square = new Square(posX, posY, mainPlayer);
            // add them to the corresponding position of state.squares 
            state.squares[posX][posY] = square;
            state.ListOfUsedSquares.add(square);
            outputFile.println ("Human's turn:");
        }
        
        else if (mainPlayer.playerType == PlayerType.random) 
        {
            Random rand = new Random();
            int posX;
            int posY;
            do
            {
                posX = rand.nextInt(state.squares.length);
                posY = rand.nextInt(state.squares[0].length);
            }while(!isValidPosition(posX, posY, state));
            Square square = new Square(posX, posY, mainPlayer);
            state.squares[posX][posY] = square;
            state.ListOfUsedSquares.add(square);
            outputFile.println ("Random's turn:");
        }
        
        else if (mainPlayer.playerType == PlayerType.AI)
        {
            ArrayList<State> children = new ArrayList<State>();
            ArrayList<State> statesArray = new ArrayList<State>();
            double value = alphabeta(state, depth, depth, nTowin, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, mainPlayer, minimaxPlayer, mainPlayer, children, true);
            //System.out.println(value);
            for (State i : children)
            {
                if (i.value == value)
                {
                    State newState = new State();
                    newState.copy(i);
                    statesArray.add(newState);
                }
            }
            Random rand = new Random();
            int randPosition = rand.nextInt(statesArray.size());
            state.copy(statesArray.get(randPosition));
            outputFile.println("AI's turn: ");
            //System.out.println("---------" + count);
        }
        
        else if (mainPlayer.playerType == PlayerType.AITable)
        {
            Table table = new Table(state);
            ArrayList<State> children = new ArrayList<State>();
            ArrayList<State> statesArray = new ArrayList<State>();
            double value = alphabetaTable(state, depth, depth, nTowin, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, mainPlayer, minimaxPlayer, mainPlayer, children, table, true);
            //System.out.println(value);
            for (State i : children)
            {
                if (i.value == value)
                {
                    State newState = new State();
                    newState.copy(i);
                    statesArray.add(newState);
                }
            }
            Random rand = new Random();
            int randPosition = rand.nextInt(statesArray.size());
            state.copy(statesArray.get(randPosition));
            outputFile.println("AITable's turn: ");
            //System.out.println("count: " + table.count() + "   " + table.count);
            //System.out.println("---------" + count);
        }
        else
        {
            System.out.println("error in the generate function");
        }
        
       
    }
    
    
    public static PlayerType convertStringToPlayerType (String stringType)
    {
        if (stringType.equals("human"))
        {
            return PlayerType.human;
        }
        else if(stringType.equals("random"))
        {
            return PlayerType.random;
        }
        else if(stringType.equals("AI"))
        {
            return PlayerType.AI;
        }
        else if(stringType.equals("AITable"))
        {
            return PlayerType.AITable;
        }
        System.out.println("error at convertStringToPlayerType method");
        return PlayerType.error;
    }
    
    
    public static Player swapPlayer(Player player1, Player player2, Player currentPlayer)
    {
        if (currentPlayer == player1)
        {
            return player2;
        }
        else
        {
            return player1;
        }
    }
    
    public static boolean isValidPosition(int positionX, int positionY, State state)
    {
        Square[][] board = state.squares;
        if(positionX >= 0 && positionX < board.length && positionY >= 0 && positionY < board[0].length)
        {
            if (board[positionX][positionY] == null)
            {
                return true;
            }
        }
        return false;
    }
    
    public static void printState(State state, PrintWriter outputFile) throws IOException
    {
        Square[][] board = state.squares;
        
        System.out.print(" ");
        for (int i = 0; i < board.length; i++)
        {
            System.out.print(" "+i);
        }
        System.out.println();
        
        for (int i = 0; i < board.length; i++)
        {
            System.out.print(i);
            for (int j = 0; j < board[0].length; j++)
            {
                if(board[i][j] == null)
                {
                    outputFile.print("| ");
                    System.out.print("| ");
                }
                else if(board[i][j].type == Type.X)
                {
                    outputFile.print("|X");
                    System.out.print("|X");
                }
                else if (board[i][j].type == Type.O)
                {
                    outputFile.print("|O");
                    System.out.print("|O");
                }
            }
            outputFile.print("| \n");
            System.out.print("| \n");
        }
    }
    
    public static boolean nextChild(State parentState, int positionX, int positionY, Player player, boolean first)
    {
        for (int x = 0; x < parentState.squares.length ; x++)
        {
            for (int y = 0; y < parentState.squares.length; y++)
            {
                if(isValidPosition(x, y, parentState))
                {
                    if((x == positionX && y > positionY) || x > positionX)
                    {
                        Square square = new Square(x, y, player);
                        parentState.squares[x][y] = square;
                        parentState.newPositionX = x;
                        parentState.newPositionY = y;
                        parentState.ListOfUsedSquares.add(square);
                        return true;
                    }
                    else if(first)
                    {
                        Square square = new Square(x, y, player);
                        parentState.squares[x][y] = square;
                        parentState.newPositionX = x;
                        parentState.newPositionY = y;
                        parentState.ListOfUsedSquares.add(square);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static double alphabetaTable(State state, int depth, int numDepth, int nToWin, double alpha, double beta, Player maxPlayer, Player minPlayer, Player currentPlayer, ArrayList<State> children, Table table, boolean first)
    {
        if (depth == 0 || terminate(state, nToWin, currentPlayer))
        {
            return (depth + 1) * evaluate(state, nToWin, maxPlayer);
        }
        
        if (currentPlayer == maxPlayer)
        {
            int positionX = 0;
            int positionY = 0;
            double value = Double.NEGATIVE_INFINITY;
            double val;
            while (nextChild(state, positionX, positionY, currentPlayer, first))
            {
                positionX = state.newPositionX;
                positionY = state.newPositionY;
                first = false;
                if(table.contains(state))
                {
                    val = table.getState(state).value;
                    //System.out.println(" up " + table.getHashValue(state) + "    " + table.getState(state).hashValue);
                }
                else
                {
                    val = alphabetaTable(state, depth - 1, numDepth, nToWin, alpha, beta, maxPlayer, minPlayer, minPlayer, children, table, true);
                    state.value = val;
                    table.add(state);
                }
                
                value = max(value, val);
                alpha = max(alpha, value);
                if (depth == numDepth)
                {
                    State newState = new State();
                    newState.copy(state);
                    newState.value = val;
                    //System.out.println("---+" + state.value + "+---");
                    children.add(newState);
                }
                state.squares[positionX][positionY] = null;
                state.ListOfUsedSquares.remove(state.ListOfUsedSquares.size() - 1);
                if (alpha > beta)
                {
                    break;
                }
            }
            //count++;
            return value;
        }
        else
        {
            int positionX = 0;
            int positionY = 0;
            double value = Double.POSITIVE_INFINITY;
            double val;
            while (nextChild(state, positionX, positionY, currentPlayer, first))
            {
                positionX = state.newPositionX;
                positionY = state.newPositionY;
                first = false;
                if(table.contains(state))
                {
                    val = table.getState(state).value;
                    //System.out.println(" up " + table.getHashValue(state) + "    " + table.getState(state).hashValue);
                }
                else
                {
                    val = alphabetaTable(state, depth - 1, numDepth, nToWin, alpha, beta, maxPlayer, minPlayer, maxPlayer, children, table, true);
                    state.value = val;
                    table.add(state);
                }
                
                value = min(value, val);
                beta = min(beta, value);
                state.squares[positionX][positionY] = null;
                state.ListOfUsedSquares.remove(state.ListOfUsedSquares.size() - 1);
                if (alpha > beta)
                {
                    break;
                }
            }
            //count++;
            return value;
        }
    }
    
    public static double alphabeta(State state, int depth, int numDepth, int nToWin, double alpha, double beta, Player maxPlayer, Player minPlayer, Player currentPlayer, ArrayList<State> children, boolean first)
    {
        if (depth == 0 || terminate(state, nToWin, currentPlayer))
        {
            return (depth + 1) * evaluate(state, nToWin, maxPlayer);
        }
        
        if (currentPlayer == maxPlayer)
        {
            int positionX = 0;
            int positionY = 0;
            double value = Double.NEGATIVE_INFINITY;
            while (nextChild(state, positionX, positionY, currentPlayer, first))
            {
                positionX = state.newPositionX;
                positionY = state.newPositionY;
                first = false;
                double val = alphabeta(state, depth - 1, numDepth, nToWin, alpha, beta, maxPlayer, minPlayer, minPlayer, children, true);
                
                value = max(value, val);
                alpha = max(alpha, value);
                if (depth == numDepth)
                {
                    State newState = new State();
                    newState.copy(state);
                    newState.value = val;
                    //System.out.println("---+" + newState.value + "+---");
                    children.add(newState);
                }
                state.squares[positionX][positionY] = null;
                state.ListOfUsedSquares.remove(state.ListOfUsedSquares.size() - 1);
                if (alpha > beta)
                {
                    break;
                }
            }
            //count++;
            return value;
        }
        else
        {
            int positionX = 0;
            int positionY = 0;
            double value = Double.POSITIVE_INFINITY;
            while (nextChild(state, positionX, positionY, currentPlayer, first))
            {
                positionX = state.newPositionX;
                positionY = state.newPositionY;
                first = false;
                value = min(value, alphabeta(state, depth - 1, numDepth, nToWin, alpha, beta, maxPlayer, minPlayer, maxPlayer, children, true));
                state.squares[positionX][positionY] = null;
                state.ListOfUsedSquares.remove(state.ListOfUsedSquares.size() - 1);
                beta = min(beta, value);
                if (alpha > beta)
                {
                    break;
                }
            }
            //count++;
            return value;
        }
    }

    public static double max(double a, double b)
    {
        if (a >= b)
        {
            return a;
        }
        else
        {
            return b;
        }
    }
    
    public static double min(double a, double b)
    {
        if (a <= b)
        {
            return a;
        }
        else
        {
            return b;
        }
    }
    
    
}
