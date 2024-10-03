```json
{
  "title": "Macros",
  "icon": "minecraft:flower_banner_pattern",
  "category": "trickster_lisp:transpiling",
  "ordinal": 2
}
```

Transpiler supports defining macros and using them in code.
They can be defined as following:


**(#def \<macroName\> (\<argumentNames\>) \<substituteExpr\>)**

;;;;;

Macros can then be called like normal functions, for example:


**(#def print (value) (reveal value))**

**(print 123)**


Code above on conversion unwrap into **Showcase Stratagem** with **123** as it's subcircle, or

**(reveal 123)**

if represented in LISP.

;;;;;

Macros are Transpiler only features, storing code that contains macros into a scroll,
will just apply the macros. 

**Loading code from a scroll will erase the macros!**


Macros can only be stored with **Paper and Pencil**.