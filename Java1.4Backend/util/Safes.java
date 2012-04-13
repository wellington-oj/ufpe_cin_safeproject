package util;

import javax.swing.tree.DefaultTreeModel;

import AST.Access;
import AST.AssignSimpleExpr;
import AST.Block;
import AST.BodyDecl;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.ConstructorDecl;
import AST.EQExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.IfStmt;
import AST.List;
import AST.MethodDecl;
import AST.Modifier;
import AST.Modifiers;
import AST.NullLiteral;
import AST.Opt;
import AST.ParameterDeclaration;
import AST.ReturnStmt;
import AST.Stmt;
import AST.SuperConstructorAccess;
import AST.SynchronizedStmt;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.BooleanLiteral;
import AST.SynchronizedStmt;
import AST.Dot;
import AST.ClassAccess;

public class Safes {

	public static boolean classFoiAdicionada = false;

	public Safes() {}
	
	//private static SafeManager instance;
	//private DefaultTreeModel tree;

	private static void createFields(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPrivate = new Modifier("private");
		Modifier modStatic = new Modifier("static");

		listaModifiers.addModifier(modPrivate);
		listaModifiers.addModifier(modStatic);

		FieldDeclaration instance = new FieldDeclaration(listaModifiers,new TypeAccess("SafeManager"),"instance");
		FieldDeclaration tree = new FieldDeclaration(listaModifiers,new TypeAccess("DefaultTreeModel"),"tree");

		retorno.add(instance);
		retorno.add(tree);

	}

	//	private SafeManager() {
	//		super();
	//		tree = new DefaultTreeModel(null,false);
	//	}

	private static void createConstrutor(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPrivate = new Modifier("private");
		listaModifiers.addModifier(modPrivate);

		Opt<Stmt> opt = new Opt<Stmt>();

		SuperConstructorAccess superConstrutor = new SuperConstructorAccess();
		ExprStmt exprStmt = new ExprStmt(superConstrutor);
		opt.addChild(exprStmt);

		List<Expr> listParameteresClassInstance = new List<Expr>();
		listParameteresClassInstance.add(new NullLiteral("null"));
		listParameteresClassInstance.add(new BooleanLiteral("false"));

		ClassInstanceExpr classInstance = new ClassInstanceExpr(new TypeAccess("DefaultTreeModel"), listParameteresClassInstance, new Opt<TypeDecl>() );
		AssignSimpleExpr expr = new AssignSimpleExpr(new VarAccess("tree"),classInstance);

		ExprStmt exprStmtTree= new ExprStmt(expr);

		List<Stmt> list = new List<Stmt>();
		list.add(exprStmtTree);

		Block block = new Block(list);

		ConstructorDecl contrutor = new ConstructorDecl(listaModifiers,"SafeManager",new List<ParameterDeclaration>(),
				new List<Access>(), opt, block);

		retorno.add(contrutor);
	}

	//	public static SafeManager getInstance(){
	//		synchronized(SafeManager.class){
	//			if (instance == null) {
	//				instance = new SafeManager();			
	//			}
	//		}
	//		return instance;
	//	}
	
	private static void createGetInstance(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modStatic = new Modifier("static");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modStatic);

		List<Stmt> stmtList = new List<Stmt>();
		
		Dot dot = new Dot(new TypeAccess("SafeManager"),new ClassAccess());
	//TODO
		//	SynchronizedStmt syncStmt = new SynchronizedStmt(dot, createBlocoIf());
		
		
		stmtList.add(createBlocoIf());

		ReturnStmt returnStmt = new ReturnStmt(new VarAccess("instance"));

		stmtList.add(returnStmt);

		Block principalBloco = new Block(stmtList);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		MethodDecl method = new MethodDecl(listaModifiers,
				new TypeAccess("SafeManager"),
				"getInstance",
				new List<ParameterDeclaration>(), 
				new List<Access>(), 
				opt);

