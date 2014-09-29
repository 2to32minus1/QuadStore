////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

public class Parsers {

	////////////////////////

	public static List<Quad> parseQuads( String data ) throws ParseException {

		List<Quad> retList = new ArrayList<Quad>();
		if ( data == null || data.length() <= 0 ) return retList;

		int lineNum = 0; // For syntax error messages
		int index = 0;
		int len = data.length();
		String [] elems = new String[ 4 ]; // Temp storage of tuple elements

		char c;

		// Outer loop - reaches top of loop when beginning parsing of each tuple element

		while ( index < len ) {

			// i-loop sequentially parses subject, predicate, object, and tuple's UID

			for ( int i = 0; i < 4; ++i ) {

				// Skip white space

				while ( index < len && Character.isWhitespace( c = data.charAt( index ) ) )
					++index;

				// Grab quad element chars

				int beginIndex = index;
				while ( index < len ) {
					c = data.charAt( index++ );
					if ( c == '\r' || c == '\n' ) { // End of line = finished reading a Quad
						if ( c == '\r' && index < len ) { // We expect \n after a \r so consume it and increment index
							if ( data.charAt( index ) == '\n' ) {
								++index;
							}
						}
						++lineNum; // Only place lineNum is incremented
						if ( i != 3 ) // If a \n is encountered before 4 tuple elements have been read = input data syntax error
							throw new ParseException( "Syntax error near line: " + lineNum, lineNum ); //Throw
						elems[ i ] = data.substring( beginIndex, index - 1 ).trim();
						boolean objectIsLiteral = ( elems[ 2 ].charAt( 0 ) == '"' );
						try {
							Quad q = Quad.buildQuad( elems[ 0 ], elems[ 1 ], elems[ 2 ], elems[ 3 ], objectIsLiteral ); // buildQuad() will strip off begin/end quotation marks
							retList.add( q );
						}
						catch ( IllegalArgumentException ex ) {
							throw new ParseException( "Invalid Quad near line: " + lineNum + " Error message: " + ex.getMessage(), lineNum ); // Throw
						}
						elems[ 0 ] = elems[ 1 ] = elems[ 2 ] = elems[ 3 ] = null;
						break; // i == 3 so this will cause control to return to outer while loop to parse next tuple line
					}
					if ( c == ',' ) {
						elems[ i ] = data.substring( beginIndex, index - 1 ).trim();
						beginIndex = index;
						break; // Causes control to return to i-loop for parsing next tuple element
					}

				}

			}
		}

		return retList;
	}

}
