////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

import java.util.List;
import java.util.ArrayList;

public class AdjacencyList {

	private final QuadStore qs;

	private AdjacencyList() { qs = null; }

	public AdjacencyList ( QuadStore qs ) {
		this.qs = qs;
	}

	public List<String> getAdjacentNodes( String sUid ) {
		List<Quad> quadList = qs.getQuadsForSubj( sUid );
		List<String> retList = new ArrayList<String>();
		for ( Quad q : quadList )
			retList.add( q.oUid );
		return retList;
	}

}
