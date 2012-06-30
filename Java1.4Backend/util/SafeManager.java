package util;

import AST.Access;
import AST.AssignSimpleExpr;
import AST.Block;
import AST.BodyDecl;
import AST.CastExpr;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.ConstructorDecl;
import AST.EQExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.ForStmt;
import AST.IfStmt;
import AST.IntegerLiteral;
import AST.LTExpr;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.Modifier;
import AST.Modifiers;
import AST.NEExpr;
import AST.NullLiteral;
import AST.Opt;
import AST.ParameterDeclaration;
import AST.PostIncExpr;
import AST.PrimitiveTypeAccess;
import AST.ReturnStmt;
import AST.Stmt;
import AST.SuperConstructorAccess;
import AST.SynchronizedStmt;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.BooleanLiteral;
import AST.Dot;
import AST.ClassAccess;
import AST.VariableDeclaration;

public class SafeManager {

	public static boolean classFoiAdicionada = false;

	public SafeManager() {}

	//private static SafeManager instance;
	//private DefaultTreeModel tree;

	private static void createFields(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPrivate = new Modifier("private");
		Modifier modStatic = new Modifier("static");

		listaModifiers.addModifier(modPrivate);

		FieldDeclaration tree = 
				new FieldDeclaration(
						listaModifiers
						,new TypeAccess("DefaultTreeModel")
						,"tree");

		listaModifiers.addModifier(modStatic);
		FieldDeclaration instance = 
				new FieldDeclaration(
						listaModifiers
						,new TypeAccess("SafeManager")
						,"instance");


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

		SuperConstructorAccess superConstrutor = 
				new SuperConstructorAccess();
		ExprStmt exprStmt = 
				new ExprStmt(superConstrutor);
		opt.addChild(exprStmt);

		List<Expr> listParameteresClassInstance = 
				new List<Expr>();

		listParameteresClassInstance.add(
				new NullLiteral("null"));
		listParameteresClassInstance.add(
				new BooleanLiteral("false"));
		ClassInstanceExpr classInstance = 
				new ClassInstanceExpr(
						new TypeAccess("DefaultTreeModel")
						,listParameteresClassInstance
						,new Opt<TypeDecl>());

		//		tree = new DefaultTreeModel(null,false);
		AssignSimpleExpr expr = 
				new AssignSimpleExpr(
						new VarAccess("tree")
						,classInstance);

		ExprStmt exprStmtTree= new ExprStmt(expr);

		Block block = new Block();
		block.addStmt(exprStmtTree);

		ConstructorDecl contrutor = 
				new ConstructorDecl(
						listaModifiers
						,"SafeManager"
						,new List<ParameterDeclaration>()
						,new List<Access>()
						,opt
						,block);

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

	private static void 
	createGetInstance(List<BodyDecl> retorno) {

		Block mainBlock = new Block();

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modStatic = new Modifier("static");

		TypeAccess typeAccess = new TypeAccess("SafeManager");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modStatic);

		Dot dot = new Dot(typeAccess,new ClassAccess());

		SynchronizedStmt syncStmt = 
				new SynchronizedStmt(
						dot
						,createBlocoIfGetInstance());

		mainBlock.addStmt(syncStmt);

		ReturnStmt returnStmt = 
				new ReturnStmt(new VarAccess("instance"));
		mainBlock.addStmt(returnStmt);

		Opt<Block> opt = new Opt<Block>();

		opt.addChild(mainBlock);

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,typeAccess
						,"getInstance"
						,new List<ParameterDeclaration>()
						,new List<Access>()
						,opt);

