package beans;

/** One person who can log in. Passwords are stored in the shared user file as plain text. */
public class UserBean {
  private String id;
  private String password;
  private String name;
  private String manager;

  public UserBean(String id, String password, String name, String manager) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.manager = manager;
  }

  public UserBean() {}

  public UserBean(String id, String password) {
    this.id = id;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  @Override
  public String toString() {
    return "UserBean [id=" + id + ", name=" + name + ", manager=" + manager + "]";
  }
}
