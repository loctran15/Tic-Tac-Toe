PROJECT TITLE: TIC TAC TOE
VERSION or DATE: 04/29/19
AUTHORS: Loc Tran - Chinh Tran
USER INSTRUCTIONS:

Step 1: compile all of the source codes by using the following command lines:
		1. javac TTT.java
		2. javac Player.java 
		3. javac Square.java
		4. javac State.java
		5. javac Table.java 
		6. javac ZobristHashing.java

Step 2: run the program by using the following format:
		java TTT nrRows nrCols nrToWin player1Type player2Type depth

	where: 	nrRows: the number of rows of the board
		nrCols: the number of columns of the board
		nrToWin: the number of consecutive elements to win
		Player1Type, player2Type can be random, human, AI, AITable
		depth: the number of moves AI can think ahead

	below are some examples:
		java TTT 5 5 3 random AI 5
		java TTT 10 10 5 AI human 2

Note: 
1. if player type is human, enter a row coordinate first and then a column coordinate to make a move.
2. the result of your game will be recorded in "test.txt" file.

References:
1. alpha_beta pruning pseudocode: https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning#Pseudocode
2. transposition tables guide: http://mediocrechess.blogspot.com/2007/01/guide-transposition-tables.html
3. Zobrist hasing: https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-5-zobrist-hashing/ 