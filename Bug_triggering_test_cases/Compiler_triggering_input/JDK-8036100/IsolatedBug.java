interface DrawableInterface { 
    default public boolean isShadowReceiver() { 
        return false; 
    } 

    default public boolean isShadowCaster() { 
        return false; 
    } 
} 

public class IsolatedBug { 
    private static final Box box = new Box(); 
	public static final java.util.ArrayList<Drawable> drawables = new java.util.ArrayList<>(); 

    public static void main(String[] args) throws InterruptedException { 
		drawables.add(box); 
		while (true) {
			drawables.forEach(drawable -> System.out.println(drawable + " C=" + drawable.isShadowCaster() + "/R=" + drawable.isShadowReceiver())); 
        } 
	}
} 