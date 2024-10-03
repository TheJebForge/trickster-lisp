// Generated from D:/Projects/trickster-lisp/src/main/java/com/thejebforge/trickster_lisp/parser/lisp.g4 by ANTLR 4.13.1
package com.thejebforge.trickster_lisp.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class lispLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, VOID=7, BOOLEAN=8, INTEGER=9, 
		FLOAT=10, STRING=11, OPERATOR=12, IDENTIFIER=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "VOID", "BOOLEAN", "INTEGER", 
			"FLOAT", "STRING", "OPERATOR", "IDENTIFIER", "EXPONENT", "HEX_DIGIT", 
			"ESC_SEQ", "OCTAL_ESC", "UNICODE_ESC", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "'#def'", "')'", "'['", "','", "']'", "'void'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "VOID", "BOOLEAN", "INTEGER", 
			"FLOAT", "STRING", "OPERATOR", "IDENTIFIER", "WS"
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


	public lispLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "lisp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000e\u00ab\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007E\b\u0007\u0001\b\u0004\bH\b\b\u000b\b\f\bI\u0001"+
		"\t\u0004\tM\b\t\u000b\t\f\tN\u0001\t\u0001\t\u0005\tS\b\t\n\t\f\tV\t\t"+
		"\u0001\t\u0003\tY\b\t\u0001\t\u0001\t\u0004\t]\b\t\u000b\t\f\t^\u0001"+
		"\t\u0003\tb\b\t\u0001\t\u0004\te\b\t\u000b\t\f\tf\u0001\t\u0003\tj\b\t"+
		"\u0001\n\u0001\n\u0001\n\u0005\no\b\n\n\n\f\nr\t\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0004\u000bw\b\u000b\u000b\u000b\f\u000bx\u0001\f\u0001\f\u0005"+
		"\f}\b\f\n\f\f\f\u0080\t\f\u0001\r\u0001\r\u0003\r\u0084\b\r\u0001\r\u0004"+
		"\r\u0087\b\r\u000b\r\f\r\u0088\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0091\b\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0003\u0010\u009c\b\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0004"+
		"\u0012\u00a6\b\u0012\u000b\u0012\f\u0012\u00a7\u0001\u0012\u0001\u0012"+
		"\u0000\u0000\u0013\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005"+
		"\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019"+
		"\r\u001b\u0000\u001d\u0000\u001f\u0000!\u0000#\u0000%\u000e\u0001\u0000"+
		"\n\u0001\u000009\u0002\u0000\"\"\\\\\t\u0000!!#&*+--//::<@^^||\u0002\u0000"+
		"AZaz\u0005\u0000--0:AZ__az\u0002\u0000EEee\u0002\u0000++--\u0003\u0000"+
		"09AFaf\b\u0000\"\"\'\'\\\\bbffnnrrtt\u0003\u0000\t\n\r\r  \u00ba\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0001\'\u0001"+
		"\u0000\u0000\u0000\u0003)\u0001\u0000\u0000\u0000\u0005.\u0001\u0000\u0000"+
		"\u0000\u00070\u0001\u0000\u0000\u0000\t2\u0001\u0000\u0000\u0000\u000b"+
		"4\u0001\u0000\u0000\u0000\r6\u0001\u0000\u0000\u0000\u000fD\u0001\u0000"+
		"\u0000\u0000\u0011G\u0001\u0000\u0000\u0000\u0013i\u0001\u0000\u0000\u0000"+
		"\u0015k\u0001\u0000\u0000\u0000\u0017v\u0001\u0000\u0000\u0000\u0019z"+
		"\u0001\u0000\u0000\u0000\u001b\u0081\u0001\u0000\u0000\u0000\u001d\u008a"+
		"\u0001\u0000\u0000\u0000\u001f\u0090\u0001\u0000\u0000\u0000!\u009b\u0001"+
		"\u0000\u0000\u0000#\u009d\u0001\u0000\u0000\u0000%\u00a5\u0001\u0000\u0000"+
		"\u0000\'(\u0005(\u0000\u0000(\u0002\u0001\u0000\u0000\u0000)*\u0005#\u0000"+
		"\u0000*+\u0005d\u0000\u0000+,\u0005e\u0000\u0000,-\u0005f\u0000\u0000"+
		"-\u0004\u0001\u0000\u0000\u0000./\u0005)\u0000\u0000/\u0006\u0001\u0000"+
		"\u0000\u000001\u0005[\u0000\u00001\b\u0001\u0000\u0000\u000023\u0005,"+
		"\u0000\u00003\n\u0001\u0000\u0000\u000045\u0005]\u0000\u00005\f\u0001"+
		"\u0000\u0000\u000067\u0005v\u0000\u000078\u0005o\u0000\u000089\u0005i"+
		"\u0000\u00009:\u0005d\u0000\u0000:\u000e\u0001\u0000\u0000\u0000;<\u0005"+
		"t\u0000\u0000<=\u0005r\u0000\u0000=>\u0005u\u0000\u0000>E\u0005e\u0000"+
		"\u0000?@\u0005f\u0000\u0000@A\u0005a\u0000\u0000AB\u0005l\u0000\u0000"+
		"BC\u0005s\u0000\u0000CE\u0005e\u0000\u0000D;\u0001\u0000\u0000\u0000D"+
		"?\u0001\u0000\u0000\u0000E\u0010\u0001\u0000\u0000\u0000FH\u0007\u0000"+
		"\u0000\u0000GF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000IG\u0001"+
		"\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000J\u0012\u0001\u0000\u0000"+
		"\u0000KM\u000209\u0000LK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000"+
		"NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000"+
		"\u0000PT\u0005.\u0000\u0000QS\u000209\u0000RQ\u0001\u0000\u0000\u0000"+
		"SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000"+
		"\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000WY\u0003\u001b"+
		"\r\u0000XW\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000Yj\u0001\u0000"+
		"\u0000\u0000Z\\\u0005.\u0000\u0000[]\u000209\u0000\\[\u0001\u0000\u0000"+
		"\u0000]^\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000^_\u0001\u0000"+
		"\u0000\u0000_a\u0001\u0000\u0000\u0000`b\u0003\u001b\r\u0000a`\u0001\u0000"+
		"\u0000\u0000ab\u0001\u0000\u0000\u0000bj\u0001\u0000\u0000\u0000ce\u0002"+
		"09\u0000dc\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fd\u0001\u0000"+
		"\u0000\u0000fg\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000hj\u0003"+
		"\u001b\r\u0000iL\u0001\u0000\u0000\u0000iZ\u0001\u0000\u0000\u0000id\u0001"+
		"\u0000\u0000\u0000j\u0014\u0001\u0000\u0000\u0000kp\u0005\"\u0000\u0000"+
		"lo\u0003\u001f\u000f\u0000mo\b\u0001\u0000\u0000nl\u0001\u0000\u0000\u0000"+
		"nm\u0001\u0000\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000"+
		"\u0000pq\u0001\u0000\u0000\u0000qs\u0001\u0000\u0000\u0000rp\u0001\u0000"+
		"\u0000\u0000st\u0005\"\u0000\u0000t\u0016\u0001\u0000\u0000\u0000uw\u0007"+
		"\u0002\u0000\u0000vu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000"+
		"xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000y\u0018\u0001\u0000"+
		"\u0000\u0000z~\u0007\u0003\u0000\u0000{}\u0007\u0004\u0000\u0000|{\u0001"+
		"\u0000\u0000\u0000}\u0080\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000"+
		"\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u001a\u0001\u0000\u0000\u0000"+
		"\u0080~\u0001\u0000\u0000\u0000\u0081\u0083\u0007\u0005\u0000\u0000\u0082"+
		"\u0084\u0007\u0006\u0000\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0001\u0000\u0000\u0000\u0084\u0086\u0001\u0000\u0000\u0000\u0085"+
		"\u0087\u000209\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u0088\u0001"+
		"\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001"+
		"\u0000\u0000\u0000\u0089\u001c\u0001\u0000\u0000\u0000\u008a\u008b\u0007"+
		"\u0007\u0000\u0000\u008b\u001e\u0001\u0000\u0000\u0000\u008c\u008d\u0005"+
		"\\\u0000\u0000\u008d\u0091\u0007\b\u0000\u0000\u008e\u0091\u0003#\u0011"+
		"\u0000\u008f\u0091\u0003!\u0010\u0000\u0090\u008c\u0001\u0000\u0000\u0000"+
		"\u0090\u008e\u0001\u0000\u0000\u0000\u0090\u008f\u0001\u0000\u0000\u0000"+
		"\u0091 \u0001\u0000\u0000\u0000\u0092\u0093\u0005\\\u0000\u0000\u0093"+
		"\u0094\u000203\u0000\u0094\u0095\u000207\u0000\u0095\u009c\u000207\u0000"+
		"\u0096\u0097\u0005\\\u0000\u0000\u0097\u0098\u000207\u0000\u0098\u009c"+
		"\u000207\u0000\u0099\u009a\u0005\\\u0000\u0000\u009a\u009c\u000207\u0000"+
		"\u009b\u0092\u0001\u0000\u0000\u0000\u009b\u0096\u0001\u0000\u0000\u0000"+
		"\u009b\u0099\u0001\u0000\u0000\u0000\u009c\"\u0001\u0000\u0000\u0000\u009d"+
		"\u009e\u0005\\\u0000\u0000\u009e\u009f\u0005u\u0000\u0000\u009f\u00a0"+
		"\u0003\u001d\u000e\u0000\u00a0\u00a1\u0003\u001d\u000e\u0000\u00a1\u00a2"+
		"\u0003\u001d\u000e\u0000\u00a2\u00a3\u0003\u001d\u000e\u0000\u00a3$\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a6\u0007\t\u0000\u0000\u00a5\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0006\u0012\u0000\u0000\u00aa&\u0001\u0000\u0000"+
		"\u0000\u0013\u0000DINTX^afinpx~\u0083\u0088\u0090\u009b\u00a7\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}