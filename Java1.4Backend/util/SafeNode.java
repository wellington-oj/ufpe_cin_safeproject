package util;

import AST.Access;
import AST.AssignSimpleExpr;
import AST.BasicCatch;
import AST.Block;
import AST.BodyDecl;
import AST.BooleanLiteral;
import AST.CastExpr;
import AST.CatchClause;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.ConstructorDecl;
import AST.Dot;
import AST.EQExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.ForStmt;
import AST.GTExpr;
import AST.IfStmt;
import AST.IntegerLiteral;
import AST.LTExpr;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.Modifier;
import AST.Modifiers;
import AST.NullLiteral;
import AST.Opt;
import AST.ParExpr;
import AST.ParameterDeclaration;
import AST.PostIncExpr;
import AST.PrimitiveTypeAccess;
import AST.ReturnStmt;
import AST.Stmt;
import AST.SuperConstructorAccess;
import AST.ThisAccess;
import AST.TryStmt;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.VariableDeclaration;
import AST.WhileStmt;

public class SafeNode {

	SafeNode(){}

	//class SafeNode extends DefaultMutableTreeNode{...}
	public static  List<TypeDecl> 
	createAndAddClassDecl(List<TypeDecl> list){

		Opt<Access> opt = new Opt<Access>();
		opt.addChild(new TypeAccess("DefaultMutableTreeNode"));
		List<Access> listaAccess = new List<Access>();
		List<BodyDecl> listaBodyDecl = createListBodyDecl();
		list.add(
				new ClassDecl(
						new Modifiers()
						,"SafeNode"
						,opt
						,listaAccess
						,listaBodyDecl));
		return list;
	}

	private static List<BodyDecl> createListBodyDecl() {
		List<BodyDecl> retorno = new List<BodyDecl>();
		createFields(retorno);
		createConstrutor(retorno);
		createAddThread(retorno);
		createTryWakeUp(retorno);
		createAddException(retorno);
		createSyncUp(retorno);
		createBreakUp(retorno);
		createExistsThread(retorno);
		createHasException(retorno);
		return retorno;
	}

	//	private ArrayList threads;
	//	private ArrayList catchExceptions;
	//	private AtomicInteger index = new AtomicInteger(0);

	private static void createFields(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPrivate = new Modifier("private");

		listaModifiers.addModifier(modPrivate);

		FieldDeclaration threads = 
				new FieldDeclaration(
						listaModifiers
						,new TypeAccess("ArrayList")
						,"threads");

		FieldDeclaration catchExceptions = 
				new FieldDeclaration(
						listaModifiers
						,new TypeAccess("ArrayList")
						,"catchExceptions");

		Opt<Expr> parametersIndex = new Opt<Expr>();
		List<Expr> parametersNewAtomicInteger = new List<Expr>();

		parametersNewAtomicInteger.add(new IntegerLiteral(0));

		parametersIndex.addChild(
				new ClassInstanceExpr(
						new TypeAccess("AtomicInteger")
						,parametersNewAtomicInteger));
		FieldDeclaration index = 
				new FieldDeclaration(
						listaModifiers
						,new TypeAccess("AtomicInteger")
						,"index"
						,parametersIndex);

		retorno.add(threads);
		retorno.add(catchExceptions);
		retorno.add(index);

	}
	//	public SafeNode() {
	//		this.threads = new ArrayList();
	//	}
	private static void createConstrutor(List<BodyDecl> retorno) {

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		listaModifiers.addModifier(modPublic);

		Opt<Stmt> opt = new Opt<Stmt>();

		SuperConstructorAccess superConstrutor = 
				new SuperConstructorAccess();
		ExprStmt exprStmt = 
				new ExprStmt(superConstrutor);
		opt.addChild(exprStmt);

		ExprStmt exprSetThreads = 
				new ExprStmt(
						new AssignSimpleExpr(
								new Dot(
										new ThisAccess("this")
										,new VarAccess("threads"))
								,new ClassInstanceExpr(
										new TypeAccess("ArrayList")
										,new List<Stmt>())));

		Block block = new Block();
		block.addStmt(exprSetThreads);

		ConstructorDecl contrutor = 
				new ConstructorDecl(
						listaModifiers
						,"SafeNode"
						,new List<ParameterDeclaration>()
						,new List<Access>()
						,opt
						,block);

		retorno.add(contrutor);
	}


	//	public synchronized void addThread(Thread thread){
	//		this.threads.add(thread);
	//		this.index.incrementAndGet();
	//	}

