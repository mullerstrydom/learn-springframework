package learn.example.springframework.propertiesbyprofile.datasource;

public class MyDataSourceProperties {

    private String userName;
    private String password;
    private String dbUrl;

    public MyDataSourceProperties() {
    }

    public MyDataSourceProperties(String userName, String password, String dbUrl) {
        this.userName = userName;
        this.password = password;
        this.dbUrl = dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
}
