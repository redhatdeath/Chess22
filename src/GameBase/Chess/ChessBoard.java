package GameBase.Chess;

import GameBase.Base.Board;
import GameBase.Base.Coordinate;
import GameBase.Base.Figure;
import GameBase.Chess.Figures.*;

public class ChessBoard extends Board {
    private static final int chessFieldSize;

    static {
        chessFieldSize = 8;
    }

    private static ChessBoard instance;


    private ChessBoard() {
        super(chessFieldSize, chessFieldSize);
    }

    public static ChessBoard getInstance() {
        if (instance == null) {
            instance = new ChessBoard();
        }
        return instance;
    }

    public void newField() {
        this.resetField();
        // white
        field[0][0] = new Rook(true, new Coordinate(0, 0));
        field[0][1] = new Knight(true, new Coordinate(1, 0));
        field[0][2] = new Bishop(true, new Coordinate(2, 0));
        field[0][3] = new Queen(true, new Coordinate(3, 0));
        field[0][4] = new King(true, new Coordinate(4, 0));
        field[0][5] = new Bishop(true, new Coordinate(5, 0));
        field[0][6] = new Knight(true, new Coordinate(6, 0));
        field[0][7] = new Rook(true, new Coordinate(7, 0));
        for (int i = 0; i < chessFieldSize; i++)
            field[1][i] = new Pawn(true, new Coordinate(i, 1));
        //black
        field[7][0] = new Rook(false, new Coordinate(0, 7));
        field[7][1] = new Knight(false, new Coordinate(1, 7));
        field[7][2] = new Bishop(false, new Coordinate(2, 7));
        field[7][3] = new Queen(false, new Coordinate(3, 7));
        field[7][4] = new King(false, new Coordinate(4, 7));
        field[7][5] = new Bishop(false, new Coordinate(5, 7));
        field[7][6] = new Knight(false, new Coordinate(6, 7));
        field[7][7] = new Rook(false, new Coordinate(7, 7));
        for (int i = 0; i < chessFieldSize; i++)
            field[6][i] = new Pawn(false, new Coordinate(i, 6));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" |A|B|C|D|E|F|G|H\n").append("-----------------\n");
        for (int i = 0; i < chessFieldSize; i++) {
            sb.append(i + 1);
            for (int j = 0; j < chessFieldSize; j++)
                sb.append("|").append(
                        field[chessFieldSize - 1 - i][j] != null ? field[chessFieldSize - 1 - i][j] : " "
                );
            sb.append("\n-----------------\n");
        }
        sb.append(" |A|B|C|D|E|F|G|H\n").append("-----------------\n");
        return sb.toString();
    }

    public boolean canMove(Coordinate from, Coordinate to) {
        if (field[from.getY()][from.getX()] == null)
            return false;
        else {
            Figure tempFrom = field[from.getY()][from.getX()];
            Figure tempTo = field[to.getY()][to.getX()];
            switch (tempFrom.getClass().getSimpleName()) {
                case "Bishop":
                    if (tempFrom.canMove(to))
                        return true;
                case "King":
                    if (tempFrom.canMove(to))
                        return false;
                case "Knight":
                    if (tempFrom.canMove(to)) {
                        if (tempTo == null)
                            return true;
                        else
                            return tempFrom.isColorIsWhite() != tempTo.isColorIsWhite();
                    }
                case "Pawn":
                    if (tempFrom.canMove(to)) {
                        if (tempTo == null)
                            return from.getX() == to.getX();
                        else
                            return tempFrom.isColorIsWhite() != tempTo.isColorIsWhite() && from.getX() != to.getX();
                    }
                    return false;
                case "Queen":
                    if (tempFrom.canMove(to))
                        return true;
                case "Rook":
                    if (tempFrom.canMove(to))
                        return false;
            }
            return false;
        }
    }

    public void move(Coordinate from, Coordinate to) {
        field[from.getY()][from.getX()].moveTo(to);
        field[to.getY()][to.getX()] = field[from.getY()][from.getX()];
        field[from.getY()][from.getX()] = null;
    }
}
