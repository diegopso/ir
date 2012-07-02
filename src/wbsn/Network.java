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
        //for test
        boolean[][] relationships_matrix = new boolean[][]{
            {false, true, true, false, true, false},
            {true, false, false, true, false, true},
            {true, false, false, false, false, false},
            {false, true, false, false, true, false},
            {true, false, false, true, false, false},
            {false, true, false, false, false, false}
        };
        
        this.factory(relationships_matrix);
    }
    
    public Network(boolean[][] relationship_matrix) {
        this.factory(relationship_matrix);
    }
    
    public static void factory(boolean[][] relationship_matrix){
        relationships = relationship_matrix;
        paths = new ArrayList<Path>();
        lineLen = relationship_matrix.length;
    }
    
    public void mapPaths(Integer source, Integer sink){
        mapPaths(source, sink, new Path());
    }
    
    private void mapPaths(Integer source, Integer sink, Path previous_path){
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
