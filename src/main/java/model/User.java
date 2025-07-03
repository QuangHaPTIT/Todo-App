package model;

import java.time.LocalDate;

public class User {
    private int id;
    
    private String firstName;
    private String lastName;
    
    private String username;
    private String email;
    private String password;
    
    private LocalDate dob;
    
    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public String getEmail() {
		return email;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    public LocalDate getDob() {
		return dob;
	}
    
    public void setDob(LocalDate dob) {
		this.dob = dob;
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
