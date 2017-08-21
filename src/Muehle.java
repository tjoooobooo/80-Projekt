import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Memo on 27.06.2017.
 */
public class Muehle implements ImmutableBoard<Move>, SaveableGame<ImmutableBoard> {
    private int[] board = new int[24];
    private int turn = +1;
    private List<Move> history = new ArrayList<>();
    private List<int[]> brainThree = new ArrayList<>();
    private boolean isMill = false;
    private Muehle parent = null;
    private boolean flip = false;
    private boolean isFlyingAllowed = false;
    private boolean p1GiveUp = false;
    private boolean p2GiveUp = false;

    private int[][] neighbours = {
            {1, 7}, {0, 2, 9}, {1, 3}, {2, 4, 11}, {3, 5}, {4, 6, 13}, {5, 7}, {0, 6, 15},
            {9, 15}, {8, 10, 17}, {9, 11}, {3, 10, 12, 19}, {11, 13}, {5, 12, 14, 21}, {13, 15},
            {7, 8, 14, 23}, {17, 23}, {9, 16, 18}, {17, 19}, {11, 18, 20}, {19, 21}, {13, 20, 22},
            {23, 21}, {15, 16, 22}};
    private int[][] rows = {{0, 1, 2}, {8, 9, 10}, {16, 17, 18}, {22, 21, 20}, {14, 13, 12}, {6, 5, 4}, {0, 7, 6}, {8, 15, 14}, {16, 23, 22}, {18, 19, 20}, {10, 11, 12}, {2, 3, 4}, {7, 15, 23}, {3, 11, 19}, {1, 9, 17}, {5, 13, 21}};

    private List<int[]> threeInRow = new ArrayList<>(Arrays.asList(rows));


    @Override
    public ImmutableBoard<Move> makeMove(Move positions) {
        Muehle child = new Muehle();
        child.board = Arrays.copyOf(board, 24);
        child.brainThree = brainThree.stream().collect(Collectors.toList());
        child.threeInRow = threeInRow.stream().collect(Collectors.toList());
        child.history = history.stream().collect(Collectors.toList());
        child.history.add(positions);
        child.isMill = isMill;
        child.parent = this;

        if (positions.p.length == 1) {
            child.board[positions.p[0]] = turn;
        } else if (positions.p.length == 2 && history.size() >= 18) {
            for (int i = 0; i < neighbours[positions.p[0]].length; i++) {
                if (neighbours[positions.p[0]][i] == positions.p[1]) {
                    child.board[positions.p[0]] = 0;
                    child.board[positions.p[1]] = turn;
                }
            }
        } else if (positions.p.length == 2) {
            child.board[positions.p[0]] = turn;
            child.board[positions.p[1]] = 0;
        } else { // stein wird geschoben und einer vom Gegner entfernt
            for (int i = 0; i < neighbours[positions.p[0]].length; i++) {
                if (neighbours[positions.p[0]][i] == positions.p[1]) {
                    child.board[positions.p[0]] = 0;
                    child.board[positions.p[1]] = turn;
                }
            }
            child.board[positions.p[2]] = 0;
        }
        child.threeInARow();
        child.turn = -turn;
        return child;
    }

    @Override
    public ImmutableBoard<Move> undoMove() {
        assert history.size() != 0;
        if (this.parent == null) return new Muehle();
        return parent;
    }

