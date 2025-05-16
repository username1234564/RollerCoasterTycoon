import java.util.HashMap;

public class RailTrack extends Building{
    // Vector previousPosition;
    Vector nextPosition;
    static int up = 1;
    static int down = 2;
    static int left = 4;
    static int right = 8;  //binary logic 
    // int ID;
    
    static HashMap <Vector, Integer> bits = new HashMap<Vector, Integer>(){{
        put(new Vector(1, 0, 0), right);
        put(new Vector(-1, 0, 0), left);
        put(new Vector(0, 1, 0), down);
        put(new Vector(0, -1, 0), up);
    }};

    static HashMap <Integer, String> textures = new HashMap<Integer, String>(){{
        put(up | down, "src/RollerCoasterTycoonStrait2.png");
        put(left | right, "src/RollerCoasterTycoonStrait1.png");
        put(left | down, "src/RollerCoasterTycoonTurn1.png");
        put(down | right, "src/RollerCoasterTycoonTurn2.png");
        put(right | up, "src/RollerCoasterTycoonTurn3.png");
        put(up | left, "src/RollerCoasterTycoonTurn4.png");
    }};

    public RailTrack(Vector position, Vector previousPosition, Vector nextPosition ){
        super(textures.get(up | down), new Vector(0, -70, 0), position, new Vector(3, 3, 1));
        Vector from = previousPosition.subtract(position).normalized();
        Vector to = nextPosition.subtract(position).normalized();
        setImage(textures.get(bits.get(from) | bits.get(to)));
    }
}
