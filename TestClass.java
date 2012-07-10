public class newTeste {

	public newTeste() {
		
		safe {
			System.out.println(10);	
			Thread thread = new Thread(new Runzinho2());
			thread.start();
			Thread thread2 = new Thread(new Runzinho());
			thread2.start();
		 }catch(Exception e){
	
		}
		 
		 System.out.println("teste");
		
	}
	
	public static void main(String[] args) {
		new newTeste();
	}

}

class Runzinho implements Runnable{

	public void run() {
	
		Thread thread = new Thread(new Runzinho2());
		thread.start();
		String s = "10";
		int ae = Integer.parseInt(s);
		System.out.println(ae);
	}
}

class Runzinho2 implements Runnable{

	public void run() {
		String s = "10";
		int ae = Integer.parseInt(s);
		System.out.println(ae);
	}
}
