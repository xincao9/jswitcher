# jswitcher

### java switcher service

<pre>
创建存储开关信息的表

CREATE TABLE `switcher` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(64) NOT NULL DEFAULT '',
  `open` tinyint(1) NOT NULL DEFAULT '0',
  `describe` varchar(128) DEFAULT NULL,
  `qos` varchar(24) NOT NULL DEFAULT 'API',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
</pre>

<pre>
maven pom.xml 中添加依赖
com.github.xincao9:jswitcher-api:1.0
</pre>

<pre>
## switcher-server.properties文件 放到classpath根目录下

switcher.listen.ip=0.0.0.0 // 绑定IP
switcher.listen.port=12306 // 监听端口
switcher.database.name=switcher // 开关数据默认存储到mysql
switcher.database.user=root
switcher.database.pass=asdf
switcher.database.host=127.0.0.1
switcher.database.port=3306

</pre>

<pre>
实例代码

public class SwitcherServerTest {

    /**
     * Test of getSwitcherService method, of class SwitcherServer.
     * @throws java.io.IOException
     */
    @Test
    public void testGetSwitcherService() throws IOException {
        SwitcherServer switcherServer = new SwitcherServer();
        SwitcherService switcherService = switcherServer.getSwitcherService();
        switcherService.register("key", Boolean.TRUE, "用于测试", QoS.API);
        if (switcherService.isOpen("key")) {
            System.out.println("key open state");
        } else {
            System.out.println("key closed state");
        }
        System.in.read();
        if (switcherService.isOpen("key")) {
            System.out.println("key open state");
        } else {
            System.out.println("key closed state");
        }
        System.in.read();
        switcherServer.close();
    }

}

</pre>