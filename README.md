## Synopsis

This repo contains a few (very) quickly-coded ideas on how to build an in-memory Quad Store.

A 'quad' is a 4-tuple: ( subject, predicate, object, uuid ) and is an augmentation of the W3C's RDF triple concept.

By adding a uuid, reification and unlimited levels of metadata are easily added to a graph.

This 4-tuple is intended for knowledge graphs and graph browsing applications.

This particular implementation, on a good machine, may be able to handle 100M quads, but I haven't tested this.  The idea of course is to have distributed Quad Stores on many computers enabling fast, parallel, graph query fulfillment and browsing.

DISCLAIMER: this was only a quick test - it is not not even close to production quality!
