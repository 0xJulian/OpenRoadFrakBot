import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class SQLCommands {
    public final Connection connection;
    JSONParser parser = new JSONParser();

    public SQLCommands() throws SQLException, ClassNotFoundException, JSONException, IOException, ParseException {
        Object obj = parser.parse(new FileReader(Main.configPath));
        JSONObject jsonObject = (JSONObject)obj;
        JSONObject obd = (JSONObject) jsonObject.get("connectionString");
        String url = obd.get("url").toString();
        String user = obd.get("user").toString();
        String pw = obd.get("password").toString();
        this.connection = DriverManager.getConnection(url, user, pw);
        }

        public ResultSet selectData(String selector, String table, String condition) throws SQLException {
            Statement stmt = this.connection.createStatement();
            return stmt.executeQuery("select " + selector + " from " + table + " WHERE " + condition);
        }
}