	private static void createAddThread(List<BodyDecl> retorno){

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		ExprStmt addThreadExprStmt = 
				new ExprStmt(
						new Dot(
								new ThisAccess("this")
								,
								new Dot(
										new VarAccess("threads")
										,
										new MethodAccess(
												"add"
												,new List<Expr>()
												.add(new VarAccess("thread")
														)
												)
										)
								)
						);
		ExprStmt incrementAndGetExprStmt =
				new ExprStmt(new Dot(
						new ThisAccess("this")
						,
						new Dot(
								new VarAccess("index")
								,
								new MethodAccess(
										"incrementAndGet"
										, new List<Expr>()
										)
								)
						)
						);
		principalBloco.addStmt(addThreadExprStmt);
		principalBloco.addStmt(incrementAndGetExprStmt);
		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Thread")
						,"thread"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"addThread"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}


	//	public synchronized void tryWakeUp(Thread thread){
	//		this.threads.remove(thread);
	//		this.index.decrementAndGet();
	//		notifyAll();
	//	}

	private static void createTryWakeUp(List<BodyDecl> retorno) {

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		ExprStmt removeThreadExprStmt = 
				new ExprStmt(
						new Dot(
								new ThisAccess("this")
								,new Dot(
										new VarAccess("threads")
										,
										new MethodAccess(
												"remove"
												,new List<Expr>()
												.add(new VarAccess("thread")
														)
												)
										)
								)
						);
		ExprStmt decrementAndGetExprStmt =
				new ExprStmt(new Dot(
						new ThisAccess("this")
						,
						new Dot(
								new VarAccess("index")
								,
								new MethodAccess(
										"decrementAndGet"
										, new List<Expr>()
										)
								)
						)
						);

		ExprStmt notifyAllExprStmt = 
				new ExprStmt(
						new MethodAccess(
								"notifyAll"
								,new List<Expr>()));

		principalBloco.addStmt(removeThreadExprStmt);
		principalBloco.addStmt(decrementAndGetExprStmt);
		principalBloco.addStmt(notifyAllExprStmt);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Thread")
						,"thread"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"tryWakeUp"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}
	//	public synchronized void addException(Exception excep){
	//		if(this.catchExceptions == null){
	//			this.catchExceptions = new ArrayList();
	//		}
	//		this.catchExceptions.add(excep);
	//	}

	private static void createAddException(List<BodyDecl> retorno) {

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		ExprStmt addExceptionExprStmt = 
				new ExprStmt(
						new Dot(
								new ThisAccess("this")
								,
								new Dot(
										new VarAccess("catchExceptions")
										,
										new MethodAccess(
												"add"
												,new List<Expr>()
												.add(new VarAccess("excep")
														)
												)
										)
								)
						);

		IfStmt ifStmt = 
				new IfStmt(
						new EQExpr(
								new Dot(
										new ThisAccess("this")
										,new VarAccess("catchExceptions")
										)
								, new NullLiteral("null") )
						,new ExprStmt(
								new AssignSimpleExpr(
										new Dot(
												new ThisAccess("this")
												,new VarAccess("catchExceptions"))
										,new ClassInstanceExpr(
												new TypeAccess("ArrayList")
												,new List<Expr>()))));

		principalBloco.addStmt(ifStmt);
		principalBloco.addStmt(addExceptionExprStmt);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Exception")
						,"excep"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"addException"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}


	//	public synchronized void syncUp(){
	//		while(index.get() > 0){
	//			try {
	//				wait();
	//			} catch (InterruptedException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	}

	private static void createSyncUp(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();

		Block blockTry = new Block();
		blockTry.addStmt(
				new ExprStmt(
						new MethodAccess(
								"wait"
								,new List<Expr>())));

		Block blockCatch = 
				new Block();
		blockCatch.addStmt(
				new ExprStmt(
						new Dot(
								new VarAccess("e")
								,
								new MethodAccess(
										"printStackTrace"
										,new List<Expr>())
								)
						)
				);

		BasicCatch catchStmt = new BasicCatch(
				new ParameterDeclaration(
						new TypeAccess("InterruptedException")
						, "e")
				,blockCatch);

		TryStmt tryStmt = 
				new TryStmt(
						blockTry
						,new List<CatchClause>().add(catchStmt)
						,new Opt<Block>());
		WhileStmt whileStmt = 
				new WhileStmt(
						new GTExpr(
								new Dot(
										new VarAccess("index")
										,new MethodAccess(
												"get"
												,new List<Expr>()))
								, new IntegerLiteral(0))
						,tryStmt);

		principalBloco.addStmt(whileStmt);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"syncUp"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}


	//	public synchronized void breakUp(){
	//		for (int i = 0; i < threads.size(); i++) {
	//			((Thread) threads.get(i)).interrupt();
	//		}
	//	}

