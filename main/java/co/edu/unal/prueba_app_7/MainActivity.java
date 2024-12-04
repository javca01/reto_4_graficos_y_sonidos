package co.edu.unal.prueba_app_7;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TicTacToeGame mGame;
    private BoardView mBoardView;
    private TextView mStatusTextView;
    private boolean mGameOver;
    private boolean mHumanFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame = new TicTacToeGame();

        mBoardView = findViewById(R.id.board);
        mBoardView.setGame(mGame);

        /*mStatusTextView = findViewById(R.id.status);

        Button newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(view -> startNewGame());

        startNewGame();*/
        // Set the listener for clicks on the board
        mBoardView.setOnCellClickListener((row, col) -> onCellClicked(row, col));

        mStatusTextView = findViewById(R.id.status);

        Button newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(view -> startNewGame());

        startNewGame();
    }
    /*
        private void startNewGame() {
            mGame.clearBoard();
            mBoardView.invalidate(); // Redraw the board
        }*/
    private void startNewGame() {
        mGame.clearBoard();
        mBoardView.invalidate();
        mGameOver = false;

        if (mHumanFirst) {
            mStatusTextView.setText(R.string.first_human);
        } else {
            mStatusTextView.setText(R.string.turn_computer);
            int move = mGame.getComputerMove();
            mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        }

        mHumanFirst = !mHumanFirst;
        mBoardView.invalidate();
    }

    public void onCellClicked(int row, int col) {
        if (mGameOver) return;

        int location = row * 3 + col;
        if (mGame.getBoardOccupant(location) == TicTacToeGame.EMPTY) {
            mGame.setMove(TicTacToeGame.HUMAN_PLAYER, location);
            mBoardView.invalidate();

            if (checkGameOver()) return;

            int computerMove = mGame.getComputerMove();
            mGame.setMove(TicTacToeGame.COMPUTER_PLAYER, computerMove);
            mBoardView.invalidate();

            checkGameOver();
        }
    }

    private boolean checkGameOver() {
        int winner = mGame.checkForWinner();
        if (winner == 0) {
            mStatusTextView.setText(mGame.getCurrentPlayer() == TicTacToeGame.HUMAN_PLAYER ?
                    R.string.turn_human : R.string.turn_computer);
            return false;
        }

        mGameOver = true;

        switch (winner) {
            case 1:
                mStatusTextView.setText(R.string.tie);
                break;
            case 2:
                mStatusTextView.setText(R.string.you_win);
                break;
            case 3:
                mStatusTextView.setText(R.string.android_wins);
                break;
        }
        return true;
    }


}
