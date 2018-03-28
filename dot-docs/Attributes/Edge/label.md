---
Title: label
MainCategory: Edge
Description: Text label attached to objects.
Type: lblString
Default: n/a
Min: 
Restrictions: 
Examples: 
LastEdit: 2018-03-28
LastEditor: generator
---

If a node's shape is record, then the label can have a special format which describes the record layout. Note that a node's default label is '\N', so the node's name or ID becomes its label. Technically, a node's name can be an HTML string but this will not mean that the node's label will be interpreted as an HTML-like label. This is because the node's actual label is an ordinary string, which will be replaced by the raw bytes stored in the node's name. To get an HTML-like label, the label attribute value itself must be an HTML string.
