// Generated from D:/Projects/trickster-lisp/src/main/java/com/thejebforge/trickster_lisp/parser/lisp.g4 by ANTLR 4.13.2
package com.thejebforge.trickster_lisp.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link lispParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface lispVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link lispParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(lispParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#sExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSExpression(lispParser.SExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code macro}
	 * labeled alternative in {@link lispParser#preprocessor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro(lispParser.MacroContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#macroCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroCall(lispParser.MacroCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(lispParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(lispParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#mapEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapEntry(lispParser.MapEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link lispParser#map}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMap(lispParser.MapContext ctx);
}