    @Override
    public List<Move> moves() {
        //checke ob die Mühle zerstört wurde und die Steine wieder entfernt werden dürfen
        for (int i = 0; i < brainThree.size(); i++) {
            for (int j = 0; j < 3; j++) {
                if (board[brainThree.get(i)[j]] == 0) {
                    threeInRow.add(brainThree.remove(i));
                    break;
                }
            }
        }
        IntStream range = IntStream.range(0, 24);
        List<Move> plays = new ArrayList<>();
        //Spielphase 3 -> Fliegen
        int counter = 0;
        counter += IntStream.range(0, 24).filter(i -> board[i] == -turn).count();
        if (history.size() >= 18 && counter == 3) isFlyingAllowed = true;
        if (isFlyingAllowed) {
            IntStream.range(0, 24).filter(i -> board[i] == turn).
                    forEach(i -> IntStream.range(0, 24).filter(j -> board[j] == 0).
                            forEach(j -> plays.add(new Move(new int[]{i, j}))));
        }
        // es wurden noch nicht alle möglichen Steine gesetzt
        else if (!(history.size() >= 18))
            range.filter(i -> board[i] == 0).forEach(i -> plays.add(new Move(new int[]{i})));
            //steine schieben
        else if (history.size() >= 18) {
            List<Integer> schieben = new ArrayList<>();
            range.filter(i -> board[i] == turn).forEach(i -> schieben.add(i));
            for (int i : schieben) {
                int[] nachbar = neighbours[i];
                for (int j : nachbar) {
                    if (board[j] == 0) plays.add(new Move(new int[]{i, j}));
                }
            }
        }
        //Steine entfernen
        List<Move> tmp = plays.stream().collect(Collectors.toList());
        for (Move entfernen : tmp) {
            ImmutableBoard newGame = this.makeMove(entfernen);
            if (((Muehle) newGame).isMill) {
                int tmp2 = plays.size();
                hier:
                for (int i = 0; i < board.length; i++) {
                    if (((Muehle) newGame).board[i] == -turn) {
                        for (int[] check : brainThree) {
                            for (int j = 0; j < check.length; j++) if (check[j] == i) continue hier;
                        }
                        int[] out = Arrays.copyOf(entfernen.p, entfernen.p.length + 1);
                        out[out.length - 1] = i;
                        plays.add(new Move(out));
                    }
                }
                if (plays.size() > tmp2) plays.remove(plays.indexOf(entfernen));
            }
        }
        return plays;
    }

    @Override
    public List<Move> getHistory() {
        return history;
    }

