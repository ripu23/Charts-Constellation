package chartconstellation.app.entities;

import java.util.List;

public class UserCharts {

    private String userName;
    private List<String> idList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

	@Override
    public String toString() {
        return "UserCharts{" +
                "userName='" + userName + '\'' +
                ", idList=" + idList +
                '}';
    }
}
