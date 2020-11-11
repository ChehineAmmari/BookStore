package humans;

public class Person {

	private int cin;
	private String firstName, lastName, phone;
	
	public Person() {}

	public Person(int cin, String firstName, String lastName, String phone) {
		this.cin = cin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		Person p = obj instanceof Person ? (Person)obj : null;
		return p != null && this.cin == p.cin ? true : false;
	}

	@Override
	public String toString() {
		return "\nCIN: #"+this.cin
				+"\nFirst Name: "+this.firstName
				+"\nLast Name: "+this.lastName
				+"\nPhone Number: "+this.phone;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
