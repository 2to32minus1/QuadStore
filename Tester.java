////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.io.PrintStream;

public class Tester {

	////////////////////////

	public static void main( String [] args ) {
		testParseSmallBookGraph( System.out );
		testParseSmallBookGraphWithExtraneousWhitespace( System.out );
		testParseGraphWithMalformedQuad( System.out );
		testParse1MQuadsFast( System.out );
		testReadQuadsFromFile( System.out );
		testQuadStore( System.out );
	}

	////////////////////////

	public static void testReadQuadsFromFile( PrintStream ps ) {
		ps.println( "------ testReadQuadsFromFile() ------" );
		ps.println();
		try {
			List<Quad> list = Utils.readQuadListFromFile( "c:\\users\\rtc1\\quads2.txt" );
			for ( Quad q : list )
				ps.println( q.toString() );
		}
		catch ( Exception e ) {
			System.err.println( "Exception occurred: " + e.getMessage() );
			ps.println();
			return;
		}

		ps.println();
	}

	////////////////////////

	public static void testQuadStore( PrintStream ps ) {
		ps.println( "------ testQuadStore() ------" );
		ps.println();
		QuadStore qs = new HashQuadStore();
		List<Quad> parsedQuadList = getBookQuadList();
		for ( Quad q : parsedQuadList )
			if ( qs.addQuad( q ) != true )
				System.err.println( "Error adding Quad: [" + q.toSimpleString() + " ]" );

		ps.println( "----------->Utils.writeQuadListToFile():" );
		try {
			Utils.writeQuadListToFile( qs, "c:\\users\\rtc1\\Quads.txt" );
		}
		catch ( Exception e ) {
			System.err.println( "Exception occurred! : " + e.getMessage() );
		}
		ps.println();

		ps.println( "----------->getAllQuads():" );
		List<Quad> qsQuadList = qs.getAllQuads();
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuad():" );
		ps.println( "getQuad( 20 ): " + qs.getQuad("20" ) );
		ps.println();

		ps.println( "----------->containsQuad():" );
		ps.println( "qs.containsQuad( 12 ): " + qs.containsQuad( "12" ) );
		ps.println();

		ps.println( "----------->getQuadsForSubj():" );
		qsQuadList = qs.getQuadsForSubj( "0" );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuadsForPred():" );
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		qsQuadList = qs.getQuadsForPred( sde.toString() );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuadsForObj():" );
		qsQuadList = qs.getQuadsForObj( "11" );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuadsForSubjAndPred():" );
		qsQuadList = qs.getQuadsForSubjAndPred( "0", sde.toString() );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuadsForPredAndObj():" );
		qsQuadList = qs.getQuadsForPredAndObj( sde.toString(), "11" );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getQuadsForLiteral():" );
		qs.addQuad( Quad.buildQuad( "RickCreamer", "PhoneNumber", "\"867-\"5309\"\"", "123-45-6789", true ) );
		qsQuadList = qs.getQuadsForLiteral( "867-\"5309\"" );
		for ( Quad q : qsQuadList )
			ps.println( q.toString() );
		ps.println();

		ps.println( "----------->getObjectUidsForSubjAndPred():" );
		List<String> stringList  = qs.getObjectUidsForSubjAndPred( "0", sde.toString() );
		for ( String s : stringList )
			ps.println( s.toString() );

		ps.println();
	}

	////////////////////////
	// This is a helper method, not a test method.  So, it doesn't print a banner.
	
