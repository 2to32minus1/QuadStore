////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

public class HashQuadStore implements QuadStore {

	private int count = 0;
	private Map<String, Set<String>> subjIndex = new ConcurrentHashMap<String, Set<String>>();
	private Map<String, Set<String>> predIndex = new ConcurrentHashMap<String, Set<String>>();
	private Map<String, Set<String>> objIndex = new ConcurrentHashMap<String, Set<String>>();
	private Map<String, Set<String>> literalIndex = new ConcurrentHashMap<String, Set<String>>();
	private Map<String, Quad> uidIndex = new ConcurrentHashMap<String, Quad>();
	
	public HashQuadStore() {
	}

	////////////////////////

	public boolean addQuad( Quad q ) {

		if ( uidIndex.get( q.uid ) != null ) {
			return false;
		}

		// Store Quad's subject UID into the subjIndex

		Set<String> s = subjIndex.get( q.sUid );
		if ( s == null ) {
			s = new HashSet<String>();
			subjIndex.put( q.sUid, s );
		}
		s.add( q.uid );

		// Store Quad's predicate UID into the predIndex

		s = predIndex.get( q.pUid );
		if ( s == null ) {
			s = new HashSet<String>();
			predIndex.put( q.pUid, s );
		}
		s.add( q.uid );

		// Store Quad's object UID into the objIndex only if object is not a literal value

		if ( !q.oIsLiteral ) {
			s = objIndex.get( q.oUid );
			if ( s == null ) {
				s = new HashSet<String >();
				objIndex.put( q.oUid, s );
			}
			s.add( q.uid );
		}

		// Store Quad's literal value (if is a literal value) in literalIndex only if object IS a literal value

		if ( q.oIsLiteral ) {
			s = literalIndex.get( q.oUid ); // Literals are stored in Quad.oUid
			if ( s == null ) {
				s = new HashSet<String>();
				literalIndex.put( q.oUid, s );
			}
			s.add( q.uid );
		}

		// Store the Quad in the Quad uidIndex

		uidIndex.put( q.uid, q );

		++count;
		return true; // Only returns true if argument not already in QuadStore
	}

	////////////////////////

	public int getCount() { return count; }

	////////////////////////

	public List<Quad> getAllQuads() {
		List<Quad> retList = new ArrayList<Quad>( getCount() );
		retList.addAll(  uidIndex.values() );
		return retList;
	}

	////////////////////////

	public Quad getQuad(String quadUid) {
		return uidIndex.get( quadUid );
	}

	////////////////////////

	public boolean containsQuad( String uid ) {
		return uidIndex.containsKey( uid );
	}

	////////////////////////

	public List<Quad> getQuadsForSubj( String s ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> quadUids = subjIndex.get( s );
		if ( quadUids != null ) {
			Iterator<String> iter = quadUids.iterator();
			while ( iter.hasNext() ) {
				Quad q = uidIndex.get( iter.next() );
				if ( q != null )
					retList.add( q );
			}
		}
		return retList;
	}

	////////////////////////

	public List<Quad> getQuadsForPred( String p ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> quadUids = predIndex.get( p );
		if ( quadUids != null ) {
			Iterator<String> iter = quadUids.iterator();
			while ( iter.hasNext() ) {
				Quad q = uidIndex.get( iter.next() );
				if ( q != null )
					retList.add( q );
			}
		}
		return retList;
	}

	////////////////////////

	public List<Quad> getQuadsForObj( String o ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> quadUids = objIndex.get( o );
		if ( quadUids != null ) {
			Iterator<String> iter = quadUids.iterator();
			while ( iter.hasNext() ) {
				Quad q = uidIndex.get( iter.next() );
				if ( q != null )
					retList.add( q );
			}
		}
		return retList;
	}

	////////////////////////

	public List<Quad> getQuadsForSubjAndPred( String s, String p ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> subjQuadUids = subjIndex.get( s );
		if ( subjQuadUids != null ) {
			Set<String> predQuadUids = predIndex.get( p );
			if ( predQuadUids != null ) {
				subjQuadUids.retainAll( predQuadUids );
				Iterator<String> iter = subjQuadUids.iterator(); // Now contains UIDs common to both Subj and Pred sets
				while ( iter.hasNext() ) {
					Quad q = uidIndex.get( iter.next() );
					if ( q != null )
						retList.add( q );
				}
			}
		}
		return retList;
	}

	////////////////////////

	public List<Quad> getQuadsForPredAndObj( String p, String o ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> predQuadUids = predIndex.get( p );
		if ( predQuadUids != null ) {
			Set<String> objQuadUids = objIndex.get( o );
			if ( objQuadUids != null ) {
				objQuadUids.retainAll( predQuadUids );
				Iterator<String> iter = objQuadUids.iterator(); // Now contains UIDs common to both Obj and Pred sets
				while ( iter.hasNext() ) {
					Quad q = uidIndex.get( iter.next() );
					if ( q != null )
						retList.add( q );
				}
			}
		}
		return retList;
	}

	////////////////////////
	
	public List<Quad> getQuadsForLiteral( String literalVal ) {
		List<Quad> retList = new ArrayList<Quad>();
		Set<String> quadUids = literalIndex.get( literalVal );
		if ( quadUids != null ) {
			Iterator<String> iter = quadUids.iterator();
			while ( iter.hasNext() ) {
				Quad q = uidIndex.get( iter.next() );
				if ( q != null )
					retList.add( q );
			}
		}
		return retList;
	}

	
	////////////////////////

	public List<String> getObjectUidsForSubjAndPred( String sUid, String pUid ) {
		List<String> retList = new ArrayList<String>();
		List<Quad> quadList = getQuadsForSubjAndPred( sUid, pUid );
		for  ( Quad q : quadList )
			retList.add( q.oUid );
		return retList;
	}


}
