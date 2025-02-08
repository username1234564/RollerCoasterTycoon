public class Vector {
    int x;
    int y;
    int z;

    public Vector(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector subtract(Vector v){
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    public Vector add(Vector v){
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public String toString(){
        return "" + x + ", " + y + ", " + z;
    }
}
