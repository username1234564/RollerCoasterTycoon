import java.awt.*;

public class Grid {
    Tile[][] grid;
    int size;

    public Grid(int size){
        this.size = size;
        this.grid = new Tile[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                grid[i][j] = new Tile();
            }
        }
    }

    public void build(Building building, Vector position){
        grid[position.x][position.y].building = building;
    }

    public Tile getTile(Vector position){
        return grid[position.x][position.y];
    }
    

}
