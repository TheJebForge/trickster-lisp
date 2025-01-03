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
		T__9=10, EMPTY=11, VOID=12, BOOLEAN=13, INTEGER=14, FLOAT=15, STRING=16, 
		OPERATOR=17, IDENTIFIER=18, GREEDY=19, WS=20, LINE_COMMENT=21;
	public static final int
		RULE_root = 0, RULE_sExpression = 1, RULE_preprocessor = 2, RULE_macroCall = 3, 
		RULE_call = 4, RULE_list = 5, RULE_mapEntry = 6, RULE_map = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "sExpression", "preprocessor", "macroCall", "call", "list", "mapEntry", 
			"map"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'#def'", "')'", "'!'", "'['", "','", "']'", "':'", "'{'", 
			"'}'", "'_'", "'void'", null, null, null, null, null, null, "'...'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "EMPTY", 
			"VOID", "BOOLEAN", "INTEGER", "FLOAT", "STRING", "OPERATOR", "IDENTIFIER", 
			"GREEDY", "WS", "LINE_COMMENT"
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
			setState(19);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(16);
					preprocessor();
					}
					} 
				}
				setState(21);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1047074L) != 0)) {
				{
				{
				setState(22);
				sExpression();
				}
				}
				setState(27);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(28);
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
		public MacroCallContext macroCall() {
			return getRuleContext(MacroCallContext.class,0);
		}
		public TerminalNode GREEDY() { return getToken(lispParser.GREEDY, 0); }
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
			setState(43);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(30);
				match(EMPTY);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(31);
				match(VOID);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(32);
				match(BOOLEAN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(33);
				match(OPERATOR);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(34);
				match(INTEGER);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(35);
				match(FLOAT);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(36);
				match(STRING);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(37);
				match(IDENTIFIER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(38);
				macroCall();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(39);
				match(GREEDY);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(40);
				call();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(41);
				list();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(42);
				map();
				}
				break;
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
		public TerminalNode GREEDY() { return getToken(lispParser.GREEDY, 0); }
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
			setState(45);
			match(T__0);
			setState(46);
			match(T__1);
			setState(47);
			((MacroContext)_localctx).name = match(IDENTIFIER);
			setState(48);
			match(T__0);
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(49);
				((MacroContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				((MacroContext)_localctx).args.add(((MacroContext)_localctx).IDENTIFIER);
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GREEDY) {
				{
				setState(55);
				match(GREEDY);
				}
			}

			setState(58);
			match(T__2);
			setState(59);
			((MacroContext)_localctx).substitute = sExpression();
			setState(60);
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
	public static class MacroCallContext extends ParserRuleContext {
		public Token name;
		public SExpressionContext sExpression;
		public List<SExpressionContext> args = new ArrayList<SExpressionContext>();
		public TerminalNode IDENTIFIER() { return getToken(lispParser.IDENTIFIER, 0); }
		public List<SExpressionContext> sExpression() {
			return getRuleContexts(SExpressionContext.class);
		}
		public SExpressionContext sExpression(int i) {
			return getRuleContext(SExpressionContext.class,i);
		}
		public MacroCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macroCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).enterMacroCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof lispListener ) ((lispListener)listener).exitMacroCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof lispVisitor ) return ((lispVisitor<? extends T>)visitor).visitMacroCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroCallContext macroCall() throws RecognitionException {
		MacroCallContext _localctx = new MacroCallContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_macroCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__0);
			setState(63);
			((MacroCallContext)_localctx).name = match(IDENTIFIER);
			setState(64);
			match(T__3);
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1047074L) != 0)) {
				{
				{
				setState(65);
				((MacroCallContext)_localctx).sExpression = sExpression();
				((MacroCallContext)_localctx).args.add(((MacroCallContext)_localctx).sExpression);
				}
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(71);
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
		enterRule(_localctx, 8, RULE_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__0);
			setState(74);
			((CallContext)_localctx).subject = sExpression();
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1047074L) != 0)) {
				{
				{
				setState(75);
				((CallContext)_localctx).sExpression = sExpression();
				((CallContext)_localctx).args.add(((CallContext)_localctx).sExpression);
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81);
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
		enterRule(_localctx, 10, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(T__4);
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1047074L) != 0)) {
				{
				setState(84);
				sExpression();
				}
			}

			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(87);
				match(T__5);
				setState(88);
				sExpression();
				}
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(94);
			match(T__6);
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
		enterRule(_localctx, 12, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			((MapEntryContext)_localctx).key = sExpression();
			setState(97);
			match(T__7);
			setState(98);
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
		enterRule(_localctx, 14, RULE_map);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(T__8);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1047074L) != 0)) {
				{
				setState(101);
				mapEntry();
				}
			}

			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(104);
				match(T__5);
				setState(105);
				mapEntry();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			match(T__9);
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
		"\u0004\u0001\u0015r\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0005\u0000\u0012\b\u0000\n\u0000\f\u0000\u0015\t\u0000\u0001\u0000"+
		"\u0005\u0000\u0018\b\u0000\n\u0000\f\u0000\u001b\t\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0003\u0001,\b\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0005\u00023\b\u0002\n\u0002\f\u00026\t"+
		"\u0002\u0001\u0002\u0003\u00029\b\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005"+
		"\u0003C\b\u0003\n\u0003\f\u0003F\t\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0005\u0004M\b\u0004\n\u0004\f\u0004P\t"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0003\u0005V\b"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005Z\b\u0005\n\u0005\f\u0005]\t"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0003\u0007g\b\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007k\b\u0007\n\u0007\f\u0007n\t\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0000"+
		"\u0000\u007f\u0000\u0013\u0001\u0000\u0000\u0000\u0002+\u0001\u0000\u0000"+
		"\u0000\u0004-\u0001\u0000\u0000\u0000\u0006>\u0001\u0000\u0000\u0000\b"+
		"I\u0001\u0000\u0000\u0000\nS\u0001\u0000\u0000\u0000\f`\u0001\u0000\u0000"+
		"\u0000\u000ed\u0001\u0000\u0000\u0000\u0010\u0012\u0003\u0004\u0002\u0000"+
		"\u0011\u0010\u0001\u0000\u0000\u0000\u0012\u0015\u0001\u0000\u0000\u0000"+
		"\u0013\u0011\u0001\u0000\u0000\u0000\u0013\u0014\u0001\u0000\u0000\u0000"+
		"\u0014\u0019\u0001\u0000\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000"+
		"\u0016\u0018\u0003\u0002\u0001\u0000\u0017\u0016\u0001\u0000\u0000\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u001c\u0001\u0000\u0000\u0000"+
		"\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u001d\u0005\u0000\u0000\u0001"+
		"\u001d\u0001\u0001\u0000\u0000\u0000\u001e,\u0005\u000b\u0000\u0000\u001f"+
		",\u0005\f\u0000\u0000 ,\u0005\r\u0000\u0000!,\u0005\u0011\u0000\u0000"+
		"\",\u0005\u000e\u0000\u0000#,\u0005\u000f\u0000\u0000$,\u0005\u0010\u0000"+
		"\u0000%,\u0005\u0012\u0000\u0000&,\u0003\u0006\u0003\u0000\',\u0005\u0013"+
		"\u0000\u0000(,\u0003\b\u0004\u0000),\u0003\n\u0005\u0000*,\u0003\u000e"+
		"\u0007\u0000+\u001e\u0001\u0000\u0000\u0000+\u001f\u0001\u0000\u0000\u0000"+
		"+ \u0001\u0000\u0000\u0000+!\u0001\u0000\u0000\u0000+\"\u0001\u0000\u0000"+
		"\u0000+#\u0001\u0000\u0000\u0000+$\u0001\u0000\u0000\u0000+%\u0001\u0000"+
		"\u0000\u0000+&\u0001\u0000\u0000\u0000+\'\u0001\u0000\u0000\u0000+(\u0001"+
		"\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+*\u0001\u0000\u0000\u0000"+
		",\u0003\u0001\u0000\u0000\u0000-.\u0005\u0001\u0000\u0000./\u0005\u0002"+
		"\u0000\u0000/0\u0005\u0012\u0000\u000004\u0005\u0001\u0000\u000013\u0005"+
		"\u0012\u0000\u000021\u0001\u0000\u0000\u000036\u0001\u0000\u0000\u0000"+
		"42\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u000058\u0001\u0000\u0000"+
		"\u000064\u0001\u0000\u0000\u000079\u0005\u0013\u0000\u000087\u0001\u0000"+
		"\u0000\u000089\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:;\u0005"+
		"\u0003\u0000\u0000;<\u0003\u0002\u0001\u0000<=\u0005\u0003\u0000\u0000"+
		"=\u0005\u0001\u0000\u0000\u0000>?\u0005\u0001\u0000\u0000?@\u0005\u0012"+
		"\u0000\u0000@D\u0005\u0004\u0000\u0000AC\u0003\u0002\u0001\u0000BA\u0001"+
		"\u0000\u0000\u0000CF\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000"+
		"DE\u0001\u0000\u0000\u0000EG\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000"+
		"\u0000GH\u0005\u0003\u0000\u0000H\u0007\u0001\u0000\u0000\u0000IJ\u0005"+
		"\u0001\u0000\u0000JN\u0003\u0002\u0001\u0000KM\u0003\u0002\u0001\u0000"+
		"LK\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000"+
		"\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000PN\u0001\u0000"+
		"\u0000\u0000QR\u0005\u0003\u0000\u0000R\t\u0001\u0000\u0000\u0000SU\u0005"+
		"\u0005\u0000\u0000TV\u0003\u0002\u0001\u0000UT\u0001\u0000\u0000\u0000"+
		"UV\u0001\u0000\u0000\u0000V[\u0001\u0000\u0000\u0000WX\u0005\u0006\u0000"+
		"\u0000XZ\u0003\u0002\u0001\u0000YW\u0001\u0000\u0000\u0000Z]\u0001\u0000"+
		"\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\^\u0001"+
		"\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^_\u0005\u0007\u0000\u0000"+
		"_\u000b\u0001\u0000\u0000\u0000`a\u0003\u0002\u0001\u0000ab\u0005\b\u0000"+
		"\u0000bc\u0003\u0002\u0001\u0000c\r\u0001\u0000\u0000\u0000df\u0005\t"+
		"\u0000\u0000eg\u0003\f\u0006\u0000fe\u0001\u0000\u0000\u0000fg\u0001\u0000"+
		"\u0000\u0000gl\u0001\u0000\u0000\u0000hi\u0005\u0006\u0000\u0000ik\u0003"+
		"\f\u0006\u0000jh\u0001\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lj\u0001"+
		"\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mo\u0001\u0000\u0000\u0000"+
		"nl\u0001\u0000\u0000\u0000op\u0005\n\u0000\u0000p\u000f\u0001\u0000\u0000"+
		"\u0000\u000b\u0013\u0019+48DNU[fl";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}