	public static List<Quad> getBookQuadList() {
		StringBuffer sb = new StringBuffer();
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		String edgeUid = sde.toString();
		sb.append( "0, " + edgeUid + ",1,1\n" );		sb.append( "0, " + edgeUid + ",5,2\n" );
		sb.append( "0, " + edgeUid + ",6,3\n" );		sb.append( "2, " + edgeUid + ",0,4\n" );
		sb.append( "2, " + edgeUid + ",3,5\n" );		sb.append( "3, " + edgeUid + ",2,6\n" );
		sb.append( "3, " + edgeUid + ",5,7\n" );		sb.append( "4, " + edgeUid + ",2,8\n" );
		sb.append( "4, " + edgeUid + ",3,9\n" );		sb.append( "4, " + edgeUid + ",11,10\n" );
		sb.append( "5, " + edgeUid + ",4,11\n" );		sb.append( "6, " + edgeUid + ",4,12\n" );
		sb.append( "6, " + edgeUid + ",9,13\n" );		sb.append( "7, " + edgeUid + ",6,14\n" );
		sb.append( "7, " + edgeUid + ",8,15\n" );		sb.append( "8, " + edgeUid + ",7,16\n" );
		sb.append( "8, " + edgeUid + ",9,17\n" );		sb.append( "9, " + edgeUid + ",10,18\n" );
		sb.append( "9, " + edgeUid + ",11,19\n" );		sb.append( "10, " + edgeUid + ",12,20\n" );
		sb.append( "11, " + edgeUid + ",12,21\n" );		sb.append( "12, " + edgeUid + ",9,22\n" );
		List<Quad> list = null;
		try {
			list = Parsers.parseQuads( sb.toString() );
		}
		catch ( ParseException pe ) {
			System.out.flush();
			System.err.flush();
			System.err.println( "Exception/error parsing tuple data: " + pe.getMessage() );
			return new ArrayList<Quad>(); // Return empty List - not a null List
		}

		return list;
	}

	////////////////////////

	public static void testParseSmallBookGraph( PrintStream ps ) {
		ps.println( "------ testParseSmallBookGraph() ------" );
		ps.println();
		StringBuffer sb = new StringBuffer();
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		String edgeUid = sde.toString();
		sb.append( "0, " + edgeUid + ",1,1\n" );		sb.append( "0, " + edgeUid + ",5,2\n" );
		sb.append( "0, " + edgeUid + ",6,3\n" );		sb.append( "2, " + edgeUid + ",0,4\n" );
		sb.append( "2, " + edgeUid + ",3,5\n" );		sb.append( "3, " + edgeUid + ",2,6\n" );
		sb.append( "3, " + edgeUid + ",5,7\n" );		sb.append( "4, " + edgeUid + ",2,8\n" );
		sb.append( "4, " + edgeUid + ",3,9\n" );		sb.append( "4, " + edgeUid + ",11,10\n" );
		sb.append( "5, " + edgeUid + ",4,11\n" );		sb.append( "6, " + edgeUid + ",4,12\n" );
		sb.append( "6, " + edgeUid + ",9,13\n" );		sb.append( "7, " + edgeUid + ",6,14\n" );
		sb.append( "7, " + edgeUid + ",8,15\n" );		sb.append( "8, " + edgeUid + ",7,16\n" );
		sb.append( "8, " + edgeUid + ",9,17\n" );		sb.append( "9, " + edgeUid + ",10,18\n" );
		sb.append( "9, " + edgeUid + ",11,19\n" );		sb.append( "10, " + edgeUid + ",12,20\n" );
		sb.append( "11, " + edgeUid + ",12,21\n" );		sb.append( "12, " + edgeUid + ",9,22\n" );

		List<Quad> list = null;
		try {
			list = Parsers.parseQuads( sb.toString() );
		}
		catch ( ParseException pe ) {
			System.out.flush();
			System.err.flush();
			System.err.println( "Exception/error parsing tuple data: " + pe.getMessage() );
			ps.println();
			return;
		}

		for ( Quad q : list )
			ps.println( q );

		ps.println();
	}

	////////////////////////

	public static void testParse1MQuadsFast( PrintStream ps ) {
		ps.println( "------ testParse1MQuadsFast() ------" );
		ps.println();
		StringBuffer sb = new StringBuffer();
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		int numQuads = 1000000;
		for ( int i = 0; i < numQuads; ++i ) {
			sb.append( i + ", " + "sde" + ", " + ( i * 2 ) + ", " + ( i * 3 ) + "\n" );
		}
		long t1 = System.currentTimeMillis();

		List<Quad> list = null;
		try {
			list = Parsers.parseQuads( sb.toString() );
		}
		catch ( ParseException pe ) {
			System.err.println( "Error parsing tuple data: " + pe.getMessage() );
			return;
		}

		long t2 = System.currentTimeMillis();
		ps.println( "parsed " + numQuads + " quads in " + ( t2 - t1 ) + " ms" );
		
		ps.println();
	}

