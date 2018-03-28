---
Title: label_scheme
MainCategory: Graph
Description: The value indicates whether to treat a node whose name has the form |edgelabel|* as a special node representing an edge label.
Type: int
Default: 0
Min: 0
Restrictions: sfdp only
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

The default (0) produces no effect. If the attribute is set to 1, sfdp uses a penalty-based method to make that kind of node close to the center of its neighbor. With a value of 2, sfdp uses a penalty-based method to make that kind of node close to the old center of its neighbor. Finally, a value of 3 invokes a two-step process of overlap removal and straightening.
