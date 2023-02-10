
public class Message {
	String sender;
	String receiver;
	String content;
	
	public void print(){
		System.out.println("From <" +sender +"> to <" +receiver+">: "+content);
		
		
	}


}

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message message1=new Message();
		message1.sender="Eu";
		message1.receiver="Tu";
		message1.content="Ha!";

		Message message2=new Message();
		message2.sender="Tu";
		message2.receiver="Eu";
		message2.content="Ha!Ha!";
		
		Message message3=new Message();
		message3.sender="Eu";
		message3.receiver="Tu";
		message3.content="Ha!Ha!Ha!";
	}

}

