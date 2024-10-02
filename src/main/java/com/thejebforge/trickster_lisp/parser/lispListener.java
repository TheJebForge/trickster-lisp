// Generated from D:/Projects/trickster-lisp/src/main/java/com/thejebforge/trickster_lisp/parser/lisp.g4 by ANTLR 4.13.1
package com.thejebforge.trickster_lisp.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link lispParser}.
 */
public interface lispListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link lispParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(lispParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(lispParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#sExpression}.
	 * @param ctx the parse tree
	 */
	void enterSExpression(lispParser.SExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#sExpression}.
	 * @param ctx the parse tree
	 */
	void exitSExpression(lispParser.SExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(lispParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(lispParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link lispParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(lispParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link lispParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(lispParser.ListContext ctx);
}