	////////////////////////

	public static void testParseSmallBookGraphWithExtraneousWhitespace( PrintStream ps ) {
		ps.println( "------ testParseSmallBookGraphWithExtraneousWhitespace() ------" );
		ps.println();
		StringBuffer sb = new StringBuffer();
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		String edgeUid = sde.toString();
		sb.append( " 0, sde , 1, 1 \n" );		sb.append( "0,sde,5,2\n" );
		sb.append( "0, " + edgeUid + ", 6,3\n" );		sb.append( "2,sde,0,4\n" );
		sb.append( "2, " + edgeUid + ", 3,5\n" );		sb.append( "3,sde,2,6\n" );
		sb.append( "3, " + edgeUid + ", 5,7\n" );		sb.append( "4,sde,2,8\n" );
		sb.append( "4, " + edgeUid + ", 3,9\n" );		sb.append( "4,sde,11,10\n" );
		sb.append( "5, " + edgeUid + ", 4,11\n" );		sb.append( "6,sde,4,12\n" );
		sb.append( "6, " + edgeUid + ", 9,13\n" );		sb.append( "7,sde,6,14\n" );
		sb.append( "7, " + edgeUid + ", 8,15\n" );		sb.append( "8,sde,7,16\n" );
		sb.append( "8, " + edgeUid + ", 9,17\n" );		sb.append( "9,sde,10,18\n" );
		sb.append( "9, " + edgeUid + ", 11,19\n" );		sb.append( "10,sde,12,20\n" );
		sb.append( "11, " + edgeUid + ",12,21\n" );		sb.append( "12,sde,9,22\n" );

		List<Quad> list = null;
		try {
			list = Parsers.parseQuads( sb.toString() );
		}
		catch ( ParseException pe ) {
			System.err.println( "Error parsing tuple data: " + pe.getMessage() );
			return;
		}
		for ( Quad q : list )
			ps.println( q );

		ps.println();
	}
	////////////////////////

	public static void testParseGraphWithMalformedQuad( PrintStream ps ) {
		ps.println( "------ testParseGraphWithMalformedQuad() ------" );
		ps.println();
		StringBuffer sb = new StringBuffer();
		SimpleDirectedEdge sde = new SimpleDirectedEdge( "sde" );
		String edgeUid = sde.toString();
		sb.append( " 0, sde , 1, 1 \n" );		sb.append( "0,sde,5,2\n" );
		sb.append( "0, " + edgeUid + ", 6,3\n" );		sb.append( "2,sde,0,4\n" );
		sb.append( "2, " + edgeUid + ", 3,5\n" );		sb.append( "3,sde,2,6\n" );
		sb.append( "3, " + edgeUid + ", 5,7\n" );		sb.append( "4,sde,2,8\n" );
		sb.append( "4, " + edgeUid + ", 3,9\n" );		sb.append( "4,sde,11,10\n" );
		sb.append( "5, " + edgeUid + ", 4,11\n" );		sb.append( "6,sde,4,12\n" );
		sb.append( "6, " + edgeUid + ", 9,13\n" );		sb.append( "7,sde,6,14\n" );
		sb.append( "7, " + edgeUid + ", 8,15\n" );		sb.append( "8,sde,7,16\n" );
		sb.append( "8, " + edgeUid + ", 9,17\n" );		sb.append( "9,sde,10,18\n" );
		sb.append( "9, " + edgeUid + ", 11,19\n" );		sb.append( "10,sde,12,20\n" );
		sb.append( "11, " + edgeUid + ",1221\n" );		sb.append( "12,sde,9,22\n" );

		List<Quad> list = null;
		try {
			list = Parsers.parseQuads( sb.toString() );
		}
		catch ( ParseException pe ) {
			ps.println( "Got expected error parsing tuple data: " + pe.getMessage() );
			ps.println();
			return;
		}
		for ( Quad q : list )
			ps.println( q );

		ps.println();
	}


}
