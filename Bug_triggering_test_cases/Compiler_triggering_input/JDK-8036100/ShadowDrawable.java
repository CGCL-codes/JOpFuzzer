public interface ShadowDrawable extends DrawableInterface { 
    @Override 
    default public boolean isShadowReceiver() { 
        return true; 
    } 

    @Override 
    default public boolean isShadowCaster() { 
        return true; 
    } 
} 
