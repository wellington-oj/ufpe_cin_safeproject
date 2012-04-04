import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;


public class SafeManager {

	private static SafeManager instance;
	private DefaultTreeModel tree;

	private SafeManager() {
		super();
		tree = new DefaultTreeModel(null,false);
	}
		
	public static SafeManager getInstance(){
		synchronized(SafeManager.class){
			if (instance == null) {
				instance = new SafeManager();			
			}
		}
		return instance;
	}

	
	public synchronized void addSafe(SafeNode newChild,SafeNode parent){
		if(tree.getRoot() == null){
			tree.setRoot(newChild);
		}
		else{
			tree.insertNodeInto(newChild,(MutableTreeNode) parent, 0);
		}
	}

	public synchronized SafeNode getRoot(){
		return (SafeNode) tree.getRoot();
	}

	public synchronized DefaultTreeModel getTree(){
		return tree;
	}
	
	public synchronized void addError(SafeNode node){
		TreeNode[] path = tree.getPathToRoot(node);
		for (TreeNode safeNode : path) {
			SafeNode safesNode = (SafeNode) safeNode;
			node.addError(safesNode.nome);
		}
	}
	
	public synchronized void showError(){
		SafeNode node = (SafeNode) this.tree.getRoot();
		for (int i = 0; i < tree.getChildCount(tree.getRoot()); i++) {
			
			//FIXME
			node = (SafeNode) tree.getChild(tree.getRoot(),i);
			if(node.hasException()){
			node.showError();
			}
			
		}
	}


	public synchronized void removeSafe(SafeNode safe){
		if(tree.getRoot() != null){
			tree.removeNodeFromParent(safe);
			System.out.println("testando");
		}
		else if(tree.getRoot().equals(safe)){
			tree.setRoot(null);
		}
		else{
			System.out.println("error");
		}
	}

	public synchronized boolean isSafe(Thread thread){
		if(getSafe((SafeNode) tree.getRoot(),thread) != null){
			return true;
		}
		return false;
	}

	public SafeNode getSafe(SafeNode originalNode,Thread thread){
		if(originalNode == null){
			return null;
		}
		else if(originalNode.existsThread(thread)){
			return (SafeNode) originalNode;
		}
		SafeNode node = (SafeNode) tree.getChild(originalNode,0);
		for (int i = 0; i < tree.getChildCount(originalNode); i++) {

			node = (SafeNode) tree.getChild(originalNode, i);
			if(node.existsThread(thread)){
				return node;
			}
		}
		return getSafe(node,thread);
	}

}

class SafeNode extends DefaultMutableTreeNode{

	private ArrayList<Thread> threads;
	private ArrayList<Exception> catchExceptions;
	private ArrayList<String> errorReport;
	private AtomicInteger index = new AtomicInteger(0);

	String nome = "";

	public SafeNode() {
		this.threads = new ArrayList<Thread>();
		this.errorReport = new ArrayList<String>();
	}

	public synchronized void addThread(Thread thread){
		this.threads.add(thread);
		this.index.incrementAndGet();
	}

	public synchronized void tryWakeUp(Thread thread){
		this.threads.remove(thread);
		this.index.decrementAndGet();
		notifyAll();
	}

	public synchronized void addException(Exception excep){
		if(this.catchExceptions == null){
			this.catchExceptions = new ArrayList<Exception>();
		}
		this.catchExceptions.add(excep);
	}

	public synchronized void addError(String className){
		this.errorReport.add(className);
	}

	public synchronized void showError(){
		for (String error : errorReport) {
			System.out.println(error);
		}
	}

	public synchronized void syncUp(){
		while(index.get() > 0){
			try {
				//System.out.println("contador : " + index.get() +" safe: " + nome);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void breakUp(){
		for (Thread thread : threads) {
			thread.interrupt();
		}
	}

	public boolean existsThread(Thread thread){
		if (threads.contains(thread)) {
			return true;
		}
		return false;
	}

	public boolean hasException(){
		if(errorReport.isEmpty()){
			return false;
		}
		return true;
	}

}