	private static void createBreakUp(List<BodyDecl> retorno) {

		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("void");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();


		List<Stmt> listVariableInitFor = new List<Stmt>();

		VariableDeclaration variableFor = 
				new VariableDeclaration(
						new PrimitiveTypeAccess("int")
						,"i"
						,new IntegerLiteral(0));
		listVariableInitFor.add(variableFor);


		Opt<Expr> listCondictionFor = new Opt<Expr>();
		LTExpr ltExpr = 
				new LTExpr(
						new VarAccess("i")
						,new Dot(
								new VarAccess("threads")
								,new MethodAccess(
										"size"
										,new List<Expr>())));
		listCondictionFor.addChild(ltExpr);

		List<Stmt> listDoFor = new List<Stmt>();
		ExprStmt exprStmt = new ExprStmt(new PostIncExpr(new VarAccess("i")));
		listDoFor.add(exprStmt);

		Block blockFor = new Block();
		List<Expr> listExpr = new List<Expr>();
		VarAccess accessI = new VarAccess("i");
		listExpr.add(accessI);


		CastExpr castExpr = new CastExpr(
				new TypeAccess("Thread")
				,new Dot(
						new VarAccess("threads")
						,new MethodAccess(
								"get"
								,listExpr)));

		ExprStmt exprStmtForStmt = new ExprStmt(
				new Dot(new ParExpr(
						castExpr)
				,new MethodAccess(
						"interrupt"
						,new List<Expr>())));
		blockFor.addStmt(exprStmtForStmt);

		ForStmt forStmt = 
				new ForStmt(
						listVariableInitFor
						,listCondictionFor
						,listDoFor
						,blockFor);

		principalBloco.addStmt(forStmt);

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"breakUp"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}


	//	public synchronized boolean existsThread(Thread thread){
	//		if (threads.contains(thread)) {
	//			return true;
	//		}
	//		return false;
	//	}

	private static void createExistsThread(List<BodyDecl> retorno) {
		Modifiers listaModifiers = new Modifiers();
		Modifier modPublic = new Modifier("public");
		Modifier modSync = new Modifier("synchronized"); 

		PrimitiveTypeAccess primitiveAccess = 
				new PrimitiveTypeAccess("boolean");

		listaModifiers.addModifier(modPublic);
		listaModifiers.addModifier(modSync);			

		Block principalBloco = new Block();
		List<Expr> parametersIfExpr = new List<Expr>();
		parametersIfExpr.add(new VarAccess("thread"));
		Block blockIf = new Block();
		blockIf.addStmt(new ReturnStmt(
				new BooleanLiteral("true")));

		IfStmt ifStmt = new IfStmt(
				new Dot(
						new VarAccess("threads")
						,new MethodAccess(
								"contains"
								,parametersIfExpr))
				,blockIf);

		principalBloco.addStmt(ifStmt);
		principalBloco.addStmt(new ReturnStmt(
				new BooleanLiteral("false")));

		Opt<Block> opt = new Opt<Block>();
		opt.addChild(principalBloco);

		List<ParameterDeclaration> parameters = 
				new List<ParameterDeclaration>();
		parameters.add(
				new ParameterDeclaration(
						new TypeAccess("Thread")
						,"thread"));

		MethodDecl method = 
				new MethodDecl(
						listaModifiers
						,primitiveAccess
						,"existsThread"
						,parameters
						,new List<Access>()
						,opt);

		retorno.add(method);

	}

	//	public boolean hasException(){
	//		if(errorReport.isEmpty()){
	//			return false;
	//		}
	//		return true;
	//	}

	private static void createHasException(List<BodyDecl> retorno) {

//		Modifiers listaModifiers = new Modifiers();
//		Modifier modPublic = new Modifier("public");
//
//		PrimitiveTypeAccess primitiveAccess = 
//				new PrimitiveTypeAccess("boolean");
//
//		listaModifiers.addModifier(modPublic);
//
//		Block principalBloco = new Block();
//
//		Block blockIf = new Block();
//		blockIf.addStmt(new ReturnStmt(
//				new BooleanLiteral("false")));
//
//		IfStmt ifStmt = new IfStmt(
//				new Dot(
//						new VarAccess("errorReport")
//						,new MethodAccess(
//								"isEmpty"
//								,new List<Expr>()))
//				,blockIf);
//
//		principalBloco.addStmt(ifStmt);
//		principalBloco.addStmt(new ReturnStmt(
//				new BooleanLiteral("true")));
//
//		Opt<Block> opt = new Opt<Block>();
//		opt.addChild(principalBloco);
//
//		List<ParameterDeclaration> parameters = 
//				new List<ParameterDeclaration>();
//
//		MethodDecl method = 
//				new MethodDecl(
//						listaModifiers
//						,primitiveAccess
//						,"hasException"
//						,parameters
//						,new List<Access>()
//						,opt);
//
//		retorno.add(method);

	}
}