    @Override
    public boolean isWin() {
        int black = 0;
        int white = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == turn) {
                black++;
            }
            if (board[i] == -turn) {
                white++;
            }
        }
        if (black < 3 && history.size() >= 18 || white < 3 && history.size() >= 18) return true;
        if (moves().size() == 0) return true;
        return false;
    }

    @Override
    public boolean isDraw() {
        return p1GiveUp && p2GiveUp;
    }

    @Override
    public ImmutableBoard<Move> flip() {
        Muehle child = new Muehle();
        child.flip = !flip;
        child.board = Arrays.copyOf(board, 24);
        IntStream.range(0, 24).forEach(i -> child.board[i] = -board[i]);
        child.brainThree = brainThree.stream().collect(Collectors.toList());
        child.threeInRow = threeInRow.stream().collect(Collectors.toList());
        child.history = history.stream().collect(Collectors.toList());
        child.isMill = isMill;
        child.parent = this;
        return child;
    }

    @Override
    public boolean isFlipped() {
        return flip;
    }

    @Override
    public String toString() {
        char[] repr = {'#', '.', 'X'};
        String field = "\n" +
                "01-------02-------03\n" +
                "|  09----10----11  |\n" +
                "|  |  17-18-19  |  |\n" +
                "08-16-24    20-12-04\n" +
                "|  |  23-22-21  |  |\n" +
                "|  15----14----13  |\n" +
                "07-------06-------05";
        String n = "";
        IntStream.range(1, 25).filter(i -> board[i - 1] != 0).boxed();
        for (int i = 1; i < 25; i++) {
            String num = "";
            if (i < 10) num += Integer.toString(0);
            num += Integer.toString(i);
            if (board[i - 1] != 0)
                field = field.replace(num, Character.toString(repr[board[i - 1] + 1]) + Character.toString(repr[board[i - 1] + 1]));
        }
        if (!(history.size() >= 18)) {
            field += "\nGib gültigen Zug ein (1-24 + <ENTER>) oder wenn du eine Mühle erzeugst (1-24<WHITESPACE>1-24 + <ENTER>)" +
                    "\n[0: Computer zieht, ?: Hilfe]: _";
        } else {
            field += "\nGib gültigen Zug ein (1-24<WHITESPACE>1-24<WHITESPACE>1-24 + <ENTER>) oder wenn du eine Mühle erzeugst (1-24<WHITESPACE>1-24<WHITESPACE>1-24 + <ENTER>)" +
                    "\n[0: Computer zieht, ?: Hilfe]: _";
        }

        return field;
    }

    public void save(ImmutableBoard board, String name) throws IOException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(name));
            StringBuilder str = new StringBuilder();

            if (history.size() != 0) {
                str.append(String.format("%02d", history.get(0).p[0]));
                for (int i = 1; i < history.size(); i++) {
                    str.append(",");
                    if (history.get(i).p.length == 1) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                    } else if (history.get(i).p.length == 2) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[1]));
                    } else if (history.get(i).p.length == 3) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[1]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[2]));
                    }
                }
                //System.out.println(isFlipped());
                if (isFlipped()) {
                    str.append(",");
                    str.append("f");
                }
            } else if (isFlipped()) {
                str.append("f");
            }
            str.append("\n");
            bw.write(str.toString());
            System.out.print("Spiel erfolgreich unter dem Namen \"" + name + "\" gespeichert!");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(ImmutableBoard board, Path path) throws IOException {
        try {
            BufferedWriter w = Files.newBufferedWriter(path);
            StringBuilder str = new StringBuilder();

            if (history.size() != 0) {
                str.append(String.format("%02d", history.get(0).p[0]));
                for (int i = 1; i < history.size(); i++) {
                    str.append(",");
                    if (history.get(i).p.length == 1) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                    } else if (history.get(i).p.length == 2) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[1]));
                    } else if (history.get(i).p.length == 3) {
                        str.append(String.format("%02d", history.get(i).p[0]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[1]));
                        str.append("-");
                        str.append(String.format("%02d", history.get(i).p[2]));
                    }
                }
                if (isFlipped()) {
                    str.append(",");
                    str.append("f");
                }
            } else if (isFlipped()) {
                str.append("f");
            }
            str.append("\n");
            w.write(str.toString());
            System.out.print("Spiel erfolgreich unter dem Pfad \"" + path + "\" gespeichert!");
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImmutableBoard load(String name) throws IOException {
        ImmutableBoard loadedBoard = new Muehle();
        try {
            char[] tmp_arr;
            boolean geflippt = false;
            StringBuilder str = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(name));
            String line;
            String[] arrstr;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            if (str.toString().contains("f")) {
                str.replace(str.length()-2,str.length(),"");
                geflippt = true;
            }
            arrstr = str.toString().replaceAll(" ", "").split(",");
            for (int i = 0; i < arrstr.length; i++) {
                if (arrstr[i].contains("-")) {
                    char[] tmpchararr = arrstr[i].toCharArray();
                    if (tmpchararr.length == 5) {
                        StringBuilder amk = new StringBuilder();
                        StringBuilder amk2 = new StringBuilder();
                        amk.append(tmpchararr[0]);
                        amk.append(tmpchararr[1]);
                        amk2.append(tmpchararr[3]);
                        amk2.append(tmpchararr[4]);
                        int[] twointstmparr = new int[2];
                        twointstmparr[0] = Integer.parseInt(amk.toString());
                        twointstmparr[1] = Integer.parseInt(amk2.toString());
                        Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(twointstmparr));
                        loadedBoard = newGame;
                    } else {
                        StringBuilder amk = new StringBuilder();
                        StringBuilder amk2 = new StringBuilder();
                        StringBuilder amk3 = new StringBuilder();
                        amk.append(tmpchararr[0]);
                        amk.append(tmpchararr[1]);
                        amk2.append(tmpchararr[3]);
                        amk2.append(tmpchararr[4]);
                        amk3.append(tmpchararr[6]);
                        amk3.append(tmpchararr[7]);
                        int[] threeintstmparr = new int[3];
                        threeintstmparr[0] = Integer.parseInt(amk.toString());
                        threeintstmparr[1] = Integer.parseInt(amk2.toString());
                        threeintstmparr[2] = Integer.parseInt(amk3.toString());
                        Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(threeintstmparr));
                        loadedBoard = newGame;
                    }
                } else {
                    int[] tmpintarr = {Integer.parseInt(arrstr[i])};
                    Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(tmpintarr));
                    loadedBoard = newGame;
                }

            }
            if (geflippt) {
                Muehle newGame = (Muehle) loadedBoard.flip();
                loadedBoard = newGame;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedBoard;
    }

    public ImmutableBoard load(Path path) throws IOException {

        ImmutableBoard loadedBoard = new Muehle();
        try {
            char[] tmp_arr;
            boolean geflippt = false;
            StringBuilder str = new StringBuilder();
            BufferedReader br = Files.newBufferedReader(path);
            String line;
            String[] arrstr;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            if (str.toString().contains("f")) {
                str.replace(str.length()-2,str.length(),"");
                geflippt = true;
            }
            arrstr = str.toString().replaceAll(" ", "").split(",");
            for (int i = 0; i < arrstr.length; i++) {
                if (arrstr[i].contains("-")) {
                    char[] tmpchararr = arrstr[i].toCharArray();
                    if (tmpchararr.length == 5) {
                        StringBuilder amk = new StringBuilder();
                        StringBuilder amk2 = new StringBuilder();
                        amk.append(tmpchararr[0]);
                        amk.append(tmpchararr[1]);
                        amk2.append(tmpchararr[3]);
                        amk2.append(tmpchararr[4]);
                        int[] twointstmparr = new int[2];
                        twointstmparr[0] = Integer.parseInt(amk.toString());
                        twointstmparr[1] = Integer.parseInt(amk2.toString());
                        Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(twointstmparr));
                        loadedBoard = newGame;
                    } else {
                        StringBuilder amk = new StringBuilder();
                        StringBuilder amk2 = new StringBuilder();
                        StringBuilder amk3 = new StringBuilder();
                        amk.append(tmpchararr[0]);
                        amk.append(tmpchararr[1]);
                        amk2.append(tmpchararr[3]);
                        amk2.append(tmpchararr[4]);
                        amk3.append(tmpchararr[6]);
                        amk3.append(tmpchararr[7]);
                        int[] threeintstmparr = new int[3];
                        threeintstmparr[0] = Integer.parseInt(amk.toString());
                        threeintstmparr[1] = Integer.parseInt(amk2.toString());
                        threeintstmparr[2] = Integer.parseInt(amk3.toString());
                        Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(threeintstmparr));
                        loadedBoard = newGame;
                    }
                } else {
                    int[] tmpintarr = {Integer.parseInt(arrstr[i])};
                    Muehle newGame = (Muehle) loadedBoard.makeMove(new Move(tmpintarr));
                    loadedBoard = newGame;
                }
            }

            if (geflippt) {
                Muehle newGame = (Muehle) loadedBoard.flip();
                loadedBoard = newGame;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedBoard;
    }
    // Methoden die nicht im Interface sind

    boolean threeInARow() {
        isMill = false;
        if (history.size() < 5) return false;
        else {
            for (int i = 0; i < threeInRow.size(); i++) {
                if (Math.abs(board[threeInRow.get(i)[0]] + board[threeInRow.get(i)[1]] + board[threeInRow.get(i)[2]]) == 3) {
                    brainThree.add(threeInRow.remove(i));
                    isMill = true;
                }
            }
        }
        return isMill;
    }

    int freeNeighbours() {
        int tmp = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == turn) {
                for (int j = 0; j < neighbours[i].length; j++) {
                    if (board[neighbours[i][j]] == 0) tmp++;
                    if (board[neighbours[i][j]] == turn) tmp += 2;

                }
            }
        }
        return tmp;
    }

    void show() {
        for (Move m : moves()) {
            for (int i = 0; i < m.getMorris().length; i++) {
                System.out.print(m.getMorris()[i] + ", ");
            }
            System.out.println();
        }
    }

    boolean getIsMill() {
        return isMill;
    }

    int[] getBoard() {
        return board;
    }

    List<int[]> getBrainThree() {
        return brainThree;
    }

}
























