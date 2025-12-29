import java.awt.*; // basic GUI building blocks
import java.awt.event.*; // event handling (ActionListener, etc)
import javax.swing.*; // Swing = higher-level GUI library (built on AWT) - JFrame, JPanel, JButton, JLabel
import javax.swing.border.LineBorder; // imports one specific border class.

public class TicTacToeAI
{

    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton resetButton = new JButton("Reset");

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";

    boolean gameOver = false;
    int turns = 0;
    
    public TicTacToeAI()
    {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());
        
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        textPanel.add(resetButton, BorderLayout.SOUTH);
        frame.add(textPanel, BorderLayout.NORTH);

        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);
                tile.setOpaque(true);
                tile.setBorder(new LineBorder(Color.white, 3));

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) 
                        {
                            tile.setText(playerX); // human is O
                            turns++;
                            checkWinner();

                            if (!gameOver && turns < 9)
                            {
                                textLabel.setText("AI thinking...");

                                Timer timer = new Timer(700, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int[] move = findBestMove();
                                        board[move[0]][move[1]].setText(playerO);
                                        turns++;
                                        checkWinner();

                                        if (!gameOver) {
                                            textLabel.setText("O's turn.");
                                        }

                                        ((Timer) e.getSource()).stop();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }

                    }
                });
            }
        }
        
        frame.setVisible(true);
    }

    void checkWinner()
    {
        //horizontal
        for (int r = 0; r < 3; r++)
        {
            if (board[r][0].getText().equals("")) continue;
            if (board[r][0].getText().equals(board[r][1].getText()) && board[r][1].getText().equals(board[r][2].getText()))
            {
                for (int i = 0; i < 3; i++)
                {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }
        //vertical
        for (int c = 0; c < 3; c++)
        {
            if (board[0][c].getText().equals("")) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) && board[1][c].getText().equals(board[2][c].getText()))
            {
                for (int i = 0; i < 3; i++)
                {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }
        //diagonal
        if (board[0][0].getText().equals(board[1][1].getText()) && 
            board[1][1].getText().equals(board[2][2].getText()) && 
            !board[0][0].getText().equals(""))
        {
            for (int i = 0; i < 3; i++)
            {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //anti-diagonal
        if (board[0][2].getText().equals(board[1][1].getText()) && 
            board[1][1].getText().equals(board[2][0].getText()) && 
            !board[0][2].getText().equals(""))
            {
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);

                gameOver = true;
                return;
            }

            if (turns == 9)
            {
                for (int r = 0; r < 3; r++)
                {
                    for (int c = 0; c < 3; c++)
                    {
                        setTie(board[r][c]);
                    }
                }
                gameOver = true;
            }
    }
    
    void setWinner(JButton tile)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(tile.getText() + " is the Winner!");
    }


    void setTie(JButton tile)
    {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");

    }
    
    int evaluate(String b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (!b[row][0].equals("") &&
                b[row][0].equals(b[row][1]) &&
                b[row][1].equals(b[row][2]))
            {
                if (b[row][0].equals(playerX))
                    return -10;
                if (b[row][0].equals(playerO))
                    return +10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (!b[0][col].equals("") && 
                b[0][col].equals(b[1][col]) &&
                b[1][col].equals(b[2][col]))
            {
                if (b[0][col].equals(playerX))
                    return -10;
                if (b[0][col].equals(playerO))
                    return +10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (!b[0][0].equals("") && b[0][0].equals(b[1][1]) && b[1][1].equals(b[2][2]))
        {
            if (b[0][0].equals(playerX))
                return -10;
            if (b[0][0].equals(playerO))
                return +10;
        }

        if (!b[0][2].equals("") && b[0][2].equals(b[1][1]) && b[1][1].equals(b[2][0]))
        {
            if (b[0][2].equals(playerX))
                return -10;
            if (b[0][2].equals(playerO))
                return +10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    static boolean isMovesLeft(String board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j].equals(""))
                    return true;
        return false;
    }

    int minimax (String board[][], int depth, boolean isMax)
    {
        int score = evaluate(board);
        if (score == 10)
            return score - depth;
        if (score == -10)
            return score + depth;
        if (!isMovesLeft(board))
            return 0;

       if (isMax) 
        {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = playerO;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        } 
        else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals("")) {
                        board[i][j] = playerX;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = "";
                    }
                }
            }
            return best;
        }
    }

    int[] findBestMove()
    {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        String[][] tempBoard = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tempBoard[i][j] = board[i][j].getText();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tempBoard[i][j].equals("")) {
                    tempBoard[i][j] = playerO; // AI is O

                    int moveVal = minimax(tempBoard, 0, false);

                    tempBoard[i][j] = "";

                    if (moveVal > bestVal) {
                        bestVal = moveVal;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    void resetGame() 
    {
        gameOver = false;
        turns = 0;

        textLabel.setText("Tic-Tac-Toe");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
    }

}
