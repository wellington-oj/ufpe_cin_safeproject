package util;

import AST.ASTNode;
import AST.Access;
import AST.AssignSimpleExpr;
import AST.BasicCatch;
import AST.Block;
import AST.BooleanLiteral;
import AST.CatchClause;
import AST.ClassInstanceExpr;
import AST.Dot;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.IfStmt;
import AST.ImportDecl;
import AST.List;
import AST.MethodAccess;
import AST.Modifier;
import AST.Modifiers;
import AST.Opt;
import AST.ParameterDeclaration;
import AST.PrimitiveTypeAccess;
import AST.SingleTypeImportDecl;
import AST.Stmt;
import AST.StringLiteral;
import AST.TryStmt;
import AST.TypeAccess;
import AST.VarAccess;
import AST.VariableDeclaration;

public class Recursos {
	
	//Usado em MethodDecl
	
	public static Block createIfStmtMethodDecl(Block blocoAntigo){
		MethodAccess getInstance = new MethodAccess("getInstance", new List<Expr>());
		MethodAccess isSafe = new MethodAccess("isSafe", new List<Expr>());
		Dot dot2 = new Dot(getInstance,isSafe);
		Dot dot1 = new Dot(new TypeAccess("SafeManager"),dot2);

		IfStmt ifStmt = new IfStmt(dot1,addTryCatchMethodDecl(blocoAntigo),createElse(blocoAntigo));
		return (new Block(new List<Stmt>().add(ifStmt)));
	}
	
	private static Block addTryCatchMethodDecl(Block blocoAntigo) {
		TryStmt tryStmt = new TryStmt(blocoAntigo,createCatchMethodDecl(),new Opt<Block>());
		return (new Block(new List<Stmt>().add(tryStmt)));
	}
	
	private static List<CatchClause> createCatchMethodDecl(){
		List<CatchClause> listaCatch = new List<CatchClause>();
		Modifiers mods = new Modifiers(new List<Modifier>());
		TypeAccess typeAcess = new TypeAccess("Exception");
		ParameterDeclaration param = new ParameterDeclaration(mods, typeAcess, "e");

		List<Stmt> listaBlock = new List<Stmt>();

		ExprStmt args = new ExprStmt(StmtSyso());

		listaBlock.add(args);

		Block bloco = new Block(listaBlock);

		BasicCatch catchs = new BasicCatch(param,bloco);
		listaCatch.add(catchs);
		return listaCatch;

	}
	
	private static Expr StmtSyso() {
		List<Expr> lista = new List<Expr>();
		StringLiteral string = new StringLiteral("Deu pau");
		MethodAccess methodAcess = new MethodAccess("println", lista);
		VarAccess varAcess = new VarAccess("out");

		lista.add(string);
		Dot dot2 = new Dot();
		dot2.setRight(methodAcess);
		dot2.setLeft(varAcess);

		Dot dot = new Dot();
		dot.setLeft(new TypeAccess("System"));
		dot.setRight(dot2);
		return dot;
	}


	private static Opt<Stmt> createElse(Block blocoAntigo){
		Opt<Stmt> opt = new Opt<Stmt>();
		opt.addChild(blocoAntigo);
		return opt;
	}
	
	//Usado em ClassDecl
	
	public static FieldDeclaration getField() {
		List<Modifier> lista = new List<Modifier>();
		lista.add(new Modifier("static"));
		Modifiers mods = new Modifiers(lista);
		PrimitiveTypeAccess boolAcess = new PrimitiveTypeAccess("boolean");
		FieldDeclaration field = new FieldDeclaration(mods,boolAcess,"isSafe");
		return field;
	}
	
	//usado em vários lugares
	
	public static void recursividade(ASTNode args){

		System.out.println("filhos de " + args.getClass());
		
//		if (args.getClass() == SingleTypeImportDecl.class) {
//			System.out.println("AOPAOPAOPAOAPAOPAOP");
//			System.out.println(args);
//		}
		
		for (int j = 0; j < args.getNumChild(); j++) {
			ASTNode temp = args.getChild(j);
			System.out.println("	#"+j+" = " +temp.getClass());
		}

		for (int j = 0; j < args.getNumChild(); j++) {
			ASTNode temp = args.getChild(j);
			recursividade(temp);
		}
		System.out.println("FIM DOS FILHOS DE " + args.getClass());

	}
	
	//Usado no SafeStmt
	
	public static ExprStmt unsetSafe(ExprStmt stmt) {
		ClassInstanceExpr classInst = (ClassInstanceExpr) stmt.getChild(0).getChild(0);
		ClassInstanceExpr runzinho = (ClassInstanceExpr) classInst.getArgList().getChild(0);
		Dot dot = new Dot();
		TypeAccess typeAcess = new TypeAccess(runzinho.getAccess().toString());
		VarAccess varAcess = new VarAccess("isSafe");
		dot.setLeft(typeAcess);
		dot.setRight(varAcess);
		BooleanLiteral bool = new BooleanLiteral(false);
		AssignSimpleExpr assSimExpr = new AssignSimpleExpr(dot, bool);
		ExprStmt exprStmt = new ExprStmt(assSimExpr);
		return exprStmt;
	}
	
