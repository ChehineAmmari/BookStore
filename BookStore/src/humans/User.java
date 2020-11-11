package humans;

import utilities.PersistentData;

public class User {
	
	private static int lastId = 0;
	private int id;
	private String username, password;
	
	public User() {}
	
	public User(String username, String password) {
		this.id = ++lastId;
		this.username = username;
		this.password = password;
	}

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public User login() {
		for(User p: PersistentData.users) {
			if(this.equals(p))
				return p;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "\nId: #"+this.id+" - "+this.username;
	}

	@Override
	public boolean equals(Object obj) {
		boolean b = false;
		if(obj instanceof User) {
			User p = (User)obj;
			b = this.username.equals(p.username) && this.password.equals(p.password) ? true : false; 
		}
		return b;
	}
	
	public void add() {
		PersistentData.users.add(this);
	}
	
	public void delete() {
		PersistentData.users.remove(this);
	}
	
	public static void showAll() {
		for(User u: PersistentData.users) {
			System.out.println(u);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
