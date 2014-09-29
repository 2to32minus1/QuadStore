////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

public class Quad {

	public final String sUid;
	public final String pUid;
	public final String oUid; // Will not be a UID if oIsLiteral == true
	public final String uid;
	public final boolean oIsLiteral; // I hope sizeof( boolean ) == 1

	private Quad( String sUid, String pUid, String oUid, String uid, boolean oIsLiteral ) {
		this.sUid = sUid;
		this.pUid = pUid;
		this.oUid = oUid;
		this.uid = uid;
		this.oIsLiteral = oIsLiteral;
	}

	////////////////////////
	// Static factory method so Quads cannot be constructed in inconsistent state
	////////////////////////
	
	public static Quad buildQuad( String sUid, String pUid, String oUid, String uid, boolean oIsLiteral ) throws IllegalArgumentException {

		// Special error logic + oUid processing logic if oIsLitera == true

		if ( oIsLiteral ) {
			if ( oUid == null || oUid.length() < 3 || oUid.charAt( 0 ) != '"' || oUid.charAt( oUid.length() - 1 ) != '"' )
				throw new IllegalArgumentException( "If oIsLiteral == true, oUid must include leading + trailing quotation marks, be non-null, and have at least one character inside the quotation marks!" );
			oUid = oUid.substring( 1, oUid.length() - 1 ); // Remove leading and trailing quotation marks
		}

		// Perform remaining validity checks

		if ( sUid == null || sUid.length() == 0 )
			throw new IllegalArgumentException( "sUid must be non-null with non-zero length!" );
		if ( pUid == null || pUid.length() == 0 )
			throw new IllegalArgumentException( "pUid must be non-null with non-zero length!" );
		if ( !oIsLiteral && oUid == null || oUid.length() == 0 ) // Already validated this field above if ( oIsLiteral == true )
			throw new IllegalArgumentException( "oUid must be non-null with non-zero length!" );
		if ( uid == null || uid.length() == 0 )
			throw new IllegalArgumentException( "uid must be non-null with non-zero length()" );

		return new Quad( sUid, pUid, oUid, uid, oIsLiteral );
	}

	////////////////////////

	public String toSimpleString() {
		return sUid + ", " + pUid + ", " + ( ( oIsLiteral ) ? ( "\"" + oUid + "\"" ) : oUid ) + ", " + uid;
	}

	////////////////////////

	@Override
	public String toString() {
		if ( oIsLiteral )
			return "{ s=" + sUid + ", p=" + pUid + ", o=\"" + oUid + "\", uid=" + uid + " objectIsLiteral=" + oIsLiteral + " }";
		return "{ s=" + sUid + ", p=" + pUid + ", o=" + oUid + ", uid=" + uid + " objectIsLiteral=" + oIsLiteral + " }";
	}

	////////////////////////

	public static void main( String [] args ) {
		Quad qLiteral = new Quad( "Fred", "PhoneNumber", "867-5309", "uid1", true );
		Quad qNoLiteral = new Quad( "U.S.", "CurrentPresident", "//world/us/citizens/Obama", "uid1", false );
		System.out.println( "qLiteral: " + qLiteral );
		System.out.println( "qNoLiteral: " + qNoLiteral );
	}
}	