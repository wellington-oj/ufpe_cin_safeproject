package util;

import AST.ASTNode;
import AST.Access;
import AST.AssignSimpleExpr;
import AST.BasicCatch;
import AST.Block;
import AST.BooleanLiteral;
import AST.CatchClause;
import AST.ClassAccess;
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
import AST.ParseName;
import AST.PrimitiveTypeAccess;
import AST.SimpleSet;
import AST.SingleTypeImportDecl;
import AST.Stmt;
import AST.StringLiteral;
import AST.ThisAccess;
import AST.TryStmt;
import AST.TypeAccess;
import AST.VarAccess;
import AST.VariableDeclaration;

public class Recursos {

	//Usado em MethodDecl

	//Criando o if(SafeManager.getInstance().isSafe(Thread thread)){...} para mudar o fluxo da thread caso ele não esteja num bloco safe.

	static public final List<Expr> emptyList = new List<Expr>();
	static public int contador = 0;
	private static List<CatchClause> clauses = new List<CatchClause>();
	
	public static Block createIfStmtMethodDecl(Block blocoAntigo){

		MethodAccess getInstance = new MethodAccess("getInstance", emptyList);
		Dot getCurrentThread = new Dot(new TypeAccess("Thread"),new MethodAccess("currentThread",emptyList));
		MethodAccess isSafe = new MethodAccess("isSafe", new List<Expr>().add(getCurrentThread));
		Dot dot2 = new Dot(getInstance,isSafe);
		Dot dot1 = new Dot(new TypeAccess("SafeManager"),dot2);
		IfStmt ifStmt = new IfStmt(dot1,addTryCatchMethodDecl(blocoAntigo),createElse(blocoAntigo));
		return (new Block(new List<Stmt>().add(ifStmt)));
	}


	//	SafeNode safeNode = new SafeNode();

	public static VariableDeclaration newSafeNode(){

		Opt<Expr> opt = new Opt<Expr>();

		opt.addChild(new ClassInstanceExpr(
				new TypeAccess("SafeNode")
				,new List<Expr>()));

		VariableDeclaration retorno =
				new VariableDeclaration(
						new Modifiers()
						, new TypeAccess("SafeNode")
						, "safeNode"
						, opt);

		return retorno;

	}

	//	SafeManager.getInstance().addSafe(
	//			safeNode
	//			,SafeManager.getInstance().getSafe(
	//					SafeManager.getInstance().getRoot()
	//					,Thread.currentThread()));

	public static ExprStmt addSafe(){

		TypeAccess safeManagerAccess = new TypeAccess("SafeManager");
		MethodAccess getInstanceAccess = 
				new MethodAccess(
						"getInstance"
						,emptyList);

		Dot getSafe = getSafe();

		List<Expr> parametersAddSafe = new List<Expr>();
		parametersAddSafe.add(new VarAccess("safeNode"));
		parametersAddSafe.add(getSafe);

		ExprStmt exprStmt = new ExprStmt(
				new Dot(
						safeManagerAccess
						, new Dot(
								getInstanceAccess
								,new MethodAccess(
										"addSafe"
										,parametersAddSafe))));

		return exprStmt;
	}

	//safeNode.syncUp();

	public static ExprStmt createSyncUp(){
		ExprStmt retorno = new ExprStmt(
				new Dot(
						new VarAccess("safeNode")
						, new MethodAccess(
								"syncUp"
								, emptyList)));
		return retorno;
	}

	//	if(SafeManager.getInstance().isSafe(Thread.currentThread())){
	//		SafeManager.getInstance().getSafe(
	//			SafeManager.getInstance()
	//				.getRoot(),Thread.currentThread()).addThread(null);
	//	}

	public static ExprStmt createIfSafeAddThread(String thread){

		Dot internalDot = new Dot(
				new TypeAccess("Thread")
				, new MethodAccess(
						"currentThread"
						,emptyList));

		Dot externalDot = new Dot(
				new MethodAccess(
						"getInstance"
						, emptyList)
				,new MethodAccess(
						"isSafe"
						, new List<Expr>().add(internalDot)));


		Dot getSafe = getSafe();

		Dot addThread = new Dot(
				getSafe,
				new MethodAccess("addThread",new List<Expr>().add(new VarAccess(thread))));


		return new ExprStmt(addThread);
	}


	private static Dot getSafe() {
		TypeAccess safeManagerAccess = new TypeAccess("SafeManager");
		MethodAccess getInstanceAccess = 
				new MethodAccess(
						"getInstance"
						,emptyList);

		List<Expr> parametersGetSafe = new List<Expr>();

		parametersGetSafe.add(
				new Dot(
						safeManagerAccess
						,new Dot(
								getInstanceAccess
								,new MethodAccess(
										"getRoot"
										,emptyList))));
		parametersGetSafe.add(
				new Dot(
						new TypeAccess("Thread")
						,new MethodAccess(
								"currentThread"
								,emptyList)));

		Dot getSafe = new Dot(
				safeManagerAccess
				,new Dot(
						getInstanceAccess
						, new MethodAccess(
								"getSafe"
								, parametersGetSafe)));
		return getSafe;
	}


