package entitys;

public class User {
	
	private String user;
	private String pwd;
	private String identity;
	
	public User(String user,String pwd,String identity) {
		this.identity = identity;
		this.pwd = pwd;
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "User [user=" + user + ", pwd=" + pwd + ", identity=" + identity + "]";
	}
	
	

}
