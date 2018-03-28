---
Title: peripheries
MainCategory: Cluster
Description: Set number of peripheries used in cluster boundaries.
Type: int
Default: 1
Min: 0
Restrictions: 
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

Note that user-defined shapes are treated as a form of box shape, so the default peripheries value is 1 and the user-defined shape will be drawn in a bounding rectangle. Setting peripheries=0 will turn this off. Also, 1 is the maximum peripheries value for clusters.
