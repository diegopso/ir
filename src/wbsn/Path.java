package wbsn;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Path{
    public ArrayList<Integer> nodes;

    public Path() {
        nodes = new ArrayList<Integer>();
    }

    public Path(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }

    @Override
    protected Path clone(){
        ArrayList<Integer> path = new ArrayList<Integer>();

        for (Integer integer : this.nodes) {
            path.add(integer);
        }

        return new Path(path);
    }

    Integer[] toArray(){
        Integer[] a = new Integer[this.nodes.size()];
        this.nodes.toArray(a);

        return a;
    }

    boolean isInPath(int i) {
        for (Integer integer : nodes) {
            if(i == integer)
                return true;
        }
        return false;
    }

    void add(int i) {
        this.nodes.add(i);
    }
}
