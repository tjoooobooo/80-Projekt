import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.*;

/**
 * Created by Memo on 30.06.2017.
 */

public class TicTacToe implements ImmutableBoard<Move>{
    private int[] board = new int[9];
    private int turn = +1;
    private List<Move> history = new ArrayList<>();
    private TicTacToe parent = null;
    private int depth = 0;
    private boolean flip = false;

    @Override
    public ImmutableBoard makeMove(Move move) {
        // assert pos >= 0 && pos <= 8 && board[pos] == 0 && !isWin();
        TicTacToe b = new TicTacToe();
        b.board = Arrays.copyOf(board,9);
        b.history = history.stream().collect(Collectors.toList());
        b.board[move.getT3()] = turn;
        b.turn = -turn;
        b.depth = depth + 1;
        b.parent = this;
        b.history.add(move);
        return b;
    }

    @Override
    public ImmutableBoard undoMove() {
        assert parent != null;
        return parent;
    }

    @Override
    public List moves() {
        List<Move> z = new ArrayList<>();
        IntStream.range(0, 9).filter(i -> board[i] == 0).forEach(el -> z.add(new Move(el)));
        return z;
    }


    @Override
    public List getHistory() {
        return history;
    }

    @Override
    public boolean isWin() {
        int[][] rows = {{0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}};
        if (depth < 5) return false;
        return IntStream.range(0,8).
                map(row -> Arrays.stream(rows[row]).
                        map(pos -> board[pos]).
                        sum()).
                map(Math::abs).
                filter(r -> r == 3).
                findAny().
                isPresent();
    }

    @Override
    public boolean isDraw() {
        return depth == 9;
    }

    @Override
    public ImmutableBoard flip() {
        TicTacToe b = new TicTacToe();
        flip = !flip;
        b.board = Arrays.copyOf(board,9);
        b.history = history.stream().collect(Collectors.toList());
        IntStream.range(0, 9).forEach(i -> b.board[i] = -board[i]);;
        b.turn = -turn;
        b.depth = depth + 1;
        b.parent = this;
        return b;
    }

    @Override
    public boolean isFlipped() {
        return flip;
    }

    @Override
    public String toString() {
        char[] repr = {'O', '.', 'X'};
        String s = "\n";
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) s += repr[board[i * 3 + j] + 1] + " ";
            s += "\n";
        }
        s += "\nGib gültigen Zug ein (1-9 + <ENTER>)" + "\n[0: Computer zieht, ?: Hilfe]: _";
        return s;
    }

    public void save(TicTacToe board, String name) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            StringBuilder str = new StringBuilder();

            if (board.history.size() == 0) {
                System.out.print("Das Spielfeld ist leer!");
            } else {
                str.append(board.history.get(0).getT3());
                for (int i = 1; i < board.history.size(); i++) {
                    str.append(",");
                    str.append(board.history.get(i).getT3());
                }
                if (board.isFlipped()) {
                    str.append(",");
                    str.append("f");
                }
                str.append("\n");
                bw.write(str.toString());
                System.out.print("Spiel erfolgreich unter dem Namen \"" + name + "\" gespeichert!");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(TicTacToe board, Path path) throws IOException {
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            StringBuilder str = new StringBuilder();

            if (board.history.size() == 0) {
                System.out.print("Das Spielfeld ist leer!");
            } else {
                str.append(board.history.get(0).getT3());
                for (int i = 1; i < board.history.size(); i++) {
                    str.append(",");
                    str.append(board.history.get(i).getT3());
                }
                if (board.isFlipped()) {
                    str.append(",");
                    str.append("f");
                }
                str.append("\n");
                w.write(str.toString());
                System.out.print("Spiel erfolgreich unter dem Pfad \"" + path + "\" gespeichert!");
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TicTacToe load(String name) throws IOException {
        TicTacToe loadedBoard = new TicTacToe();
        try {
            char[] tmp_arr;
            StringBuilder str = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(name));
            String line;

            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            line = str.toString().replaceAll(",", "");
            if (line.endsWith("f")) {
                TicTacToe newBoard = (TicTacToe) loadedBoard.flip();
                loadedBoard = newBoard;
                line = line.substring(0, line.length() - 1);
            }
            tmp_arr = line.toCharArray();
            for (int i = 0; i < tmp_arr.length; i++) {
                Move m = new Move((int) tmp_arr[i] - 48);
                TicTacToe newBoard = (TicTacToe) loadedBoard.makeMove(m);
                loadedBoard = newBoard;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedBoard;
    }

    public TicTacToe load(Path path) throws IOException {
        TicTacToe loadedBoard = new TicTacToe();

        try {
            char[] tmp_arr;
            String line;
            BufferedReader r = Files.newBufferedReader(path);
            StringBuilder str = new StringBuilder();

            while ((line = r.readLine()) != null) {
                str.append(line);
            }
            line = str.toString().replaceAll(",", "");
            if (line.endsWith("f")) {
                loadedBoard.flip();
                line = line.substring(0, line.length() - 1);
            }
            tmp_arr = line.toCharArray();
            for (int i = 0; i < tmp_arr.length; i++) {
                Move m = new Move((int) tmp_arr[i] - 48);
                loadedBoard.makeMove(m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedBoard;
    }
}

