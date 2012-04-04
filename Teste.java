public class Teste {

	public Teste() {
		try{	
			System.out.println(2);	
			new Thread(new Runzinho2()).start();
			new Thread(new Runzinho3()).start();
			Thread w = new Thread(new Runnable() {

			public void run() {
				System.out.println("testes");
			}
			});
			w.start();			
			//System.out.println(3);
			//System.out.println(4);
			//Thread j = new Thread(new Runzinho());
			//j.start();
			//try {
			//	j.join();
			//} catch (InterruptedException e) {
			//	 System.out.println(23);	
			//}

			new Thread(new Runnable() {
			public void run() {
				String s = null;
				int a = Integer.parseInt(s);
				System.out.println(a);
			}
		});
		}finally{

		}
		
	}

	public static void main(String[] args) {
		new Teste();
	}

}

class Runzinho implements Runnable{

	public void run() {
		String s = null;
		int ae = Integer.parseInt(s);
		System.out.println(ae);
	}
}

class Runzinho2 implements Runnable{

	public void run() {
		System.out.println(24);		
	}
}

class Runzinho3 implements Runnable{

	public void run() {
		System.out.println(25);		
	}
}




class Josta{

	String ae = "teste";

	Josta(){

	}

	public String getAE(){
		return ae;
	}
}