	public static ExprStmt setSafe(Stmt stmt) {
		ClassInstanceExpr classInst = (ClassInstanceExpr) stmt.getChild(0).getChild(0);
		ClassInstanceExpr runzinho = (ClassInstanceExpr) classInst.getArgList().getChild(0);
		Dot dot = new Dot();
		TypeAccess typeAcess = new TypeAccess(runzinho.getAccess().toString());
		VarAccess varAcess = new VarAccess("isSafe");
		dot.setLeft(typeAcess);
		dot.setRight(varAcess);
		BooleanLiteral bool = new BooleanLiteral(true);
		AssignSimpleExpr assSimExpr = new AssignSimpleExpr(dot, bool);
		ExprStmt exprStmt = new ExprStmt(assSimExpr);
		return exprStmt;
	}
	
	public static TryStmt inserirJoin(String param) {

		TryStmt tryStmt = new TryStmt(createBlockJoin(param),createCatchSafeStmt(),new Opt<Block>());
		return tryStmt;

	}
	
	private static Block createBlockJoin(String param) {
		Dot dot = new Dot();
		VarAccess acess = new VarAccess(param);
		MethodAccess methodAccess = new MethodAccess("join",new List<Expr>());
		dot.setLeft(acess);
		dot.setRight(methodAccess);
		ExprStmt expr = new ExprStmt(dot);
		List<Stmt> listBlock = new List<Stmt>();
		listBlock.add(expr);
		Block bloco = new Block(listBlock);
		return bloco;

	}
	
	private static List<CatchClause> createCatchSafeStmt(){
		List<CatchClause> listaCatch = new List<CatchClause>();
		Modifiers mods = new Modifiers(new List<Modifier>());
		TypeAccess typeAcess = new TypeAccess("Exception");
		ParameterDeclaration param = new ParameterDeclaration(mods, typeAcess, "e");

		List<Stmt> listaBlock = new List<Stmt>();

		ExprStmt args = new ExprStmt(StmtSyso());

		listaBlock.add(args);

		Block bloco = new Block(listaBlock);

		BasicCatch catchs = new BasicCatch(param,bloco);
		listaCatch.add(catchs);
		return listaCatch;

	}
	


	public static ExprStmt inserirStart(String param) {
		Dot dot = new Dot();
		VarAccess acess = new VarAccess(param);
		MethodAccess methodAccess = new MethodAccess("start",new List<Expr>());

		dot.setLeft(acess);
		dot.setRight(methodAccess);

		ExprStmt expr = new ExprStmt(dot);
		return expr;
	}
	
	public static VariableDeclaration inserirDecl(ExprStmt stmt,String param) {
		Modifiers mods = new Modifiers(new List<Modifier>());
		ClassInstanceExpr classInst = (ClassInstanceExpr) stmt.getChild(0).getChild(0);
		Opt opt = new Opt<ASTNode>(classInst);
		Access acesso = (Access) stmt.getChild(0).getChild(0).getChild(0);
		VariableDeclaration declar = new VariableDeclaration(mods, acesso, param,opt);
		return declar;
	}
	//import java.util.ArrayList;
	//import java.util.concurrent.atomic.AtomicInteger;
	//import javax.swing.tree.DefaultMutableTreeNode;
	//import javax.swing.tree.DefaultTreeModel;
	//import javax.swing.tree.MutableTreeNode;
	public static List<ImportDecl> createImports(List<ImportDecl> p1) {
//		SingleTypeImportDecl importArrayList = new SingleTypeImportDecl(new TypeAccess("java.util.ArrayList"));
//		SingleTypeImportDecl importAtomicInteger = new SingleTypeImportDecl(new TypeAccess("java.util.concurrent.atomic.AtomicInteger"));
//		SingleTypeImportDecl importDefaultMutableTreeNode = new SingleTypeImportDecl(new TypeAccess("javax.swing.tree.DefaultMutableTreeNode"));
//		SingleTypeImportDecl importDefaultTreeModel = new SingleTypeImportDecl(new TypeAccess("javax.swing.tree.DefaultTreeModel"));
//		SingleTypeImportDecl importMutableTreeNode = new SingleTypeImportDecl(new TypeAccess("javax.swing.tree.MutableTreeNode"));
//				
//		p1.add(importArrayList);
//		p1.add(importMutableTreeNode);
//		p1.add(importDefaultMutableTreeNode);
//		p1.add(importAtomicInteger);
//		p1.add(importDefaultTreeModel);
		return p1;
	}
	
}
