/*
 * Copyright 2019 xincao9@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.jswitcher.service.dao;

import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.api.vo.Switcher;
import com.github.xincao9.jswitcher.service.Configure;
import com.github.xincao9.jswitcher.api.exception.SwitcherServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 开关仓库
 *
 * @author xincao9@gmail.com
 */
public class SwitcherDAO {

    private Connection connection;
    private static final String INSERT = "insert into switcher (`key`, `open`, `describe`, `qos`) values (?, ?, ?, ?)";
    private static final String UPDATE = "update switcher set `open`=? where `key`=? and `open`=?";
    private static final String SELECT = "select `id`, `key`, `open`, `describe`, `qos` from switcher where `key`=?";

    /**
     * 构造器
     *
     * @param configure 配置
     */
    public SwitcherDAO(Configure configure) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?%s", configure.getDatabaseHost(), configure.getDatabasePort(), configure.getDatabaseName(), configure.getDatabaseOpts()), configure.getDatabaseUser(), configure.getDatabasePass());
        } catch (Throwable e) {
            throw new SwitcherServerException("SwitcherDAO abnormal", e);
        }
    }

    /**
     * 创建
     *
     * @param switcher 开关
     * @return 条数
     */
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
            throw new SwitcherServerException("SwitcherDAO abnormal", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable e) {
                    throw new SwitcherServerException("SwitcherDAO abnormal", e);
                }

            }
        }
    }

    /**
     * 修改开关状态
     *
     * @param key 键值
     * @param expected 期望开关状态
     * @param open 开关状态
     * @return 影响条数
     */
    public int changeStatusByKey(String key, boolean expected, boolean open) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(UPDATE);
            statement.setBoolean(1, open);
            statement.setString(2, key);
            statement.setBoolean(3, expected);
            return statement.executeUpdate();
        } catch (Throwable e) {
            throw new SwitcherServerException("SwitcherDAO abnormal", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Throwable e) {
                    throw new SwitcherServerException("SwitcherDAO abnormal", e);
                }
            }
        }
    }

    /**
     * 查询开关
     *
     * @param key 键值
     * @return 开关
     */
    public Switcher selectByKey(String key) {
        List<Switcher> switcheres = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = this.connection.prepareStatement(SELECT);
            statement.setString(1, key);
            rs = statement.executeQuery();
            if (rs != null) {
                switcheres = new ArrayList();
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
            throw new SwitcherServerException("SwitcherDAO abnormal", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Throwable e) {
                throw new SwitcherServerException("SwitcherDAO abnormal", e);
            }
        }
        if (switcheres != null && !switcheres.isEmpty()) {
            return switcheres.get(0);
        }
        return null;
    }

}
