---
Title: inputscale
MainCategory: Graph
Description: For layout algorithms that support initial input positions (specified by the pos attribute), this attribute can be used to appropriately scale the values.
Type: double
Default: n/a
Min: 
Restrictions: fdp, neato only
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

By default, fdp and neato interpret the x and y values of pos as being in inches. (NOTE: neato -n(2) treats the coordinates as being in points, being the unit used by the layout algorithms for the pos attribute.) Thus, if the graph has pos attributes in points, one should set inputscale=72. This can also be set on the command line using the -s flag flag.
