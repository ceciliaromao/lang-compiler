// Generated from src/main/java/br/ufjf/lang/compiler/parser/Lang.g4 by ANTLR 4.13.1
package br.ufjf.lang.compiler.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class LangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, TYID=40, ID=41, INT=42, FLOAT=43, CHAR=44, WS=45, COMMENT=46, 
		BLOCK_COMMENT=47;
	public static final int
		RULE_prog = 0, RULE_def = 1, RULE_data = 2, RULE_decl = 3, RULE_fun = 4, 
		RULE_params = 5, RULE_type = 6, RULE_btype = 7, RULE_block = 8, RULE_cmd = 9, 
		RULE_itcond = 10, RULE_exp = 11, RULE_expAnd = 12, RULE_expEq = 13, RULE_expRel = 14, 
		RULE_expAdd = 15, RULE_expMul = 16, RULE_expUnary = 17, RULE_expPostfix = 18, 
		RULE_expPrimary = 19, RULE_lvalue = 20, RULE_exps = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "def", "data", "decl", "fun", "params", "type", "btype", "block", 
			"cmd", "itcond", "exp", "expAnd", "expEq", "expRel", "expAdd", "expMul", 
			"expUnary", "expPostfix", "expPrimary", "lvalue", "exps"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'abstract'", "'data'", "'{'", "'}'", "'::'", "';'", "'('", "')'", 
			"':'", "','", "'['", "']'", "'Int'", "'Char'", "'Bool'", "'Float'", "'if'", 
			"'else'", "'iterate'", "'read'", "'print'", "'return'", "'='", "'<'", 
			"'>'", "'&&'", "'=='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", 
			"'.'", "'new'", "'true'", "'false'", "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "TYID", "ID", "INT", "FLOAT", "CHAR", "WS", "COMMENT", 
			"BLOCK_COMMENT"
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
	public String getGrammarFileName() { return "Lang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(LangParser.EOF, 0); }
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2199023255558L) != 0)) {
				{
				{
				setState(44);
				def();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
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
	public static class DefContext extends ParserRuleContext {
		public DataContext data() {
			return getRuleContext(DataContext.class,0);
		}
		public FunContext fun() {
			return getRuleContext(FunContext.class,0);
		}
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_def);
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				data();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				fun();
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
	public static class DataContext extends ParserRuleContext {
		public TerminalNode TYID() { return getToken(LangParser.TYID, 0); }
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public List<FunContext> fun() {
			return getRuleContexts(FunContext.class);
		}
		public FunContext fun(int i) {
			return getRuleContext(FunContext.class,i);
		}
		public DataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitData(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_data);
		int _la;
		try {
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(T__0);
				setState(57);
				match(T__1);
				setState(58);
				match(TYID);
				setState(59);
				match(T__2);
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					setState(62);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						setState(60);
						decl();
						}
						break;
					case 2:
						{
						setState(61);
						fun();
						}
						break;
					}
					}
					setState(66);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(67);
				match(T__3);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				match(T__1);
				setState(69);
				match(TYID);
				setState(70);
				match(T__2);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(71);
					decl();
					}
					}
					setState(76);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(77);
				match(T__3);
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
	public static class DeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(ID);
			setState(81);
			match(T__4);
			setState(82);
			type(0);
			setState(83);
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
	public static class FunContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public CmdContext cmd() {
			return getRuleContext(CmdContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public FunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterFun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitFun(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitFun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunContext fun() throws RecognitionException {
		FunContext _localctx = new FunContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fun);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(ID);
			setState(86);
			match(T__6);
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(87);
				params();
				}
			}

			setState(90);
			match(T__7);
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(91);
				match(T__8);
				setState(92);
				type(0);
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(93);
					match(T__9);
					setState(94);
					type(0);
					}
					}
					setState(99);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(102);
			cmd();
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
	public static class ParamsContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(LangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LangParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(ID);
			setState(105);
			match(T__4);
			setState(106);
			type(0);
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(107);
				match(T__9);
				setState(108);
				match(ID);
				setState(109);
				match(T__4);
				setState(110);
				type(0);
				}
				}
				setState(115);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class TypeContext extends ParserRuleContext {
		public BtypeContext btype() {
			return getRuleContext(BtypeContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(117);
			btype();
			}
			_ctx.stop = _input.LT(-1);
			setState(124);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(119);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(120);
					match(T__10);
					setState(121);
					match(T__11);
					}
					} 
				}
				setState(126);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BtypeContext extends ParserRuleContext {
		public TerminalNode TYID() { return getToken(LangParser.TYID, 0); }
		public BtypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_btype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterBtype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitBtype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitBtype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BtypeContext btype() throws RecognitionException {
		BtypeContext _localctx = new BtypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_btype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1099511750656L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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
	public static class BlockContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(T__2);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2199031250952L) != 0)) {
				{
				{
				setState(130);
				cmd();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			match(T__3);
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
	public static class CmdContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public ItcondContext itcond() {
			return getRuleContext(ItcondContext.class,0);
		}
		public List<LvalueContext> lvalue() {
			return getRuleContexts(LvalueContext.class);
		}
		public LvalueContext lvalue(int i) {
			return getRuleContext(LvalueContext.class,i);
		}
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitCmd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitCmd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cmd);
		int _la;
		try {
			setState(203);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				match(T__16);
				setState(140);
				match(T__6);
				setState(141);
				exp();
				setState(142);
				match(T__7);
				setState(143);
				cmd();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				match(T__16);
				setState(146);
				match(T__6);
				setState(147);
				exp();
				setState(148);
				match(T__7);
				setState(149);
				cmd();
				setState(150);
				match(T__17);
				setState(151);
				cmd();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(153);
				match(T__18);
				setState(154);
				match(T__6);
				setState(155);
				itcond();
				setState(156);
				match(T__7);
				setState(157);
				cmd();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(159);
				match(T__19);
				setState(160);
				lvalue(0);
				setState(161);
				match(T__5);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(163);
				match(T__20);
				setState(164);
				exp();
				setState(165);
				match(T__5);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(167);
				match(T__21);
				setState(168);
				exp();
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(169);
					match(T__9);
					setState(170);
					exp();
					}
					}
					setState(175);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(176);
				match(T__5);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(178);
				lvalue(0);
				setState(179);
				match(T__22);
				setState(180);
				exp();
				setState(181);
				match(T__5);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(183);
				match(ID);
				setState(184);
				match(T__6);
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 34034394595456L) != 0)) {
					{
					setState(185);
					exps();
					}
				}

				setState(188);
				match(T__7);
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(189);
					match(T__23);
					setState(190);
					lvalue(0);
					setState(195);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__9) {
						{
						{
						setState(191);
						match(T__9);
						setState(192);
						lvalue(0);
						}
						}
						setState(197);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(198);
					match(T__24);
					}
				}

				setState(202);
				match(T__5);
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
	public static class ItcondContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ItcondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_itcond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterItcond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitItcond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitItcond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItcondContext itcond() throws RecognitionException {
		ItcondContext _localctx = new ItcondContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_itcond);
		try {
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				match(ID);
				setState(206);
				match(T__8);
				setState(207);
				exp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(208);
				exp();
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
	public static class ExpContext extends ParserRuleContext {
		public ExpAndContext expAnd() {
			return getRuleContext(ExpAndContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			expAnd();
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
	public static class ExpAndContext extends ParserRuleContext {
		public List<ExpEqContext> expEq() {
			return getRuleContexts(ExpEqContext.class);
		}
		public ExpEqContext expEq(int i) {
			return getRuleContext(ExpEqContext.class,i);
		}
		public ExpAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expAnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpAndContext expAnd() throws RecognitionException {
		ExpAndContext _localctx = new ExpAndContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expAnd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			expEq();
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__25) {
				{
				{
				setState(214);
				match(T__25);
				setState(215);
				expEq();
				}
				}
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ExpEqContext extends ParserRuleContext {
		public List<ExpRelContext> expRel() {
			return getRuleContexts(ExpRelContext.class);
		}
		public ExpRelContext expRel(int i) {
			return getRuleContext(ExpRelContext.class,i);
		}
		public ExpEqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expEq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpEq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpEq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpEqContext expEq() throws RecognitionException {
		ExpEqContext _localctx = new ExpEqContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expEq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			expRel();
			setState(226);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__26 || _la==T__27) {
				{
				{
				setState(222);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(223);
				expRel();
				}
				}
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ExpRelContext extends ParserRuleContext {
		public List<ExpAddContext> expAdd() {
			return getRuleContexts(ExpAddContext.class);
		}
		public ExpAddContext expAdd(int i) {
			return getRuleContext(ExpAddContext.class,i);
		}
		public ExpRelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expRel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpRel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpRel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpRel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpRelContext expRel() throws RecognitionException {
		ExpRelContext _localctx = new ExpRelContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expRel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			expAdd();
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__23) {
				{
				setState(230);
				match(T__23);
				setState(231);
				expAdd();
				}
			}

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
	public static class ExpAddContext extends ParserRuleContext {
		public List<ExpMulContext> expMul() {
			return getRuleContexts(ExpMulContext.class);
		}
		public ExpMulContext expMul(int i) {
			return getRuleContext(ExpMulContext.class,i);
		}
		public ExpAddContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expAdd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpAdd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpAdd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpAddContext expAdd() throws RecognitionException {
		ExpAddContext _localctx = new ExpAddContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expAdd);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			expMul();
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__28 || _la==T__29) {
				{
				{
				setState(235);
				_la = _input.LA(1);
				if ( !(_la==T__28 || _la==T__29) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(236);
				expMul();
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ExpMulContext extends ParserRuleContext {
		public List<ExpUnaryContext> expUnary() {
			return getRuleContexts(ExpUnaryContext.class);
		}
		public ExpUnaryContext expUnary(int i) {
			return getRuleContext(ExpUnaryContext.class,i);
		}
		public ExpMulContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expMul; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpMul(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpMul(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpMul(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpMulContext expMul() throws RecognitionException {
		ExpMulContext _localctx = new ExpMulContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expMul);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			expUnary();
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032385536L) != 0)) {
				{
				{
				setState(243);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 15032385536L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(244);
				expUnary();
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ExpUnaryContext extends ParserRuleContext {
		public ExpUnaryContext expUnary() {
			return getRuleContext(ExpUnaryContext.class,0);
		}
		public ExpPostfixContext expPostfix() {
			return getRuleContext(ExpPostfixContext.class,0);
		}
		public ExpUnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expUnary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpUnaryContext expUnary() throws RecognitionException {
		ExpUnaryContext _localctx = new ExpUnaryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expUnary);
		try {
			setState(255);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__33:
				enterOuterAlt(_localctx, 1);
				{
				setState(250);
				match(T__33);
				setState(251);
				expUnary();
				}
				break;
			case T__29:
				enterOuterAlt(_localctx, 2);
				{
				setState(252);
				match(T__29);
				setState(253);
				expUnary();
				}
				break;
			case T__6:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case ID:
			case INT:
			case FLOAT:
			case CHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(254);
				expPostfix();
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
	public static class ExpPostfixContext extends ParserRuleContext {
		public ExpPrimaryContext expPrimary() {
			return getRuleContext(ExpPrimaryContext.class,0);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(LangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LangParser.ID, i);
		}
		public ExpPostfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expPostfix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpPostfix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpPostfix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpPostfix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpPostfixContext expPostfix() throws RecognitionException {
		ExpPostfixContext _localctx = new ExpPostfixContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expPostfix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			expPrimary();
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10 || _la==T__34) {
				{
				setState(264);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__10:
					{
					setState(258);
					match(T__10);
					setState(259);
					exp();
					setState(260);
					match(T__11);
					}
					break;
				case T__34:
					{
					setState(262);
					match(T__34);
					setState(263);
					match(ID);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ExpPrimaryContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
		}
		public TerminalNode INT() { return getToken(LangParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(LangParser.FLOAT, 0); }
		public TerminalNode CHAR() { return getToken(LangParser.CHAR, 0); }
		public ExpPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expPrimary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExpPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExpPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExpPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpPrimaryContext expPrimary() throws RecognitionException {
		ExpPrimaryContext _localctx = new ExpPrimaryContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expPrimary);
		int _la;
		try {
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(269);
				lvalue(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				match(T__6);
				setState(271);
				exp();
				setState(272);
				match(T__7);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(274);
				match(T__35);
				setState(275);
				type(0);
				setState(280);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(276);
					match(T__10);
					setState(277);
					exp();
					setState(278);
					match(T__11);
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(282);
				match(ID);
				setState(283);
				match(T__6);
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 34034394595456L) != 0)) {
					{
					setState(284);
					exps();
					}
				}

				setState(287);
				match(T__7);
				setState(288);
				match(T__10);
				setState(289);
				exp();
				setState(290);
				match(T__11);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(292);
				match(T__36);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(293);
				match(T__37);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(294);
				match(T__38);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(295);
				match(INT);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(296);
				match(FLOAT);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(297);
				match(CHAR);
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
	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitLvalue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitLvalue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		return lvalue(0);
	}

	private LvalueContext lvalue(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LvalueContext _localctx = new LvalueContext(_ctx, _parentState);
		LvalueContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_lvalue, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(301);
			match(ID);
			}
			_ctx.stop = _input.LT(-1);
			setState(313);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(311);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new LvalueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_lvalue);
						setState(303);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(304);
						match(T__10);
						setState(305);
						exp();
						setState(306);
						match(T__11);
						}
						break;
					case 2:
						{
						_localctx = new LvalueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_lvalue);
						setState(308);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(309);
						match(T__34);
						setState(310);
						match(ID);
						}
						break;
					}
					} 
				}
				setState(315);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpsContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LangVisitor ) return ((LangVisitor<? extends T>)visitor).visitExps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpsContext exps() throws RecognitionException {
		ExpsContext _localctx = new ExpsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_exps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			exp();
			setState(321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(317);
				match(T__9);
				setState(318);
				exp();
				}
				}
				setState(323);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 20:
			return lvalue_sempred((LvalueContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean lvalue_sempred(LvalueContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001/\u0145\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0005\u0000.\b\u0000\n\u0000\f\u00001\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u00017\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"?\b\u0002\n\u0002\f\u0002B\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002I\b\u0002\n\u0002\f\u0002L\t\u0002"+
		"\u0001\u0002\u0003\u0002O\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"Y\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004`\b\u0004\n\u0004\f\u0004c\t\u0004\u0003\u0004e\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005p\b\u0005\n\u0005\f\u0005s\t"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006{\b\u0006\n\u0006\f\u0006~\t\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0005\b\u0084\b\b\n\b\f\b\u0087\t\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u00ac\b\t\n"+
		"\t\f\t\u00af\t\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0003\t\u00bb\b\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0005\t\u00c2\b\t\n\t\f\t\u00c5\t\t\u0001\t\u0001\t\u0003\t"+
		"\u00c9\b\t\u0001\t\u0003\t\u00cc\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\n\u00d2\b\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0005\f\u00d9"+
		"\b\f\n\f\f\f\u00dc\t\f\u0001\r\u0001\r\u0001\r\u0005\r\u00e1\b\r\n\r\f"+
		"\r\u00e4\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00e9\b\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00ee\b\u000f\n\u000f"+
		"\f\u000f\u00f1\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010"+
		"\u00f6\b\u0010\n\u0010\f\u0010\u00f9\t\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u0100\b\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005"+
		"\u0012\u0109\b\u0012\n\u0012\f\u0012\u010c\t\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u0119\b\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u011e\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u012b\b\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0138\b\u0014"+
		"\n\u0014\f\u0014\u013b\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0005"+
		"\u0015\u0140\b\u0015\n\u0015\f\u0015\u0143\t\u0015\u0001\u0015\u0000\u0002"+
		"\f(\u0016\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*\u0000\u0004\u0002\u0000\r\u0010((\u0001"+
		"\u0000\u001b\u001c\u0001\u0000\u001d\u001e\u0001\u0000\u001f!\u015e\u0000"+
		"/\u0001\u0000\u0000\u0000\u00026\u0001\u0000\u0000\u0000\u0004N\u0001"+
		"\u0000\u0000\u0000\u0006P\u0001\u0000\u0000\u0000\bU\u0001\u0000\u0000"+
		"\u0000\nh\u0001\u0000\u0000\u0000\ft\u0001\u0000\u0000\u0000\u000e\u007f"+
		"\u0001\u0000\u0000\u0000\u0010\u0081\u0001\u0000\u0000\u0000\u0012\u00cb"+
		"\u0001\u0000\u0000\u0000\u0014\u00d1\u0001\u0000\u0000\u0000\u0016\u00d3"+
		"\u0001\u0000\u0000\u0000\u0018\u00d5\u0001\u0000\u0000\u0000\u001a\u00dd"+
		"\u0001\u0000\u0000\u0000\u001c\u00e5\u0001\u0000\u0000\u0000\u001e\u00ea"+
		"\u0001\u0000\u0000\u0000 \u00f2\u0001\u0000\u0000\u0000\"\u00ff\u0001"+
		"\u0000\u0000\u0000$\u0101\u0001\u0000\u0000\u0000&\u012a\u0001\u0000\u0000"+
		"\u0000(\u012c\u0001\u0000\u0000\u0000*\u013c\u0001\u0000\u0000\u0000,"+
		".\u0003\u0002\u0001\u0000-,\u0001\u0000\u0000\u0000.1\u0001\u0000\u0000"+
		"\u0000/-\u0001\u0000\u0000\u0000/0\u0001\u0000\u0000\u000002\u0001\u0000"+
		"\u0000\u00001/\u0001\u0000\u0000\u000023\u0005\u0000\u0000\u00013\u0001"+
		"\u0001\u0000\u0000\u000047\u0003\u0004\u0002\u000057\u0003\b\u0004\u0000"+
		"64\u0001\u0000\u0000\u000065\u0001\u0000\u0000\u00007\u0003\u0001\u0000"+
		"\u0000\u000089\u0005\u0001\u0000\u00009:\u0005\u0002\u0000\u0000:;\u0005"+
		"(\u0000\u0000;@\u0005\u0003\u0000\u0000<?\u0003\u0006\u0003\u0000=?\u0003"+
		"\b\u0004\u0000><\u0001\u0000\u0000\u0000>=\u0001\u0000\u0000\u0000?B\u0001"+
		"\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000"+
		"AC\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000CO\u0005\u0004\u0000"+
		"\u0000DE\u0005\u0002\u0000\u0000EF\u0005(\u0000\u0000FJ\u0005\u0003\u0000"+
		"\u0000GI\u0003\u0006\u0003\u0000HG\u0001\u0000\u0000\u0000IL\u0001\u0000"+
		"\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000KM\u0001"+
		"\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000MO\u0005\u0004\u0000\u0000"+
		"N8\u0001\u0000\u0000\u0000ND\u0001\u0000\u0000\u0000O\u0005\u0001\u0000"+
		"\u0000\u0000PQ\u0005)\u0000\u0000QR\u0005\u0005\u0000\u0000RS\u0003\f"+
		"\u0006\u0000ST\u0005\u0006\u0000\u0000T\u0007\u0001\u0000\u0000\u0000"+
		"UV\u0005)\u0000\u0000VX\u0005\u0007\u0000\u0000WY\u0003\n\u0005\u0000"+
		"XW\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000"+
		"\u0000Zd\u0005\b\u0000\u0000[\\\u0005\t\u0000\u0000\\a\u0003\f\u0006\u0000"+
		"]^\u0005\n\u0000\u0000^`\u0003\f\u0006\u0000_]\u0001\u0000\u0000\u0000"+
		"`c\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000"+
		"\u0000be\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000\u0000d[\u0001\u0000"+
		"\u0000\u0000de\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0003"+
		"\u0012\t\u0000g\t\u0001\u0000\u0000\u0000hi\u0005)\u0000\u0000ij\u0005"+
		"\u0005\u0000\u0000jq\u0003\f\u0006\u0000kl\u0005\n\u0000\u0000lm\u0005"+
		")\u0000\u0000mn\u0005\u0005\u0000\u0000np\u0003\f\u0006\u0000ok\u0001"+
		"\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000"+
		"qr\u0001\u0000\u0000\u0000r\u000b\u0001\u0000\u0000\u0000sq\u0001\u0000"+
		"\u0000\u0000tu\u0006\u0006\uffff\uffff\u0000uv\u0003\u000e\u0007\u0000"+
		"v|\u0001\u0000\u0000\u0000wx\n\u0002\u0000\u0000xy\u0005\u000b\u0000\u0000"+
		"y{\u0005\f\u0000\u0000zw\u0001\u0000\u0000\u0000{~\u0001\u0000\u0000\u0000"+
		"|z\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}\r\u0001\u0000\u0000"+
		"\u0000~|\u0001\u0000\u0000\u0000\u007f\u0080\u0007\u0000\u0000\u0000\u0080"+
		"\u000f\u0001\u0000\u0000\u0000\u0081\u0085\u0005\u0003\u0000\u0000\u0082"+
		"\u0084\u0003\u0012\t\u0000\u0083\u0082\u0001\u0000\u0000\u0000\u0084\u0087"+
		"\u0001\u0000\u0000\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0085\u0086"+
		"\u0001\u0000\u0000\u0000\u0086\u0088\u0001\u0000\u0000\u0000\u0087\u0085"+
		"\u0001\u0000\u0000\u0000\u0088\u0089\u0005\u0004\u0000\u0000\u0089\u0011"+
		"\u0001\u0000\u0000\u0000\u008a\u00cc\u0003\u0010\b\u0000\u008b\u008c\u0005"+
		"\u0011\u0000\u0000\u008c\u008d\u0005\u0007\u0000\u0000\u008d\u008e\u0003"+
		"\u0016\u000b\u0000\u008e\u008f\u0005\b\u0000\u0000\u008f\u0090\u0003\u0012"+
		"\t\u0000\u0090\u00cc\u0001\u0000\u0000\u0000\u0091\u0092\u0005\u0011\u0000"+
		"\u0000\u0092\u0093\u0005\u0007\u0000\u0000\u0093\u0094\u0003\u0016\u000b"+
		"\u0000\u0094\u0095\u0005\b\u0000\u0000\u0095\u0096\u0003\u0012\t\u0000"+
		"\u0096\u0097\u0005\u0012\u0000\u0000\u0097\u0098\u0003\u0012\t\u0000\u0098"+
		"\u00cc\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u0013\u0000\u0000\u009a"+
		"\u009b\u0005\u0007\u0000\u0000\u009b\u009c\u0003\u0014\n\u0000\u009c\u009d"+
		"\u0005\b\u0000\u0000\u009d\u009e\u0003\u0012\t\u0000\u009e\u00cc\u0001"+
		"\u0000\u0000\u0000\u009f\u00a0\u0005\u0014\u0000\u0000\u00a0\u00a1\u0003"+
		"(\u0014\u0000\u00a1\u00a2\u0005\u0006\u0000\u0000\u00a2\u00cc\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a4\u0005\u0015\u0000\u0000\u00a4\u00a5\u0003\u0016"+
		"\u000b\u0000\u00a5\u00a6\u0005\u0006\u0000\u0000\u00a6\u00cc\u0001\u0000"+
		"\u0000\u0000\u00a7\u00a8\u0005\u0016\u0000\u0000\u00a8\u00ad\u0003\u0016"+
		"\u000b\u0000\u00a9\u00aa\u0005\n\u0000\u0000\u00aa\u00ac\u0003\u0016\u000b"+
		"\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000"+
		"\u0000\u00ae\u00b0\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000"+
		"\u0000\u00b0\u00b1\u0005\u0006\u0000\u0000\u00b1\u00cc\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b3\u0003(\u0014\u0000\u00b3\u00b4\u0005\u0017\u0000\u0000"+
		"\u00b4\u00b5\u0003\u0016\u000b\u0000\u00b5\u00b6\u0005\u0006\u0000\u0000"+
		"\u00b6\u00cc\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005)\u0000\u0000\u00b8"+
		"\u00ba\u0005\u0007\u0000\u0000\u00b9\u00bb\u0003*\u0015\u0000\u00ba\u00b9"+
		"\u0001\u0000\u0000\u0000\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb\u00bc"+
		"\u0001\u0000\u0000\u0000\u00bc\u00c8\u0005\b\u0000\u0000\u00bd\u00be\u0005"+
		"\u0018\u0000\u0000\u00be\u00c3\u0003(\u0014\u0000\u00bf\u00c0\u0005\n"+
		"\u0000\u0000\u00c0\u00c2\u0003(\u0014\u0000\u00c1\u00bf\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c5\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c4\u0001\u0000\u0000\u0000\u00c4\u00c6\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c6\u00c7\u0005\u0019\u0000"+
		"\u0000\u00c7\u00c9\u0001\u0000\u0000\u0000\u00c8\u00bd\u0001\u0000\u0000"+
		"\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cc\u0005\u0006\u0000\u0000\u00cb\u008a\u0001\u0000\u0000"+
		"\u0000\u00cb\u008b\u0001\u0000\u0000\u0000\u00cb\u0091\u0001\u0000\u0000"+
		"\u0000\u00cb\u0099\u0001\u0000\u0000\u0000\u00cb\u009f\u0001\u0000\u0000"+
		"\u0000\u00cb\u00a3\u0001\u0000\u0000\u0000\u00cb\u00a7\u0001\u0000\u0000"+
		"\u0000\u00cb\u00b2\u0001\u0000\u0000\u0000\u00cb\u00b7\u0001\u0000\u0000"+
		"\u0000\u00cc\u0013\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005)\u0000\u0000"+
		"\u00ce\u00cf\u0005\t\u0000\u0000\u00cf\u00d2\u0003\u0016\u000b\u0000\u00d0"+
		"\u00d2\u0003\u0016\u000b\u0000\u00d1\u00cd\u0001\u0000\u0000\u0000\u00d1"+
		"\u00d0\u0001\u0000\u0000\u0000\u00d2\u0015\u0001\u0000\u0000\u0000\u00d3"+
		"\u00d4\u0003\u0018\f\u0000\u00d4\u0017\u0001\u0000\u0000\u0000\u00d5\u00da"+
		"\u0003\u001a\r\u0000\u00d6\u00d7\u0005\u001a\u0000\u0000\u00d7\u00d9\u0003"+
		"\u001a\r\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d9\u00dc\u0001\u0000"+
		"\u0000\u0000\u00da\u00d8\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000"+
		"\u0000\u0000\u00db\u0019\u0001\u0000\u0000\u0000\u00dc\u00da\u0001\u0000"+
		"\u0000\u0000\u00dd\u00e2\u0003\u001c\u000e\u0000\u00de\u00df\u0007\u0001"+
		"\u0000\u0000\u00df\u00e1\u0003\u001c\u000e\u0000\u00e0\u00de\u0001\u0000"+
		"\u0000\u0000\u00e1\u00e4\u0001\u0000\u0000\u0000\u00e2\u00e0\u0001\u0000"+
		"\u0000\u0000\u00e2\u00e3\u0001\u0000\u0000\u0000\u00e3\u001b\u0001\u0000"+
		"\u0000\u0000\u00e4\u00e2\u0001\u0000\u0000\u0000\u00e5\u00e8\u0003\u001e"+
		"\u000f\u0000\u00e6\u00e7\u0005\u0018\u0000\u0000\u00e7\u00e9\u0003\u001e"+
		"\u000f\u0000\u00e8\u00e6\u0001\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000"+
		"\u0000\u0000\u00e9\u001d\u0001\u0000\u0000\u0000\u00ea\u00ef\u0003 \u0010"+
		"\u0000\u00eb\u00ec\u0007\u0002\u0000\u0000\u00ec\u00ee\u0003 \u0010\u0000"+
		"\u00ed\u00eb\u0001\u0000\u0000\u0000\u00ee\u00f1\u0001\u0000\u0000\u0000"+
		"\u00ef\u00ed\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000"+
		"\u00f0\u001f\u0001\u0000\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000"+
		"\u00f2\u00f7\u0003\"\u0011\u0000\u00f3\u00f4\u0007\u0003\u0000\u0000\u00f4"+
		"\u00f6\u0003\"\u0011\u0000\u00f5\u00f3\u0001\u0000\u0000\u0000\u00f6\u00f9"+
		"\u0001\u0000\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f7\u00f8"+
		"\u0001\u0000\u0000\u0000\u00f8!\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001"+
		"\u0000\u0000\u0000\u00fa\u00fb\u0005\"\u0000\u0000\u00fb\u0100\u0003\""+
		"\u0011\u0000\u00fc\u00fd\u0005\u001e\u0000\u0000\u00fd\u0100\u0003\"\u0011"+
		"\u0000\u00fe\u0100\u0003$\u0012\u0000\u00ff\u00fa\u0001\u0000\u0000\u0000"+
		"\u00ff\u00fc\u0001\u0000\u0000\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000"+
		"\u0100#\u0001\u0000\u0000\u0000\u0101\u010a\u0003&\u0013\u0000\u0102\u0103"+
		"\u0005\u000b\u0000\u0000\u0103\u0104\u0003\u0016\u000b\u0000\u0104\u0105"+
		"\u0005\f\u0000\u0000\u0105\u0109\u0001\u0000\u0000\u0000\u0106\u0107\u0005"+
		"#\u0000\u0000\u0107\u0109\u0005)\u0000\u0000\u0108\u0102\u0001\u0000\u0000"+
		"\u0000\u0108\u0106\u0001\u0000\u0000\u0000\u0109\u010c\u0001\u0000\u0000"+
		"\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001\u0000\u0000"+
		"\u0000\u010b%\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000"+
		"\u010d\u012b\u0003(\u0014\u0000\u010e\u010f\u0005\u0007\u0000\u0000\u010f"+
		"\u0110\u0003\u0016\u000b\u0000\u0110\u0111\u0005\b\u0000\u0000\u0111\u012b"+
		"\u0001\u0000\u0000\u0000\u0112\u0113\u0005$\u0000\u0000\u0113\u0118\u0003"+
		"\f\u0006\u0000\u0114\u0115\u0005\u000b\u0000\u0000\u0115\u0116\u0003\u0016"+
		"\u000b\u0000\u0116\u0117\u0005\f\u0000\u0000\u0117\u0119\u0001\u0000\u0000"+
		"\u0000\u0118\u0114\u0001\u0000\u0000\u0000\u0118\u0119\u0001\u0000\u0000"+
		"\u0000\u0119\u012b\u0001\u0000\u0000\u0000\u011a\u011b\u0005)\u0000\u0000"+
		"\u011b\u011d\u0005\u0007\u0000\u0000\u011c\u011e\u0003*\u0015\u0000\u011d"+
		"\u011c\u0001\u0000\u0000\u0000\u011d\u011e\u0001\u0000\u0000\u0000\u011e"+
		"\u011f\u0001\u0000\u0000\u0000\u011f\u0120\u0005\b\u0000\u0000\u0120\u0121"+
		"\u0005\u000b\u0000\u0000\u0121\u0122\u0003\u0016\u000b\u0000\u0122\u0123"+
		"\u0005\f\u0000\u0000\u0123\u012b\u0001\u0000\u0000\u0000\u0124\u012b\u0005"+
		"%\u0000\u0000\u0125\u012b\u0005&\u0000\u0000\u0126\u012b\u0005\'\u0000"+
		"\u0000\u0127\u012b\u0005*\u0000\u0000\u0128\u012b\u0005+\u0000\u0000\u0129"+
		"\u012b\u0005,\u0000\u0000\u012a\u010d\u0001\u0000\u0000\u0000\u012a\u010e"+
		"\u0001\u0000\u0000\u0000\u012a\u0112\u0001\u0000\u0000\u0000\u012a\u011a"+
		"\u0001\u0000\u0000\u0000\u012a\u0124\u0001\u0000\u0000\u0000\u012a\u0125"+
		"\u0001\u0000\u0000\u0000\u012a\u0126\u0001\u0000\u0000\u0000\u012a\u0127"+
		"\u0001\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012a\u0129"+
		"\u0001\u0000\u0000\u0000\u012b\'\u0001\u0000\u0000\u0000\u012c\u012d\u0006"+
		"\u0014\uffff\uffff\u0000\u012d\u012e\u0005)\u0000\u0000\u012e\u0139\u0001"+
		"\u0000\u0000\u0000\u012f\u0130\n\u0002\u0000\u0000\u0130\u0131\u0005\u000b"+
		"\u0000\u0000\u0131\u0132\u0003\u0016\u000b\u0000\u0132\u0133\u0005\f\u0000"+
		"\u0000\u0133\u0138\u0001\u0000\u0000\u0000\u0134\u0135\n\u0001\u0000\u0000"+
		"\u0135\u0136\u0005#\u0000\u0000\u0136\u0138\u0005)\u0000\u0000\u0137\u012f"+
		"\u0001\u0000\u0000\u0000\u0137\u0134\u0001\u0000\u0000\u0000\u0138\u013b"+
		"\u0001\u0000\u0000\u0000\u0139\u0137\u0001\u0000\u0000\u0000\u0139\u013a"+
		"\u0001\u0000\u0000\u0000\u013a)\u0001\u0000\u0000\u0000\u013b\u0139\u0001"+
		"\u0000\u0000\u0000\u013c\u0141\u0003\u0016\u000b\u0000\u013d\u013e\u0005"+
		"\n\u0000\u0000\u013e\u0140\u0003\u0016\u000b\u0000\u013f\u013d\u0001\u0000"+
		"\u0000\u0000\u0140\u0143\u0001\u0000\u0000\u0000\u0141\u013f\u0001\u0000"+
		"\u0000\u0000\u0141\u0142\u0001\u0000\u0000\u0000\u0142+\u0001\u0000\u0000"+
		"\u0000\u0143\u0141\u0001\u0000\u0000\u0000 /6>@JNXadq|\u0085\u00ad\u00ba"+
		"\u00c3\u00c8\u00cb\u00d1\u00da\u00e2\u00e8\u00ef\u00f7\u00ff\u0108\u010a"+
		"\u0118\u011d\u012a\u0137\u0139\u0141";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}