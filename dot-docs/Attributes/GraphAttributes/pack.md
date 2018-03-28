---
Title: pack
MainCategory: Graph
Description: This is true if the value of pack is 'true' (case-insensitive) or a non-negative integer.
Type: bool,int
Default: FALSE
Min: 
Restrictions: 
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

If true, each connected component of the graph is laid out separately, and then the graphs are packed together. If pack has an integral value, this is used as the size, in points, of a margin around each part; otherwise, a default margin of 8 is used. If pack is interpreted as false, the entire graph is laid out together. The granularity and method of packing is influenced by the packmode attribute.
