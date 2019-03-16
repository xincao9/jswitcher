# jswitcher

### java switcher service

> Applicable to Operation and Maintenance Service Intervention Management Suite

<pre>
## create a table of storage switch information

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
## add dependency to pom.xml file

com.github.xincao9:jswitcher-api:1.0
</pre>

<pre>
## switcher.properties file is placed in the root of the classpath

switcher.port=12306 // listening port
switcher.database.name=switcher // switch data is stored by default to mysql
switcher.database.user=root
switcher.database.pass=
switcher.database.host=127.0.0.1
switcher.database.port=3306

</pre>

<pre>
## Sample code

public class Sample {

    public static void main() throws IOException {
        SwitcherServer switcherServer = new SwitcherServer();
        SwitcherService switcherService = switcherServer.getSwitcherService();
        switcherService.register("key", Boolean.TRUE, "for testing", QoS.API);
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