// Generated from D:/Projects/trickster-lisp/src/main/java/com/thejebforge/trickster_lisp/parser/lisp.g4 by ANTLR 4.13.2
package com.thejebforge.trickster_lisp.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class lispParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		EMPTY=10, VOID=11, BOOLEAN=12, INTEGER=13, FLOAT=14, STRING=15, OPERATOR=16, 
		IDENTIFIER=17, WS=18;
	public static final int
		RULE_root = 0, RULE_sExpression = 1, RULE_preprocessor = 2, RULE_call = 3, 
		RULE_list = 4, RULE_mapEntry = 5, RULE_map = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "sExpression", "preprocessor", "call", "list", "mapEntry", "map"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'#def'", "')'", "'['", "','", "']'", "':'", "'{'", "'}'", 
			"'_'", "'void'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "EMPTY", 
			"VOID", "BOOLEAN", "INTEGER", "FLOAT", "STRING", "OPERATOR", "IDENTIFIER", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "lisp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public lispParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RootContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(lispParser.EOF, 0); }
		public List<PreprocessorContext> preprocessor() {
			return getRuleContexts(PreprocessorContext.class);
		}
		public PreprocessorContext preprocessor(int i) {
			return getRuleContext(PreprocessorContext.class,i);
		}
		public List<SExpressionContext> sExpression() {
			return getRuleContexts(SExpressionContext.class);
		}
		public SExpressionContext sExpression(int i) {
			return getRuleContext(SExpressionContext.class,i);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitRoot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(14);
					preprocessor();
					}
					} 
				}
				setState(19);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 261394L) != 0)) {
				{
				{
				setState(20);
				sExpression();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SExpressionContext extends ParserRuleContext {
		public TerminalNode EMPTY() { return getToken(lispParser.EMPTY, 0); }
		public TerminalNode VOID() { return getToken(lispParser.VOID, 0); }
		public TerminalNode BOOLEAN() { return getToken(lispParser.BOOLEAN, 0); }
		public TerminalNode OPERATOR() { return getToken(lispParser.OPERATOR, 0); }
		public TerminalNode INTEGER() { return getToken(lispParser.INTEGER, 0); }
		public TerminalNode FLOAT() { return getToken(lispParser.FLOAT, 0); }
		public TerminalNode STRING() { return getToken(lispParser.STRING, 0); }
		public TerminalNode IDENTIFIER() { return getToken(lispParser.IDENTIFIER, 0); }
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public MapContext map() {
			return getRuleContext(MapContext.class,0);
		}
		public SExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterSExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitSExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitSExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SExpressionContext sExpression() throws RecognitionException {
		SExpressionContext _localctx = new SExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sExpression);
		try {
			setState(39);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EMPTY:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				match(EMPTY);
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				match(VOID);
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 3);
				{
				setState(30);
				match(BOOLEAN);
				}
				break;
			case OPERATOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(31);
				match(OPERATOR);
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 5);
				{
				setState(32);
				match(INTEGER);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 6);
				{
				setState(33);
				match(FLOAT);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 7);
				{
				setState(34);
				match(STRING);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 8);
				{
				setState(35);
				match(IDENTIFIER);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 9);
				{
				setState(36);
				call();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 10);
				{
				setState(37);
				list();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 11);
				{
				setState(38);
				map();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PreprocessorContext extends ParserRuleContext {
		public PreprocessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preprocessor; }
	 
		public PreprocessorContext() { }
		public void copyFrom(PreprocessorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MacroContext extends PreprocessorContext {
		public Token name;
		public Token IDENTIFIER;
		public List<Token> args = new ArrayList<Token>();
		public SExpressionContext substitute;
		public List<TerminalNode> IDENTIFIER() { return getTokens(lispParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(lispParser.IDENTIFIER, i);
		}
		public SExpressionContext sExpression() {
			return getRuleContext(SExpressionContext.class,0);
		}
		public MacroContext(PreprocessorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PreprocessorContext preprocessor() throws RecognitionException {
		PreprocessorContext _localctx = new PreprocessorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_preprocessor);
		int _la;
		try {
			_localctx = new MacroContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			match(T__0);
			setState(42);
			match(T__1);
			setState(43);
			((MacroContext)_localctx).name = match(IDENTIFIER);
			setState(44);
			match(T__0);
			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(45);
				((MacroContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				((MacroContext)_localctx).args.add(((MacroContext)_localctx).IDENTIFIER);
				}
				}
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			match(T__2);
			setState(52);
			((MacroContext)_localctx).substitute = sExpression();
			setState(53);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallContext extends ParserRuleContext {
		public SExpressionContext subject;
		public SExpressionContext sExpression;
		public List<SExpressionContext> args = new ArrayList<SExpressionContext>();
		public List<SExpressionContext> sExpression() {
			return getRuleContexts(SExpressionContext.class);
		}
		public SExpressionContext sExpression(int i) {
			return getRuleContext(SExpressionContext.class,i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(T__0);
			setState(56);
			((CallContext)_localctx).subject = sExpression();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 261394L) != 0)) {
				{
				{
				setState(57);
				((CallContext)_localctx).sExpression = sExpression();
				((CallContext)_localctx).args.add(((CallContext)_localctx).sExpression);
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListContext extends ParserRuleContext {
		public List<SExpressionContext> sExpression() {
			return getRuleContexts(SExpressionContext.class);
		}
		public SExpressionContext sExpression(int i) {
			return getRuleContext(SExpressionContext.class,i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(T__3);
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 261394L) != 0)) {
				{
				setState(66);
				sExpression();
				}
			}

			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(69);
				match(T__4);
				setState(70);
				sExpression();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapEntryContext extends ParserRuleContext {
		public SExpressionContext key;
		public SExpressionContext value;
		public List<SExpressionContext> sExpression() {
			return getRuleContexts(SExpressionContext.class);
		}
		public SExpressionContext sExpression(int i) {
			return getRuleContext(SExpressionContext.class,i);
		}
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterMapEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitMapEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitMapEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			((MapEntryContext)_localctx).key = sExpression();
			setState(79);
			match(T__6);
			setState(80);
			((MapEntryContext)_localctx).value = sExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MapContext extends ParserRuleContext {
		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}
		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}
		public MapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_map; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterMap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitMap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitMap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapContext map() throws RecognitionException {
		MapContext _localctx = new MapContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_map);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(T__7);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 261394L) != 0)) {
				{
				setState(83);
				mapEntry();
				}
			}

			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(86);
				match(T__4);
				setState(87);
				mapEntry();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0012`\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0005\u0000\u0010"+
		"\b\u0000\n\u0000\f\u0000\u0013\t\u0000\u0001\u0000\u0005\u0000\u0016\b"+
		"\u0000\n\u0000\f\u0000\u0019\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001(\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"/\b\u0002\n\u0002\f\u00022\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003;\b\u0003"+
		"\n\u0003\f\u0003>\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0003\u0004D\b\u0004\u0001\u0004\u0001\u0004\u0005\u0004H\b\u0004\n\u0004"+
		"\f\u0004K\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0003\u0006U\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006Y\b\u0006\n\u0006\f\u0006\\\t\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0000\u0000\u0007\u0000\u0002\u0004\u0006"+
		"\b\n\f\u0000\u0000j\u0000\u0011\u0001\u0000\u0000\u0000\u0002\'\u0001"+
		"\u0000\u0000\u0000\u0004)\u0001\u0000\u0000\u0000\u00067\u0001\u0000\u0000"+
		"\u0000\bA\u0001\u0000\u0000\u0000\nN\u0001\u0000\u0000\u0000\fR\u0001"+
		"\u0000\u0000\u0000\u000e\u0010\u0003\u0004\u0002\u0000\u000f\u000e\u0001"+
		"\u0000\u0000\u0000\u0010\u0013\u0001\u0000\u0000\u0000\u0011\u000f\u0001"+
		"\u0000\u0000\u0000\u0011\u0012\u0001\u0000\u0000\u0000\u0012\u0017\u0001"+
		"\u0000\u0000\u0000\u0013\u0011\u0001\u0000\u0000\u0000\u0014\u0016\u0003"+
		"\u0002\u0001\u0000\u0015\u0014\u0001\u0000\u0000\u0000\u0016\u0019\u0001"+
		"\u0000\u0000\u0000\u0017\u0015\u0001\u0000\u0000\u0000\u0017\u0018\u0001"+
		"\u0000\u0000\u0000\u0018\u001a\u0001\u0000\u0000\u0000\u0019\u0017\u0001"+
		"\u0000\u0000\u0000\u001a\u001b\u0005\u0000\u0000\u0001\u001b\u0001\u0001"+
		"\u0000\u0000\u0000\u001c(\u0005\n\u0000\u0000\u001d(\u0005\u000b\u0000"+
		"\u0000\u001e(\u0005\f\u0000\u0000\u001f(\u0005\u0010\u0000\u0000 (\u0005"+
		"\r\u0000\u0000!(\u0005\u000e\u0000\u0000\"(\u0005\u000f\u0000\u0000#("+
		"\u0005\u0011\u0000\u0000$(\u0003\u0006\u0003\u0000%(\u0003\b\u0004\u0000"+
		"&(\u0003\f\u0006\u0000\'\u001c\u0001\u0000\u0000\u0000\'\u001d\u0001\u0000"+
		"\u0000\u0000\'\u001e\u0001\u0000\u0000\u0000\'\u001f\u0001\u0000\u0000"+
		"\u0000\' \u0001\u0000\u0000\u0000\'!\u0001\u0000\u0000\u0000\'\"\u0001"+
		"\u0000\u0000\u0000\'#\u0001\u0000\u0000\u0000\'$\u0001\u0000\u0000\u0000"+
		"\'%\u0001\u0000\u0000\u0000\'&\u0001\u0000\u0000\u0000(\u0003\u0001\u0000"+
		"\u0000\u0000)*\u0005\u0001\u0000\u0000*+\u0005\u0002\u0000\u0000+,\u0005"+
		"\u0011\u0000\u0000,0\u0005\u0001\u0000\u0000-/\u0005\u0011\u0000\u0000"+
		".-\u0001\u0000\u0000\u0000/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000"+
		"\u000001\u0001\u0000\u0000\u000013\u0001\u0000\u0000\u000020\u0001\u0000"+
		"\u0000\u000034\u0005\u0003\u0000\u000045\u0003\u0002\u0001\u000056\u0005"+
		"\u0003\u0000\u00006\u0005\u0001\u0000\u0000\u000078\u0005\u0001\u0000"+
		"\u00008<\u0003\u0002\u0001\u00009;\u0003\u0002\u0001\u0000:9\u0001\u0000"+
		"\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001"+
		"\u0000\u0000\u0000=?\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000"+
		"?@\u0005\u0003\u0000\u0000@\u0007\u0001\u0000\u0000\u0000AC\u0005\u0004"+
		"\u0000\u0000BD\u0003\u0002\u0001\u0000CB\u0001\u0000\u0000\u0000CD\u0001"+
		"\u0000\u0000\u0000DI\u0001\u0000\u0000\u0000EF\u0005\u0005\u0000\u0000"+
		"FH\u0003\u0002\u0001\u0000GE\u0001\u0000\u0000\u0000HK\u0001\u0000\u0000"+
		"\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000JL\u0001\u0000"+
		"\u0000\u0000KI\u0001\u0000\u0000\u0000LM\u0005\u0006\u0000\u0000M\t\u0001"+
		"\u0000\u0000\u0000NO\u0003\u0002\u0001\u0000OP\u0005\u0007\u0000\u0000"+
		"PQ\u0003\u0002\u0001\u0000Q\u000b\u0001\u0000\u0000\u0000RT\u0005\b\u0000"+
		"\u0000SU\u0003\n\u0005\u0000TS\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000"+
		"\u0000UZ\u0001\u0000\u0000\u0000VW\u0005\u0005\u0000\u0000WY\u0003\n\u0005"+
		"\u0000XV\u0001\u0000\u0000\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001\u0000"+
		"\u0000\u0000Z[\u0001\u0000\u0000\u0000[]\u0001\u0000\u0000\u0000\\Z\u0001"+
		"\u0000\u0000\u0000]^\u0005\t\u0000\u0000^\r\u0001\u0000\u0000\u0000\t"+
		"\u0011\u0017\'0<CITZ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}