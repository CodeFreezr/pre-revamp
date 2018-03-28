---
Title: normalize
MainCategory: Graph
Description: If set, normalize coordinates of final layout so that the first point is at the origin, and then rotate the layout so that the angle of the first edge is specified by the value of normalize in degrees.
Type: double,bool
Default: FALSE
Min: 
Restrictions: not dot
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

If normalize is not a number, it is evaluated as a bool, with true corresponding to 0 degrees. NOTE: Since the attribute is evaluated first as a number, 0 and 1 cannot be used for false and true.
