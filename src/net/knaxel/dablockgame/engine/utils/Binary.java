package net.knaxel.dablockgame.engine.utils;

public class Binary {
	public static String intToString(int number) {
		
		
	    StringBuilder result = new StringBuilder();

	    for(int i = 0; i < 32 ; i++) {
	        int mask = 1 << i;
	        result.append((number & mask) != 0 ? "1" : "0");

	        if ( 31-i < 16	 && (31-i) % 5 == 0)
	            result.append(" ");
	        if( 31-i == 19) 
	            result.append(" ");
	        if( 31-i == 23) 
	            result.append(" ");
	        if( 31-i == 31) 
	            result.append(" ");
	        
	    }
	    result.reverse();

	    return result.toString();
	}
}
