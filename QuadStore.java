////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.util.List;

public interface QuadStore {
	public boolean addQuad( Quad q );
	public List<Quad> getAllQuads();
	public Quad getQuad( String quadUid );
	public boolean containsQuad( String quadUid );
	public List<Quad> getQuadsForSubj( String s );
	public List<Quad> getQuadsForPred( String p );
	public List<Quad> getQuadsForObj( String o );
	public List<Quad> getQuadsForSubjAndPred( String s, String p );
	public List<Quad> getQuadsForPredAndObj( String p, String o );
	public List<Quad> getQuadsForLiteral( String litVal );
	public List<String> getObjectUidsForSubjAndPred( String sUid, String pUid );
	public int getCount();
}
