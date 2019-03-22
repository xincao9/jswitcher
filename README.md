## jswitcher

### java switcher service

Applicable to Operation and Maintenance Service Intervention Management Suite

**_Create a table structure_**

```
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

```

**_Maven dependency_**

```
<dependency>
    <groupId>com.github.xincao9</groupId>
    <artifactId>jswitcher-core</artifactId>
    <version>1.0</version>
</dependency>
```

**_jswitcher.properties_**

```
jswitcher.port=12306
jswitcher.database.name=switcher
jswitcher.database.user=root
jswitcher.database.pass=
jswitcher.database.host=127.0.0.1
jswitcher.database.port=3306
jswitcher.database.opts=useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
```

**_Sample_**

```
public class Sample {

    public static void main() throws IOException {
        SwitcherServer switcherServer = new SwitcherServer();
        SwitcherService switcherService = switcherServer.getSwitcherService();
        switcherService.register("key", Boolean.TRUE, "用于测试", QoS.API);
        if (switcherService.isOpen("key")) {
            System.out.println("key open status");
        } else {
            System.out.println("key closed status");
        }
        switcherService.set("key", Boolean.FALSE);
        System.in.read();
        switcherServer.close();
    }

}
```