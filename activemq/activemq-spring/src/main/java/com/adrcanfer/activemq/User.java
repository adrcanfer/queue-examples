package com.adrcanfer.activemq;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
	
	private static final long serialVersionUID = -4577424801230409710L;
	
	private String name;
	private String email;
	private LocalDateTime lastUpdate;
	
	public String getName() {
		return name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", lastUpdate=" + lastUpdate + "]";
	}
	
}
