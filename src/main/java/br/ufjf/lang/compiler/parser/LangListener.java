// Generated from src/main/java/br/ufjf/lang/compiler/parser/Lang.g4 by ANTLR 4.13.1
package br.ufjf.lang.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LangParser}.
 */
public interface LangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LangParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LangParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LangParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDef(LangParser.DefContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDef(LangParser.DefContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(LangParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(LangParser.DataContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(LangParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(LangParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#fun}.
	 * @param ctx the parse tree
	 */
	void enterFun(LangParser.FunContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#fun}.
	 * @param ctx the parse tree
	 */
	void exitFun(LangParser.FunContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(LangParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(LangParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(LangParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(LangParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void enterBtype(LangParser.BtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#btype}.
	 * @param ctx the parse tree
	 */
	void exitBtype(LangParser.BtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LangParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LangParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(LangParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(LangParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void enterItcond(LangParser.ItcondContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 */
	void exitItcond(LangParser.ItcondContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(LangParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(LangParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expAnd}.
	 * @param ctx the parse tree
	 */
	void enterExpAnd(LangParser.ExpAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expAnd}.
	 * @param ctx the parse tree
	 */
	void exitExpAnd(LangParser.ExpAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expEq}.
	 * @param ctx the parse tree
	 */
	void enterExpEq(LangParser.ExpEqContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expEq}.
	 * @param ctx the parse tree
	 */
	void exitExpEq(LangParser.ExpEqContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expRel}.
	 * @param ctx the parse tree
	 */
	void enterExpRel(LangParser.ExpRelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expRel}.
	 * @param ctx the parse tree
	 */
	void exitExpRel(LangParser.ExpRelContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expAdd}.
	 * @param ctx the parse tree
	 */
	void enterExpAdd(LangParser.ExpAddContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expAdd}.
	 * @param ctx the parse tree
	 */
	void exitExpAdd(LangParser.ExpAddContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expMul}.
	 * @param ctx the parse tree
	 */
	void enterExpMul(LangParser.ExpMulContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expMul}.
	 * @param ctx the parse tree
	 */
	void exitExpMul(LangParser.ExpMulContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expUnary}.
	 * @param ctx the parse tree
	 */
	void enterExpUnary(LangParser.ExpUnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expUnary}.
	 * @param ctx the parse tree
	 */
	void exitExpUnary(LangParser.ExpUnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expPostfix}.
	 * @param ctx the parse tree
	 */
	void enterExpPostfix(LangParser.ExpPostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expPostfix}.
	 * @param ctx the parse tree
	 */
	void exitExpPostfix(LangParser.ExpPostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#expPrimary}.
	 * @param ctx the parse tree
	 */
	void enterExpPrimary(LangParser.ExpPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#expPrimary}.
	 * @param ctx the parse tree
	 */
	void exitExpPrimary(LangParser.ExpPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(LangParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(LangParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link LangParser#exps}.
	 * @param ctx the parse tree
	 */
	void enterExps(LangParser.ExpsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LangParser#exps}.
	 * @param ctx the parse tree
	 */
	void exitExps(LangParser.ExpsContext ctx);
}