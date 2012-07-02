package statistics;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class Network {
    public static boolean[][] relationships;
    public static Integer lineLen;
    public static ArrayList<Path> paths;

    public Network() {
        this.factory();
    }
    
    public static void factory(){
        //mudar isso depois dos testes...
        relationships = new boolean[][]{
            {false, true, true, false, true, false},
            {true, false, false, true, false, true},
            {true, false, false, false, false, false},
            {false, true, false, false, true, false},
            {true, false, false, true, false, false},
            {false, true, false, false, false, false}
        };
        
        paths = new ArrayList<Path>();
        lineLen = 6;
    }
    
    public void mapPaths(Integer source, Integer sink, Path previous_path){
        if(previous_path == null){
            previous_path = new Path();
        }
        previous_path.add(source);
        
        for (int i = 0; i < lineLen; i++) {
            if(previous_path.isInPath(i)){
                continue;
            }
            
            if(relationships[source][i]){
                if(i == sink){
                    previous_path.add(i);
                    paths.add(previous_path);
                    return;
                }else{
                    mapPaths(i, sink, previous_path.clone());
                }
            }
        }
    }
    
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
}
