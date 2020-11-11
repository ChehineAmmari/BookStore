package humans;

import utilities.PersistentData;

public class Author extends Person {
	
	public Author() {}
	
	public Author(int cin, String firstName, String lastName, String phone) {
		super(cin,firstName,lastName,phone);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	public void add() {
		PersistentData.authors.add(this);
	}
	
	public void delete() {
		PersistentData.authors.remove(this);
	}
	
	public static void showAll() {
		for(Author a: PersistentData.authors) {
			System.out.println(a);
		}
	}
	
	public static Author getAuthor(int cin) {
		for(Author a: PersistentData.authors) {
			if(a.getCin() == cin) {
				return a;
			}
		}
		return null;
	}

}
