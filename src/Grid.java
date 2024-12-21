import java.awt.*;

public class Grid {
    Tile[][] grid;

    public Grid(int size){
        this.grid = new Tile[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                grid[i][j] = new Tile();
            }
        }
    }
    

}
