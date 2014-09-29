////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class Utils {

	////////////////////////

	public static void writeQuadListToFile( QuadStore qs, String path ) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream( path );
			StringBuffer sb = new StringBuffer();
			List<Quad> quads = qs.getAllQuads();
			for ( Quad q : quads )
				sb.append( q.toSimpleString() + "\r\n" );
			byte [] bytes = sb.toString().getBytes();
			fos.write( bytes );
		}
		catch ( Exception e ) {
			throw e;
		}
		finally {
			if ( fos != null )
				fos.close();
		}

	}

	////////////////////////

	public static List<Quad> readQuadListFromFile( String path ) throws Exception {
		List<Quad> retList = new ArrayList<Quad>();
		FileInputStream fos = null;
		try {
			File f = new File( path );
			long len = f.length();
			if ( len > ( long ) Integer.MAX_VALUE )
				throw new IllegalStateException( "Error - File length exceeds " + Integer.MAX_VALUE + " bytes!" );
			long availMem = Runtime.getRuntime().freeMemory();
			if ( availMem < availMem )
				throw new OutOfMemoryError( "Not enough memory to read in file!" );
			fos = new FileInputStream( f );
			byte [] bytes = new byte[ ( int ) len ];
			if ( fos.read( bytes ) != ( int ) len )
				throw new IllegalStateException( "Error reading file!" );
			String s = new String( bytes );
			retList = Parsers.parseQuads( s );
		}
		catch ( Exception e ) {
			throw e;
		}
		finally {
			if ( fos != null)
				fos.close();
		}

		return retList;
	}

}
