# Tic Tac Toe (Java Swing)

A **Tic Tac Toe** game built in **Java** using **Swing** for the GUI.  
The project includes both a **classic 2-player mode** and a **single-player AI mode** powered by the **Minimax algorithm**.

---

## Features

### Classic Mode
- 2-player game (X vs O)
- Turn indicator
- Win detection (rows, columns, diagonals)
- Tie detection
- Simple Swing-based GUI

### AI Mode
- Human (X) vs AI (O)
- AI uses the Minimax algorithm
- Depth-aware evaluation (faster wins, delayed losses)
- AI cannot be beaten (draw or loss at best)
- Reset button to replay without restarting the app

---

## Requirements

- Java JDK 8 or higher

Check your Java version:

```bash
java -version

# Clone the repository
git clone https://github.com/ShauryaLuthra/Tic-tac-toe-java.git
cd Tic-tac-toe-java

# Compile the code
javac App.java TicTacToe.java TicTacToeAI.java

# Run the game

# Using launcher (recommended)
java App

# Classic 2-player mode directly
java TicTacToe

# AI mode directly
java TicTacToeAI

.
├── App.java            # Entry point / launcher
├── TicTacToe.java      # 2-player version
├── TicTacToeAI.java    # AI version (Minimax)
├── .gitignore          # Ignores .class files
└── README.md
