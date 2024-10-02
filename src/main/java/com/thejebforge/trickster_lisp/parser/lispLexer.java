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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, VOID=6, BOOLEAN=7, INTEGER=8, 
		FLOAT=9, STRING=10, OPERATOR=11, IDENTIFIER=12, WS=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "VOID", "BOOLEAN", "INTEGER", 
			"FLOAT", "STRING", "OPERATOR", "IDENTIFIER", "EXPONENT", "HEX_DIGIT", 
			"ESC_SEQ", "OCTAL_ESC", "UNICODE_ESC", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'['", "','", "']'", "'void'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "VOID", "BOOLEAN", "INTEGER", "FLOAT", 
			"STRING", "OPERATOR", "IDENTIFIER", "WS"
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
		"\u0004\u0000\r\u00a4\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006>\b\u0006\u0001\u0007\u0004\u0007A\b\u0007\u000b\u0007\f\u0007B"+
		"\u0001\b\u0004\bF\b\b\u000b\b\f\bG\u0001\b\u0001\b\u0005\bL\b\b\n\b\f"+
		"\bO\t\b\u0001\b\u0003\bR\b\b\u0001\b\u0001\b\u0004\bV\b\b\u000b\b\f\b"+
		"W\u0001\b\u0003\b[\b\b\u0001\b\u0004\b^\b\b\u000b\b\f\b_\u0001\b\u0003"+
		"\bc\b\b\u0001\t\u0001\t\u0001\t\u0005\th\b\t\n\t\f\tk\t\t\u0001\t\u0001"+
		"\t\u0001\n\u0004\np\b\n\u000b\n\f\nq\u0001\u000b\u0001\u000b\u0005\u000b"+
		"v\b\u000b\n\u000b\f\u000by\t\u000b\u0001\f\u0001\f\u0003\f}\b\f\u0001"+
		"\f\u0004\f\u0080\b\f\u000b\f\f\f\u0081\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u008a\b\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0003\u000f\u0095\b\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0004"+
		"\u0011\u009f\b\u0011\u000b\u0011\f\u0011\u00a0\u0001\u0011\u0001\u0011"+
		"\u0000\u0000\u0012\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005"+
		"\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019"+
		"\u0000\u001b\u0000\u001d\u0000\u001f\u0000!\u0000#\r\u0001\u0000\n\u0001"+
		"\u000009\u0002\u0000\"\"\\\\\t\u0000!!#&*+--//::<@^^||\u0002\u0000AZa"+
		"z\u0005\u0000--0:AZ__az\u0002\u0000EEee\u0002\u0000++--\u0003\u000009"+
		"AFaf\b\u0000\"\"\'\'\\\\bbffnnrrtt\u0003\u0000\t\n\r\r  \u00b3\u0000\u0001"+
		"\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001"+
		"\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000"+
		"\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000"+
		"\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000"+
		"\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000"+
		"\u0000\u0001%\u0001\u0000\u0000\u0000\u0003\'\u0001\u0000\u0000\u0000"+
		"\u0005)\u0001\u0000\u0000\u0000\u0007+\u0001\u0000\u0000\u0000\t-\u0001"+
		"\u0000\u0000\u0000\u000b/\u0001\u0000\u0000\u0000\r=\u0001\u0000\u0000"+
		"\u0000\u000f@\u0001\u0000\u0000\u0000\u0011b\u0001\u0000\u0000\u0000\u0013"+
		"d\u0001\u0000\u0000\u0000\u0015o\u0001\u0000\u0000\u0000\u0017s\u0001"+
		"\u0000\u0000\u0000\u0019z\u0001\u0000\u0000\u0000\u001b\u0083\u0001\u0000"+
		"\u0000\u0000\u001d\u0089\u0001\u0000\u0000\u0000\u001f\u0094\u0001\u0000"+
		"\u0000\u0000!\u0096\u0001\u0000\u0000\u0000#\u009e\u0001\u0000\u0000\u0000"+
		"%&\u0005(\u0000\u0000&\u0002\u0001\u0000\u0000\u0000\'(\u0005)\u0000\u0000"+
		"(\u0004\u0001\u0000\u0000\u0000)*\u0005[\u0000\u0000*\u0006\u0001\u0000"+
		"\u0000\u0000+,\u0005,\u0000\u0000,\b\u0001\u0000\u0000\u0000-.\u0005]"+
		"\u0000\u0000.\n\u0001\u0000\u0000\u0000/0\u0005v\u0000\u000001\u0005o"+
		"\u0000\u000012\u0005i\u0000\u000023\u0005d\u0000\u00003\f\u0001\u0000"+
		"\u0000\u000045\u0005t\u0000\u000056\u0005r\u0000\u000067\u0005u\u0000"+
		"\u00007>\u0005e\u0000\u000089\u0005f\u0000\u00009:\u0005a\u0000\u0000"+
		":;\u0005l\u0000\u0000;<\u0005s\u0000\u0000<>\u0005e\u0000\u0000=4\u0001"+
		"\u0000\u0000\u0000=8\u0001\u0000\u0000\u0000>\u000e\u0001\u0000\u0000"+
		"\u0000?A\u0007\u0000\u0000\u0000@?\u0001\u0000\u0000\u0000AB\u0001\u0000"+
		"\u0000\u0000B@\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000C\u0010"+
		"\u0001\u0000\u0000\u0000DF\u000209\u0000ED\u0001\u0000\u0000\u0000FG\u0001"+
		"\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000"+
		"HI\u0001\u0000\u0000\u0000IM\u0005.\u0000\u0000JL\u000209\u0000KJ\u0001"+
		"\u0000\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000"+
		"MN\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000"+
		"\u0000PR\u0003\u0019\f\u0000QP\u0001\u0000\u0000\u0000QR\u0001\u0000\u0000"+
		"\u0000Rc\u0001\u0000\u0000\u0000SU\u0005.\u0000\u0000TV\u000209\u0000"+
		"UT\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000"+
		"\u0000WX\u0001\u0000\u0000\u0000XZ\u0001\u0000\u0000\u0000Y[\u0003\u0019"+
		"\f\u0000ZY\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[c\u0001\u0000"+
		"\u0000\u0000\\^\u000209\u0000]\\\u0001\u0000\u0000\u0000^_\u0001\u0000"+
		"\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000\u0000`a\u0001"+
		"\u0000\u0000\u0000ac\u0003\u0019\f\u0000bE\u0001\u0000\u0000\u0000bS\u0001"+
		"\u0000\u0000\u0000b]\u0001\u0000\u0000\u0000c\u0012\u0001\u0000\u0000"+
		"\u0000di\u0005\"\u0000\u0000eh\u0003\u001d\u000e\u0000fh\b\u0001\u0000"+
		"\u0000ge\u0001\u0000\u0000\u0000gf\u0001\u0000\u0000\u0000hk\u0001\u0000"+
		"\u0000\u0000ig\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jl\u0001"+
		"\u0000\u0000\u0000ki\u0001\u0000\u0000\u0000lm\u0005\"\u0000\u0000m\u0014"+
		"\u0001\u0000\u0000\u0000np\u0007\u0002\u0000\u0000on\u0001\u0000\u0000"+
		"\u0000pq\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000qr\u0001\u0000"+
		"\u0000\u0000r\u0016\u0001\u0000\u0000\u0000sw\u0007\u0003\u0000\u0000"+
		"tv\u0007\u0004\u0000\u0000ut\u0001\u0000\u0000\u0000vy\u0001\u0000\u0000"+
		"\u0000wu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000x\u0018\u0001"+
		"\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000z|\u0007\u0005\u0000\u0000"+
		"{}\u0007\u0006\u0000\u0000|{\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000"+
		"\u0000}\u007f\u0001\u0000\u0000\u0000~\u0080\u000209\u0000\u007f~\u0001"+
		"\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u007f\u0001"+
		"\u0000\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u001a\u0001"+
		"\u0000\u0000\u0000\u0083\u0084\u0007\u0007\u0000\u0000\u0084\u001c\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0005\\\u0000\u0000\u0086\u008a\u0007\b"+
		"\u0000\u0000\u0087\u008a\u0003!\u0010\u0000\u0088\u008a\u0003\u001f\u000f"+
		"\u0000\u0089\u0085\u0001\u0000\u0000\u0000\u0089\u0087\u0001\u0000\u0000"+
		"\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u008a\u001e\u0001\u0000\u0000"+
		"\u0000\u008b\u008c\u0005\\\u0000\u0000\u008c\u008d\u000203\u0000\u008d"+
		"\u008e\u000207\u0000\u008e\u0095\u000207\u0000\u008f\u0090\u0005\\\u0000"+
		"\u0000\u0090\u0091\u000207\u0000\u0091\u0095\u000207\u0000\u0092\u0093"+
		"\u0005\\\u0000\u0000\u0093\u0095\u000207\u0000\u0094\u008b\u0001\u0000"+
		"\u0000\u0000\u0094\u008f\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000"+
		"\u0000\u0000\u0095 \u0001\u0000\u0000\u0000\u0096\u0097\u0005\\\u0000"+
		"\u0000\u0097\u0098\u0005u\u0000\u0000\u0098\u0099\u0003\u001b\r\u0000"+
		"\u0099\u009a\u0003\u001b\r\u0000\u009a\u009b\u0003\u001b\r\u0000\u009b"+
		"\u009c\u0003\u001b\r\u0000\u009c\"\u0001\u0000\u0000\u0000\u009d\u009f"+
		"\u0007\t\u0000\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a3\u0006"+
		"\u0011\u0000\u0000\u00a3$\u0001\u0000\u0000\u0000\u0013\u0000=BGMQWZ_"+
		"bgiqw|\u0081\u0089\u0094\u00a0\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}