	private static Block addTryCatchMethodDecl(Block blocoAntigo) {
		Opt<Block> opt = new Opt<Block>();
		Block block = new Block();
		block.addStmt(tryWakeUp());
		opt.addChild(block);
		TryStmt tryStmt = new TryStmt(modifedOldBlock(blocoAntigo),createCatchMethodDecl(),opt);
		return (new Block(new List<Stmt>().add(tryStmt)));
	}

	private static Block modifedOldBlock(Block oldBlock){
		
		List<Stmt> lista = new List<Stmt>();
		for (int i = 0; i < oldBlock.getChild(0).getNumChild(); i++) {
			lista.add((Stmt) oldBlock.getChild(0).getChild(i));
		}
		Block newBlock = new Block(lista);
		
		for(int i = 0; i < oldBlock.getNumStmt(); i++) {

			Stmt stmtAtual = oldBlock.getStmt(i);
			boolean ehClassInstance = false;
			String args = stmtAtual.toString();
			Class<? extends Stmt> classe = stmtAtual.getClass();
			if(classe.equals(ExprStmt.class)){
				if(stmtAtual.getChild(0).getClass().equals(Dot.class)){
					if(stmtAtual.getChild(0).getChild(0).getClass().equals(ClassInstanceExpr.class))
						ehClassInstance = true;
				}
			}

			//TODO
			//isso precisa ser urgentemente melhorado
			if(args.contains((CharSequence) "start")){

				newBlock.addStmt(Recursos.createIfSafeAddThread(
						((Dot) ((ExprStmt) stmtAtual).getExpr()).getLeft().toString()),i+1);
				if((ehClassInstance)){

				}else if(!args.contains((CharSequence) "variavelNova")){
				}
			}
		}

		return newBlock;
	}
	//SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).tryWakeUp(Thread.currentThread());
	
	public static ExprStmt tryWakeUp(){
		return new ExprStmt(
				new Dot(getSafe()
						,new MethodAccess("tryWakeUp",new List<Expr>().add(
								new Dot(
										new TypeAccess("Thread"),
										new MethodAccess("currentThread",emptyList))))));
	}
	
	//SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).breakUp();
	
	public static ExprStmt createBreakUp(){
		return new ExprStmt(new Dot(getSafe(),new MethodAccess("breakUp",emptyList)));
	}
	
	private static List<CatchClause> createCatchMethodDecl(){
		List<CatchClause> listaCatch = new List<CatchClause>();
		
//		for (int i = 0; i < clauses.getNumChild(); i++) {
//			listaCatch.add(clauses.getChild(i));
//		}
		Modifiers mods = new Modifiers(new List<Modifier>());
		TypeAccess typeAcess = new TypeAccess("Exception");
		ParameterDeclaration param = new ParameterDeclaration(mods, typeAcess, "e");

		ExprStmt args = new ExprStmt(StmtSyso());

		Block bloco = new Block();
		bloco.addStmt(createBreakUp());
		bloco.addStmt(args);
		
		BasicCatch catchs = new BasicCatch(param,bloco);
		listaCatch.add(catchs);
		return listaCatch;

	}

	private static Expr StmtSyso() {
		List<Expr> lista = new List<Expr>();
		StringLiteral string = new StringLiteral("Thread Interrompida");
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
		TypeAccess typeAcess = new TypeAccess("InterruptedException");
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
		Opt opt = new Opt (classInst);
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

		SingleTypeImportDecl importArrayList = 
				new SingleTypeImportDecl(
						new Dot(
								new ParseName("java")
								, new Dot(new ParseName("util")
								, new ParseName("ArrayList"))));
		SingleTypeImportDecl importAtomicInteger = 
				new SingleTypeImportDecl(
						new Dot(
								new ParseName("java")
								, new Dot(new ParseName("util")
								, new Dot(new ParseName("concurrent")
								, new Dot(new ParseName("atomic")
								, new ParseName("AtomicInteger"))))));
		SingleTypeImportDecl importDefaultMutableTreeNode = 
				new SingleTypeImportDecl(
						new Dot(new ParseName("javax")
						, new Dot(new ParseName("swing")
						, new Dot(new ParseName("tree")
						,new ParseName("DefaultMutableTreeNode")))));
		SingleTypeImportDecl importDefaultTreeModel = 
				new SingleTypeImportDecl(
						new Dot(new ParseName("javax")
						,new Dot(new ParseName("swing")
						, new Dot(new ParseName("tree")
						,new ParseName("DefaultTreeModel")))));
		SingleTypeImportDecl importMutableTreeNode = 
				new SingleTypeImportDecl(
						new Dot(new ParseName("javax")
						,new Dot(new ParseName("swing")
						, new Dot(new ParseName("tree")
						,new ParseName("MutableTreeNode")))));

		p1.add(importArrayList);
		p1.add(importMutableTreeNode);
		p1.add(importDefaultMutableTreeNode);
		p1.add(importAtomicInteger);
		p1.add(importDefaultTreeModel);

		return p1;
	}


	public static void setCatchs(List<CatchClause> catchClauseList) {
		
		clauses = catchClauseList;
		
	}

}