		retorno.add(method);
	}

	static public List<TypeDecl> createAndAddClassDecl(List<TypeDecl> p2){

		Opt<Access> opt = new Opt<Access>();
		List<Access> listaAccess = new List<Access>();
		List<BodyDecl> listaBodyDecl = createListBodyDecl();
		p2.add(new ClassDecl(new Modifiers(),"SafeManager",opt,listaAccess, listaBodyDecl));
		return p2;
	}

	private static List<BodyDecl> createListBodyDecl() {

		List<BodyDecl> retorno = new List<BodyDecl>();

		createFields(retorno);
		createConstrutor(retorno);
		createGetInstance(retorno);
		createAddSafe(retorno);
		createRemoveSafe(retorno);
		createIsSafe(retorno);

		return retorno;
	}

	private static IfStmt createBlocoIf() {
		Block blocoIf = createBlocoIfNull();

		EQExpr eqExpr = new EQExpr(new VarAccess("instance"),new NullLiteral("null"));
		IfStmt ifStmt = new IfStmt(eqExpr,blocoIf);
		return ifStmt;
	}

	private static Block createBlocoIfNull() {
		Block bloco = new Block();
		Block blocoSync = new Block();

		ClassInstanceExpr classInstance = new ClassInstanceExpr(new TypeAccess("SafeManager"), new List<Expr>(), new Opt<TypeDecl>() );

		AssignSimpleExpr assignSimple = new AssignSimpleExpr(new VarAccess("instance"),classInstance);

		ExprStmt assignStmt = new ExprStmt(assignSimple);

		blocoSync.addStmt(assignStmt);

		SynchronizedStmt sync = new SynchronizedStmt(new VarAccess("instance"), blocoSync);

		bloco.addStmt(sync);

		return bloco;
	}


	//	public synchronized void addSafe(SafeNode newChild,SafeNode parent){
	//		if(tree.getRoot() == null){
	//			tree.setRoot(newChild);
	//		}
	//		else{
	//			tree.insertNodeInto(newChild,(MutableTreeNode) parent, 0);
	//		}
	//	}

	private static void createAddSafe(List<BodyDecl> retorno) {

		//		Modifiers listaModifiers = new Modifiers();
		//		Modifier modPublic = new Modifier("public");
		//		Modifier modSync = new Modifier("synchronized"); 
		//		
		//		listaModifiers.addModifier(modPublic);
		//		listaModifiers.addModifier(modSync);
		//		
		//		List<Stmt> stmtList = new List<Stmt>();
		//
		//		
		//		PostIncExpr expr = new PostIncExpr(new VarAccess("mapping"));
		//		ExprStmt exprStmt = new ExprStmt(expr);
		//		
		//		stmtList.add(exprStmt);
		//		
		//		Block principalBloco = new Block(stmtList);
		//		
		//		Opt<Block> opt = new Opt<Block>();
		//		opt.addChild(principalBloco);
		//			
		//		MethodDecl method = new MethodDecl(listaModifiers,
		//				new PrimitiveTypeAccess("void"),
		//				"addSafe",
		//				new List<ParameterDeclaration>(), 
		//				new List<Access>(), 
		//				opt);
		//		
		//		retorno.add(method);
	}

	//	public synchronized void removeSafe(SafeNode safeNode){
	//		if(tree.getRoot() != null){
	//			tree.removeNodeFromParent(safeNode);
	//			System.out.println("testando");
	//		}
	//		else if(tree.getRoot().equals(safeNode)){
	//			tree.setRoot(null);
	//		}
	//		else{
	//			System.out.println("error");
	//		}
	//	}
	private static void createRemoveSafe(List<BodyDecl> retorno) {
		//		Modifiers listaModifiers = new Modifiers();
		//		Modifier modPublic = new Modifier("public");
		//		Modifier modSync = new Modifier("synchronized"); 
		//		
		//		listaModifiers.addModifier(modPublic);
		//		listaModifiers.addModifier(modSync);
		//		
		//		List<Stmt> stmtList = new List<Stmt>();
		//
		//		GTExpr gtExpr = new GTExpr(new VarAccess("mapping"), new IntegerLiteral(0));
		//		Block blocoIf = new Block();
		//		PostDecExpr decExpr = new PostDecExpr(new VarAccess("mapping"));
		//		ExprStmt exprStmt = new ExprStmt(decExpr);
		//		blocoIf.addStmt(exprStmt);
		//		IfStmt ifStmt = new IfStmt(gtExpr, blocoIf);
		//		
		//		stmtList.add(ifStmt);
		//		Block principalBloco = new Block(stmtList);
		//		
		//		Opt<Block> opt = new Opt<Block>();
		//		opt.addChild(principalBloco);
		//			
		//		MethodDecl method = new MethodDecl(listaModifiers,
		//				new PrimitiveTypeAccess("void"),
		//				"removeSafe",
		//				new List<ParameterDeclaration>(), 
		//				new List<Access>(), 
		//				opt);
		//		
		//		retorno.add(method);
		//		
	}

	//	public synchronized boolean isSafe(Thread thread){
	//		if(getSafe((SafeNode) tree.getRoot(),thread) != null){
	//			return true;
	//		}
	//		return false;
	//	}

	private static void createIsSafe(List<BodyDecl> retorno) {
		//		Modifiers listaModifiers = new Modifiers();
		//		Modifier modPublic = new Modifier("public");
		//		Modifier modSync = new Modifier("synchronized"); 
		//		
		//		listaModifiers.addModifier(modPublic);
		//		listaModifiers.addModifier(modSync);
		//		
		//		List<Stmt> stmtList = new List<Stmt>();
		//
		//		GTExpr gtExpr = new GTExpr(new VarAccess("mapping"), new IntegerLiteral(0));
		//		Block blocoIf = new Block();
		//		ReturnStmt returnStmt = new ReturnStmt(new BooleanLiteral(true));
		//		blocoIf.addStmt(returnStmt);
		//		
		//		Opt<Stmt> optIf = new Opt<Stmt>();
		//		ReturnStmt returnStmtFalse = new ReturnStmt(new BooleanLiteral(false));
		//		optIf.addChild(returnStmtFalse);
		//		
		//		IfStmt ifStmt = new IfStmt(gtExpr, blocoIf,optIf);
		//		
		//		stmtList.add(ifStmt);
		//		Block principalBloco = new Block(stmtList);
		//		
		//		Opt<Block> opt = new Opt<Block>();
		//		opt.addChild(principalBloco);
		//			
		//		MethodDecl method = new MethodDecl(listaModifiers,
		//				new PrimitiveTypeAccess("boolean"),
		//				"isSafe",
		//				new List<ParameterDeclaration>(), 
		//				new List<Access>(), 
		//				opt);
		//		
		//		retorno.add(method);
		//		
	}
}