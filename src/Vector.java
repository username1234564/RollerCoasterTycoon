public class Vector {
    int x;
    int y;
    int z;

    public Vector(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString(){
        return "" + x + ", " + y + ", " + z;
    }
}
