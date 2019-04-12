## jswitcher

### Grayscale release, Service degradation [中文文档](https://github.com/xincao9/jswitcher/wiki/%E4%B8%AD%E6%96%87%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E)

Support for grayscale publishing of functions, service downgrade, Automated operation and maintenance

![logo](https://github.com/xincao9/jswitcher/blob/master/logo.png)


**_Maven dependency_**


```
<dependency>
    <groupId>com.github.xincao9</groupId>
    <artifactId>jswitcher-spring-boot-starter</artifactId>
    <version>1.2.2</version>
</dependency>
```

**_ServiceApplication_**

```
package com.github.xincao9.jswitcher.sample;

import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.api.vo.QoS;
import com.github.xincao9.jswitcher.spring.boot.starter.EnableJswitcher;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Service Application
 *
 * @author xincao9@gmail.com
 */
@SpringBootApplication
@EnableJswitcher
public class ServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceApplication.class);
    @Autowired
    private SwitcherService switcherService;
    private static final String KEY = ServiceApplication.class.getCanonicalName();

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (String... args) -> {
            switcherService.register(KEY, Boolean.TRUE, "Recording ServiceApplication Logger", QoS.API);
            for (int no = 0; no < 100; no++) {
                if (switcherService.isOpen(KEY)) {
                    LOGGER.info(RandomStringUtils.randomAscii(128));
                }
                TimeUnit.SECONDS.sleep(1);
            }
        };
    }

}
```

**_application.properties_**

```
jswitcher.application.name=jswitcher-sample
jswitcher.server.port=12306
jswitcher.discovery.zookeeper=localhost:2181
jswitcher.database.name=switcher
jswitcher.database.user=root
jswitcher.database.pass=
jswitcher.database.host=127.0.0.1
jswitcher.database.port=3306
jswitcher.database.opts=useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
```

**_Create a table structure_**

```
## create a table of storage switch information

CREATE TABLE `switcher` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `application` varchar(64) NOT NULL DEFAULT 'default',
  `key` varchar(64) NOT NULL DEFAULT '',
  `open` tinyint(1) NOT NULL DEFAULT '0',
  `describe` varchar(128) DEFAULT NULL,
  `qos` varchar(24) NOT NULL DEFAULT 'API',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

### jswitcher-ui

**_Install_**


1. [download jswitcher-ui](https://search.maven.org/remotecontent?filepath=com/github/xincao9/jswitcher-ui/1.2.2/jswitcher-ui-1.2.2.jar)
2. java -jar jswitcher-ui-1.2.2.jar --jsonrpc.discovery.zookeeper=localhost:2181
3. [ui url](http://localhost:8080)

**_Used_**

![keys](https://github.com/xincao9/jswitcher/blob/master/keys.png)

#### Contact

* [https://github.com/xincao9/jswitcher/issues](https://github.com/xincao9/jswitcher/issues)
* xincao9@gmail.com
