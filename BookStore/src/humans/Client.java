package humans;

public class Client extends User {

	public Client() {}
	
	public Client(String username, String password) {
		super(username,password);
	}
	
	public Client(int id, String username, String password) {
		super(id,username,password);
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		return s;
	}

	
}