		retorno.add(method);
	}

	//synchronized(SafeManager.class){
	//			if (instance == null) {
	//				instance = new SafeManager();			
	//			}
	//		}

	private static Block createBlocoIfGetInstance() {
		Block blocoIf = new Block();

		ClassInstanceExpr classInstance = 
				new ClassInstanceExpr(
						new TypeAccess("SafeManager")
						,new List<Expr>()
						,new Opt<TypeDecl>());

		AssignSimpleExpr assignSimple = 
				new AssignSimpleExpr(
						new VarAccess("instance")
						,classInstance);

		ExprStmt assignStmt = new ExprStmt(assignSimple);
		
		blocoIf.addStmt(assignStmt);

		EQExpr eqExpr = new EQExpr(
				new VarAccess("instance")
				,new NullLiteral("null"));
		IfStmt ifStmt = new IfStmt(eqExpr,blocoIf);
		Block retorno = new Block();
		retorno.addStmt(ifStmt);
		return retorno;
	}

	//classname SafeManager {...}

	public static  List<TypeDecl> 
	createAndAddClassDecl(List<TypeDecl> list){

		Opt<Access> opt = new Opt<Access>();
		List<Access> listaAccess = new List<Access>();
		List<BodyDecl> listaBodyDecl = createListBodyDecl();
		list.add(
				new ClassDecl(
						new Modifiers(),
						"SafeManager",
						opt,
						listaAccess,
						listaBodyDecl));
		return list;
	}

	private static List<BodyDecl> createListBodyDecl() {

		List<BodyDecl> retorno = new List<BodyDecl>();

		createFields(retorno);
		createConstrutor(retorno);
		createGetInstance(retorno);
		createAddSafe(retorno);
		createGetRoot(retorno);
		createGetTree(retorno);
		createRemoveSafe(retorno);
		createIsSafe(retorno);
		createGetSafe(retorno);

		return retorno;
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

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		IfStmt ifStmt = createIfStmtAddSafe();
		principalBloco.addStmt(ifStmt);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("SafeNode")
						,"newChild"));
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("SafeNode")
						,"parent"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"addSafe"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);
	}


	//	public synchronized SafeNode getRoot(){
	//	return (SafeNode) tree.getRoot();
	//}

	private static void createGetRoot(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		TypeAccess returnType = new TypeAccess("SafeNode");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();
		
		principalBloco.addStmt(new ReturnStmt(new CastExpr(
				new TypeAccess("SafeNode"),
				new Dot(new VarAccess("tree"),
						new MethodAccess(
								"getRoot",
								new List<Expr>())))));

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,returnType
						,"getRoot"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);
	}

	//public synchronized DefaultTreeModel getTree(){
	//	return tree;
	//}

	private static void createGetTree(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		TypeAccess returnType = 
				new TypeAccess("DefaultTreeModel");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		principalBloco.addStmt(
				new ReturnStmt(
						new VarAccess("tree")));

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,returnType
						,"getTree"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);
	}

	//		if(tree.getRoot() == null){
	//			tree.setRoot(newChild);
	//		}
	//		else{
	//			tree.insertNodeInto(newChild,(MutableTreeNode) parent, 0);
	//		}

	private static IfStmt createIfStmtAddSafe() {
		Block blockIf = new Block();
		List<Expr> parametersIf = new List<Expr>();
		parametersIf.add(new VarAccess("newChild"));

		//tree.setRoot(newChild);
		ExprStmt exprStmtIf = 
				new ExprStmt(
						new Dot(
								new VarAccess("tree")
								,new MethodAccess(
										"setRoot"
										,parametersIf )));
		blockIf.addStmt(exprStmtIf);
		//tree.getRoot() == null
		EQExpr eqExpr = 
				new EQExpr(new Dot(
						new VarAccess("tree")
						,new MethodAccess(
								"getRoot"
								,new List<Expr>()))
				,new NullLiteral("null"));

		Opt<Stmt> opt = new Opt<Stmt>();
		Block blockElse = new Block();

		List<Expr> parametersElse = new List<Expr>();
		VarAccess varAccess = new VarAccess("newChild");
		//(MutableTreeNode) parent
		CastExpr castExpr = 
				new CastExpr(
						new TypeAccess("MutableTreeNode")
						,new VarAccess("parent"));
		IntegerLiteral integerLiteral = new IntegerLiteral(0);
		parametersElse.add(varAccess);
		parametersElse.add(castExpr);
		parametersElse.add(integerLiteral);

		//.insertNodeInto(newChild,(MutableTreeNode) parent, 0);
		MethodAccess methodAccess = 
				new MethodAccess(
						"insertNodeInto"
						,parametersElse);

		//tree.insertNodeInto(newChild,(MutableTreeNode) parent, 0);	
		ExprStmt exprStmtElse = 
				new ExprStmt(new Dot(
						new VarAccess("tree")
						,methodAccess));

		blockElse.addStmt(exprStmtElse);

		opt.addChild(blockElse);
		IfStmt ifStmt = new IfStmt(eqExpr,blockIf,opt);
		return ifStmt;
	}

	//	public synchronized void removeSafe(SafeNode safeNode){
	//		if(tree.getRoot() != null){
	//			tree.removeNodeFromParent(safeNode);
	//		}
	//		else if(tree.getRoot().equals(safeNode)){
	//			tree.setRoot(null);
	//		}
	//	}

	private static void createRemoveSafe(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);

		Block mainBlock = new Block();

		//if(tree.getRoot() != null){...}
		IfStmt ifStmt = createIfStmtRemoveSafe();

		mainBlock.addStmt(ifStmt);
		Opt<Block> opt = new Opt<Block>();
		opt.addChild(mainBlock);

		List<ParameterDeclaration> parameters =
				new List<ParameterDeclaration>();

		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("SafeNode")
						,"safeNode"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,new PrimitiveTypeAccess("void")
						,"removeSafe"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}

	//if(tree.getRoot() != null){
	//			tree.removeNodeFromParent(safeNode);
	//		}
	//		else if(tree.getRoot().equals(safeNode)){
	//			tree.setRoot(null);
	//		}

	private static IfStmt createIfStmtRemoveSafe() {
		Block blockIf = new Block();

		VarAccess safeNodeAccess = 
				new VarAccess("safeNode");
		VarAccess treeAccess = 
				new VarAccess("tree");

		List<Expr> parametersIf = new List<Expr>();
		parametersIf.add(safeNodeAccess);

		VarAccess varAccessTree = new VarAccess("tree");

		//tree.removeNodeFromParent(safeNode);		
		ExprStmt exprStmt = 
				new ExprStmt(new Dot(
						varAccessTree
						, new MethodAccess(
								"removeNodeFromParent"
								,parametersIf)));

		blockIf.addStmt(exprStmt);
		//tree.getRoot() != null
		NEExpr neExpr = 
				new NEExpr(
						new Dot(
								new VarAccess("tree")
								,new MethodAccess(
										"getRoot"
										,new List<Expr>()))
						,new NullLiteral("null"));

		Opt<Stmt> opt = new Opt<Stmt>();
		Block blockElse = new Block();

		List<Expr> parametersElse = new List<Expr>();
		parametersElse.add(safeNodeAccess);

		//tree.getRoot().equals(safeNode)
		Dot dotInt = new Dot(
				new VarAccess("tree"),
				new Dot(
						new MethodAccess(
								"getRoot"
								,new List<Expr>())
						,new MethodAccess(
								"equals"
								,parametersElse)));



		List<Expr> parameters2 = new List<Expr>();
		NullLiteral nullLiteral = new NullLiteral("null");
		parameters2.add(nullLiteral);

		//		else if(tree.getRoot().equals(safeNode)){
		//			tree.setRoot(null);
		//		}

		IfStmt ifStmtElse = 
				new IfStmt(dotInt,
						new ExprStmt(new Dot(
								treeAccess
								,new MethodAccess(
										"setRoot"
										,parameters2))));

		blockElse.addStmt(ifStmtElse);

		opt.addChild(blockElse);
		IfStmt ifStmt = new IfStmt(neExpr,blockIf,opt);
		return ifStmt;
	}

	//	public synchronized boolean isSafe(Thread thread){
	//		if(getSafe((SafeNode) tree.getRoot(),thread) != null){
	//			return true;
	//		}
	//		return false;
	//	}

	private static void createIsSafe(List<BodyDecl> retorno) {
		Block mainBlock = new Block();

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Thread")
						,"thread"));

		List<Expr> getSafeParameters = new List<Expr>();
		CastExpr castExpr = 
				new CastExpr(
						new TypeAccess("SafeNode")
						,new Dot(
								new VarAccess("tree")
								,new MethodAccess(
										"getRoot"
										,new List<Expr>())));
		VarAccess threadAccess = new VarAccess("thread");
		getSafeParameters.add(castExpr);
		getSafeParameters.add(threadAccess);

		NEExpr neExpr = 
				new NEExpr(
						new MethodAccess(
								"getSafe"
								,getSafeParameters)
						, new NullLiteral("null"));

		Block blockIf = new Block();

		ReturnStmt returnStmtTrue = 
				new ReturnStmt(new BooleanLiteral(true));

		blockIf.addStmt(returnStmtTrue);


		IfStmt ifStmt = new IfStmt(neExpr,blockIf);
		mainBlock.addStmt(ifStmt);

		ReturnStmt returnStmtFalse = 
				new ReturnStmt(new BooleanLiteral(false));
		mainBlock.addStmt(returnStmtFalse);


		Opt<Block> opt = new Opt<Block>();
		opt.addChild(mainBlock);

		MethodDecl method = 
				new MethodDecl(
						listaModifiers,
						new PrimitiveTypeAccess("boolean"),
						"isSafe",
						parameters, 
						new List<Access>(), 
						opt);

		retorno.add(method);
	}

	//	public SafeNode getSafe(SafeNode originalNode,Thread thread){
	//		if(originalNode == null){
	//			return null;
	//		}
	//		else if(originalNode.existsThread(thread)){
	//			return (SafeNode) originalNode;
	//		}
	//		SafeNode node = (SafeNode) tree.getChild(originalNode,0);
	//		for (int i = 0; i < tree.getChildCount(originalNode); i++) {
	//
	//			node = (SafeNode) tree.getChild(originalNode, i);
	//			if(node.existsThread(thread)){
	//				return node;
	//			}
	//		}
	//		return getSafe(node,thread);
	//	}


	private static void createGetSafe(List<BodyDecl> retorno) {

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		listaModifiers.addModifier(modPublic);
		Block mainBlock = new Block();

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("SafeNode")
						,"originalNode"));
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Thread")
						,"thread"));


		IfStmt ifOriginalIsNull = getIfStmtGetSafe();

		mainBlock.addStmt(ifOriginalIsNull);

		Opt<Expr> opt = new Opt<Expr>();
		List<Expr> list = new List<Expr>();
		list.add(new VarAccess("originalNode"));
		list.add(new IntegerLiteral(0));
		opt.addChild(
				new CastExpr(
						new TypeAccess("SafeNode")
						, new Dot(
								new VarAccess("tree")
								,new MethodAccess(
										"getChild"
										,list))));
		VariableDeclaration setNode =
				new VariableDeclaration(
						new Modifiers()
						, new TypeAccess("SafeNode")
						, "node"
						, opt);
		mainBlock.addStmt(setNode);

		List<Stmt> listVariableInitFor = new List<Stmt>();

		VariableDeclaration variableFor = 
				new VariableDeclaration(
						new PrimitiveTypeAccess("int")
						,"i"
						,new IntegerLiteral(0));
		listVariableInitFor.add(variableFor);

		List<Expr> parametersCondictionFor = new List<Expr>();
		parametersCondictionFor.add(new VarAccess("originalNode"));
		Opt<Expr> listCondictionFor = new Opt<Expr>();
		LTExpr ltExpr = 
				new LTExpr(
						new VarAccess("i")
						,new Dot(
								new VarAccess("tree")
								,new MethodAccess(
										"getChildCount"
										,parametersCondictionFor)));
		listCondictionFor.addChild(ltExpr);

		List<Stmt> listDoFor = new List<Stmt>();
		ExprStmt exprStmt = new ExprStmt(new PostIncExpr(new VarAccess("i")));
		listDoFor.add(exprStmt);

		Block blockFor = new Block();

		List<Expr> assignParameters = new List<Expr>();
		assignParameters.add(new VarAccess("originalNode"));
		assignParameters.add(new VarAccess("i"));
		AssignSimpleExpr assignNode = 
				new AssignSimpleExpr(
						new VarAccess("node")
						, new CastExpr(
								new TypeAccess("SafeNode"),
								new Dot(
										new VarAccess("tree")
										,new MethodAccess(
												"getChild"
												,assignParameters))));
		ExprStmt setNodeFor = new ExprStmt(assignNode);
		blockFor.addStmt(setNodeFor);
		List<Expr> ifForParameters = new List<Expr>();
		ifForParameters.add(new VarAccess("thread"));

		Block blockIfFor = new Block();
		blockIfFor.addStmt(new ReturnStmt(new VarAccess("node")));

		IfStmt ifFor = new IfStmt(
				new Dot(
						new VarAccess("node")
						,new MethodAccess(
								"existsThread"
								,ifForParameters))
				, blockIfFor);

		blockFor.addStmt(ifFor);

		ForStmt forStmt = new ForStmt(listVariableInitFor,listCondictionFor,listDoFor,blockFor);

		mainBlock.addStmt(forStmt);

		List<Expr> returnParameters = new List<Expr>();
		returnParameters.add(new VarAccess("node"));
		returnParameters.add(new VarAccess("thread"));
		ReturnStmt returnStmt = 
				new ReturnStmt(
						new MethodAccess(
								"getSafe"
								,returnParameters));

		mainBlock.addStmt(returnStmt);


		Opt<Block> mainOpt = new Opt<Block>();
		mainOpt.addChild(mainBlock);

		MethodDecl method = 
				new MethodDecl(
						listaModifiers,
						new TypeAccess("SafeNode"),
						"getSafe",
						parameters, 
						new List<Access>(), 
						mainOpt);

		retorno.add(method);
	}

	//if(originalNode == null){
	//			return null;
	//		}
	//		else if(originalNode.existsThread(thread)){
	//			return (SafeNode) originalNode;
	//		}

	private static IfStmt getIfStmtGetSafe() {
		Block blockIfOriginalElse = new Block();

		//return (SafeNode) originalNode;
		ReturnStmt returnElse = 
				new ReturnStmt(
						new CastExpr(
								new TypeAccess("SafeNode")
								,new VarAccess("originalNode")));

		blockIfOriginalElse.addStmt(returnElse);

		List<Expr> parametersIfOriginalElse = new List<Expr>();
		parametersIfOriginalElse.add(new VarAccess("thread"));

		//else if(originalNode.existsThread(thread)){...}

		IfStmt ifOriginalElse = 
				new IfStmt(
						new Dot(
								new VarAccess("originalNode")
								,new MethodAccess(
										"existsThread"
										,parametersIfOriginalElse))
						,blockIfOriginalElse);
		Opt<Stmt> elseStmt = new Opt<Stmt>();
		elseStmt.addChild(ifOriginalElse);

		Block blockOriginalNull = new Block();

		//return null;
		ReturnStmt returnOriginalNull = 
				new ReturnStmt(new NullLiteral("null"));
		blockOriginalNull.addStmt(returnOriginalNull);

		//if(originalNode == null){...}
		IfStmt ifOriginalIsNull = 
				new IfStmt(
						new EQExpr(
								new VarAccess("originalNode")
								,new NullLiteral("null"))
						,blockOriginalNull
						,elseStmt);
		return ifOriginalIsNull;
	}

}