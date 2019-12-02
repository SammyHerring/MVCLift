package CustomDataTypes;

public class Tuple<X, Y> { 
	public final X x;
	public final Y y;

	
	public Tuple(X x, Y y) {
		this.x = x; 
		this.y = y; 
	}
	
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object comparator) {
    	
        if (comparator == this) {
            return true;
        }

        if (!(comparator instanceof Quartet)){
            return false;
        }
        
        Tuple<X, Y> comparator_ = (Tuple<X, Y>) comparator;

        // this may cause NPE if nulls are valid values for x or y. The logic may be improved to handle nulls properly, if needed.
        if (comparator_.x == null || comparator_.y == null || this.x == null || this.y == null) {
        	throw new NullPointerException("Comparator Tuple Values cannot be null.");
        } else {
	        return comparator_.x.equals(this.x) && comparator_.y.equals(this.y);
        }
    }

    @Override
    public int hashCode() {
    	
        final int prime = 31;
        int result = 1;
        
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        
        return result;
        
    }
}