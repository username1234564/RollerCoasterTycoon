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

    public void build(Building building){
        removeBuilding(getTile(building.position).building);
        for(int i = 0; i < building.dimensions.x; i++)
            for(int j = 0; j < building.dimensions.y; j++)
                grid[building.position.x + i][building.position.y + j].building = building;
    }

    public void removeBuilding(Building building){
        if(building == null)
            return;
        for(int i = 0; i < building.dimensions.x; i++)
            for(int j = 0; j < building.dimensions.y; j++)
               grid[building.position.x + i][building.position.y + j].building = null;
    }

    public Tile getTile(Vector position){
        return grid[position.x][position.y];
    }
    

}
