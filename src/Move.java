/**
 * Created by Memo on 27.06.2017.
 */
public class Move {
    int ttt;
    int[] p;
    Move(int pos){
        ttt = pos;
    }
    Move(int[] pos){
        p = new int[pos.length];
        for(int i = 0; i< pos.length;i++){
            p[i] = pos[i];
        }
    }
    int [] getMorris() {
        return p;
    }
    int getT3(){
        return ttt;
    }
}
