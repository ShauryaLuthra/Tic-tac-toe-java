import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {

        String[] options = {"2 Player", "Play vs AI"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose game mode",
                "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            new TicTacToe();
        } else if (choice == 1) {
            new TicTacToeAI();
        }
    }
}
