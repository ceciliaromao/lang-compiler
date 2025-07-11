// Generated from src/main/java/br/ufjf/lang/compiler/parser/Lang.g4 by ANTLR 4.13.1
package br.ufjf.lang.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(LangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef(LangParser.DefContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData(LangParser.DataContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(LangParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#fun}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun(LangParser.FunContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(LangParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(LangParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#btype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBtype(LangParser.BtypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmd(LangParser.CmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#itcond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItcond(LangParser.ItcondContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(LangParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpAnd(LangParser.ExpAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expEq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpEq(LangParser.ExpEqContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expRel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpRel(LangParser.ExpRelContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expAdd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpAdd(LangParser.ExpAddContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expMul}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpMul(LangParser.ExpMulContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expUnary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpUnary(LangParser.ExpUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expPostfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpPostfix(LangParser.ExpPostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#expPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpPrimary(LangParser.ExpPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(LangParser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link LangParser#exps}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExps(LangParser.ExpsContext ctx);
}