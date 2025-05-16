public class Vector {
    int x;
    int y;
    int z;

    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector subtract(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }


    public Vector normalized() {
        double magnitude = Math.sqrt(x*x + y*y + z*z);
        if (magnitude == 0) {
            return new Vector(0, 0, 0); // Prevent 0/0
        }
        return new Vector((int)Math.round(x / magnitude), (int)Math.round(y / magnitude), (int)Math.round(z / magnitude));
    }

    public boolean equals(Object obj) { //normal equals is bad for vector
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        Vector v = (Vector) obj;
        return x == v.x && y == v.y && z == v.z;
    }


    public int hashCode() {
        return Integer.hashCode(x) ^ Integer.hashCode(y) ^ Integer.hashCode(z);
    }


    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
