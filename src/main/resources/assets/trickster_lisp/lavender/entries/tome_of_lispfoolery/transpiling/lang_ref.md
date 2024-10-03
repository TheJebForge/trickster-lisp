```json
{
  "title": "Language Reference",
  "icon": "minecraft:book",
  "category": "trickster_lisp:transpiling",
  "ordinal": 1
}
```

Language this mod uses is very similar to LISP, with some differences.


At the heart of everything is an **Identifier** or **ID**, it's a name that maps to a trick in Trickster.


For instance, **caster_reflection** will map to **Reflection Delusion** in Trickster.

;;;;;

Just putting an **ID** of the trick into code editor and then storing that onto a scroll, 
will give you pattern of the trick. 

This can also be explained as an **unargumented call**,
or a function call that doesn't have any arguments.


**Argumented calls** can be performed by wrapping **ID** in parentheses 
and then specifying arguments separated by space.

;;;;;

You can check **IDs** of tricks in Tom of Tomfoolery, they're written under each trick.


There's also a bunch of new functions you can use to create different trickster fragments:


**Block Fragments** can be represented with **(block_type "\<id\>")**.


**Boolean Fragments** can be represented with **true** or **false**.

;;;;;

**Dimension Fragments** can be represented with **(dimension "\<id\>")**.


**Entity Fragments** can be represented with 

**(entity "\<uuid\>")**.


**Entity Type Fragments** can be represented with **(entity_type "\<id\>")**.


**Item Type Fragments** can be represented with **(item_type "\<id\>")**.

;;;;;

**List Fragments** can be represented with square brackets and elements separated with commas, 

like **[ 1, 2, 3 ]**.


**Number Fragments** can be represented with integers and floats, like **123** or **1.23**.


**Pattern Fragments** can be represented with 

**(pattern \<int\>)**, 

don't ask me how to obtain those...

;;;;;

**Slot Fragments** can be represented with 

**(slot \<slotIndex\>)**,

or 

**(slot \<slotIndex\> \<vec\>)**.


**Type Fragments** can be represented with 

**(type "\<fragmentType\>")**.


**Vector Fragments** can be represented with

**(vec \<x\> \<y\> \<z\>)**.


**Voids** or **Empty Circles** can be represented with **void**.

;;;;;

There are some special functions that the transpiler understands:


**(get_glyph \<expression\>)** will on conversion take the pattern of whatever expression you give it.

So **(get_glyph reveal)** will give you pattern fragment of **Showcase Stratagem** trick.


**(arg \<1-8\>)** will provide **Primary Delusion**, **Secondary Delusion** and so on.

;;;;;

**(if \<condition\> \<trueCase\> \<falseCase\>)** will unwrap into **Quiet Distortion** with **Decision Distortion**.
In LISP it can be represented as following:

**(execute_same_scope (if_else \<condition\> ((\<trueCase\>)) ((\<false case\>))))**


Try seeing what happens when you store any of those fragment representations into a scroll!