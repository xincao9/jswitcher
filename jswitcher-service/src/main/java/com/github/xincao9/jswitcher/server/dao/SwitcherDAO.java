package com.github.xincao9.jswitcher.server.dao;

import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import com.github.xincao9.jswitcher.server.Configure;
import com.github.xincao9.jswitcher.server.exception.SwitcherServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class SwitcherDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherDAO.class);
    private Connection connection;
    private static final String INSERT = "insert into switcher (`key`, `open`, `describe`, `qos`) values (?, ?, ?, ?)";
    private static final String UPDATE = "update switcher set `open`=? where `key`=? and `open`=?";
    private static final String SELECT = "select `id`, `key`, `open`, `describe`, `qos` from switcher where `key`=?";

    public SwitcherDAO(Configure configure) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?%s", configure.getDatabaseHost(), configure.getDatabasePort(), configure.getDatabaseName(), configure.getDatabaseOpts()), configure.getDatabaseUser(), configure.getDatabasePass());
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
            throw new SwitcherServerException("SwitcherDAO 异常", e);
        }
    }

    public int insert(Switcher switcher) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(INSERT);
            statement.setString(1, String.valueOf(switcher.getKey()));
            statement.setBoolean(2, switcher.getOpen());
            statement.setString(3, String.valueOf(switcher.getDescribe()));
            statement.setString(4, String.valueOf(switcher.getQos()));
            return statement.executeUpdate();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
            throw new SwitcherServerException("SwitcherDAO 异常", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable e) {
                    LOGGER.error(e.getMessage());
                    throw new SwitcherServerException("SwitcherDAO 异常", e);
                }

            }
        }
    }

    public int changeStatusByKey(String key, boolean expected, boolean open) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(UPDATE);
            statement.setBoolean(1, open);
            statement.setString(2, key);
            statement.setBoolean(3, expected);
            return statement.executeUpdate();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
            throw new SwitcherServerException("SwitcherDAO 异常", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable e) {
                    LOGGER.error(e.getMessage());
                    throw new SwitcherServerException("SwitcherDAO 异常", e);
                }
            }
        }
    }

    public Switcher selectByKey(String key) {
        List<Switcher> switcheres = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = this.connection.prepareStatement(SELECT);
            statement.setString(1, key);
            rs = statement.executeQuery();
            if (rs != null) {
                switcheres = new ArrayList<Switcher>();
                while (rs.next()) {
                    Switcher switcher = new Switcher();
                    switcher.setKey(rs.getString("key"));
                    switcher.setOpen(rs.getBoolean("open"));
                    switcher.setQos(QoS.valueOf(rs.getString("qos")));
                    switcher.setDescribe(rs.getString("describe"));
                    switcheres.add(switcher);
                }
            }
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
            throw new SwitcherServerException("SwitcherDAO 异常", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable e) {
                LOGGER.error(e.getMessage());
                throw new SwitcherServerException("SwitcherDAO 异常", e);
            }
        }
        if (switcheres != null && !switcheres.isEmpty()) {
            return switcheres.get(0);
        }
        return null;
    }

}
