import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.tree.TreeNode;

public class Prototipo {


	public Prototipo() {
		createThreads();
	}

	public void createThreads(){
		SafeNode safe = new SafeNode();
		SafeManager.getInstance().addSafe(safe,SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()));
		safe.nome = "pai";
		try{
			safe.addException(new InterruptedException());
			safe.addException(new NullPointerException());
			Threadzinha thread1 = new Threadzinha();
			thread1.start();
			safe.addThread(thread1);
			Threadzinha thread2 = new Threadzinha();
			thread2.start();
			safe.addThread(thread2);
			Threadzinha thread3 = new Threadzinha();
			thread3.start();
			safe.addThread(thread3);
		}
		finally{
			if(safe.hasException()){
				SafeManager.getInstance().showError();				
			}
			safe.syncUp();
			//	System.out.println("Thread Principal terminou");
		}
	}
	static class Threadzinha extends Thread{

		static AtomicBoolean args = new AtomicBoolean(false);
				
		public Threadzinha() {
		}
		@Override
		public void run() {
			super.run();

			//	Boolean ehSafe = SafeManager.getInstance().isSafe(Thread.currentThread());
			//	System.out.println("é Safe? " + ehSafe);
			
				if(Threadzinha.args.get() == false){
					Threadzinha.args.set(true);
					Threadzinha threadok = new Threadzinha();
					SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).addThread(threadok);
					threadok.start();

					//SIMULANDO UM SAFE

					SafeNode safe = new SafeNode();
					SafeManager.getInstance().addSafe(safe,SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()));
					safe.nome = "filho";
					try{
						Threadzinha thread1 = new Threadzinha();
						thread1.start();
						safe.addThread(thread1);
						Threadzinha thread2 = new Threadzinha();
						thread2.start();
						safe.addThread(thread2);
						Threadzinha thread3 = new Threadzinha();
						thread3.start();
						safe.addThread(thread3);
						wait();
					} catch (Exception e) {
						e.getStackTrace();
						List<StackTraceElement> stack = Arrays.asList(e.getStackTrace());
						TreeNode[] nodes = SafeManager.getInstance().getTree().getPathToRoot(SafeManager.getInstance().getSafe(null,Thread.currentThread()));
						SafeManager.getInstance().addError(safe);
						//safe.addError(Thread.currentThread().getClass().toString());
						//System.out.println(Thread.currentThread().getStackTrace().toString());
						//e.printStackTrace();
						//new Exception().printStackTrace();
					}
					finally{
						if(safe.hasException()){

							SafeManager.getInstance().showError();				
						}
						safe.syncUp();
						//			System.out.println("Thread Secundaria terminou");
					}
				}
			
			try {
				for (long i = 0; i < 100000000l; i++) {
					long w = i+i;
				}
			}catch (Exception e) {
				SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).addError(Thread.currentThread().toString());
				SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).breakUp();
			}finally{
				//		System.out.println("Thread #" + Thread.currentThread() + " saiu." + " safe : "+ SafeManager.getInstance().getSafe(Thread.currentThread()).nome);
				SafeManager.getInstance().getSafe(SafeManager.getInstance().getRoot(),Thread.currentThread()).tryWakeUp(Thread.currentThread());
			}
		}
	}
	public static void main(String[] args) {
		new Prototipo();
	}
}
