////////////////////////
// Copyright (c) Richard Creamer 2011 - All Rights Reserved
////////////////////////

public class SimpleDirectedEdge {
	public final String uid;
	public SimpleDirectedEdge( String uid ) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return uid;
	}
}
