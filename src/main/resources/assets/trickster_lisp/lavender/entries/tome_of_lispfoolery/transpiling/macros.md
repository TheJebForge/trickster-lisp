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

Macros can then be called as following:


**(#def print (value) (reveal value))**

**(print! 123)**


Code above on conversion will unwrap into **Showcase Stratagem** with **123** as it's subcircle, or

**(reveal 123)**

if represented in LISP.

;;;;;

Macros can also catch indefinite amount of arguments if **Greedy Operator** is added to argument list, for example:

**(#def print (...) (reveal ...))**

**(print! 1 2 3)**


Code above on conversion will unwrap into:

**(reveal 1 2 3)**


When using this along with normal arguments, greedy operator must be the last in the argument list.

;;;;;

Macros are Transpiler only features, storing code that contains macros into a scroll,
will just apply the macros. 

**Loading code from a scroll will erase the macros!**


Macros can be stored to **Paper and Pencil**, or **Macro Ring**.

Trying to store code while holding a **macro ring** in offhand, will store macros onto it! 

;;;;;

You can see how many LISP macros are stored by hovering mouse over the item.


When macros are stored on a macro ring and equipped in **ring slot**, 
transpiler will be able to reverse any patterns it finds into macro calls!


So, if you want cleaner code when using macros, consider putting them on a macro ring
and letting transpiler reverse them on conversion.