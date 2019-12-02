package CustomDataTypes;

public class Quartet<X, Y, Z, W> { 
	public final X x;
	public final Y y;
	public final Z z;
	public final W w;
	
	public Quartet(X x, Y y, Z z, W w) {
		this.x = x; 
		this.y = y; 
		this.z = z;
		this.w = w;
	}
	
    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + "," + w + ")";
    }

    @Override
    public boolean equals(Object comparator) {
    	
        if (comparator == this) {
            return true;
        }

        if (!(comparator instanceof Quartet)){
            return false;
        }
        
        Quartet<X, Y, Z, W> comparator_ = (Quartet<X, Y, Z, W>) comparator;

        // this may cause NPE if nulls are valid values for x or y. The logic may be improved to handle nulls properly, if needed.
        if (comparator_.x == null || comparator_.y == null || this.x == null || this.y == null ||
        	comparator_.z == null || comparator_.w == null || this.z == null || this.w == null) {
        	throw new NullPointerException("Comparator Quartet Values cannot be null.");
        } else {
	        return comparator_.x.equals(this.x) && comparator_.y.equals(this.y) && comparator_.z.equals(this.z) && comparator_.w.equals(this.w);
        }
    }

    @Override
    public int hashCode() {
    	
        final int prime = 31;
        int result = 1;
        
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        result = prime * result + ((z == null) ? 0 : z.hashCode());
        result = prime * result + ((w == null) ? 0 : w.hashCode());
        
        return result;
        
    }
}