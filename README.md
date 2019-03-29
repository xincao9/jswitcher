## jswitcher

### Grayscale release, Service degradation

Support for grayscale publishing of functions, service downgrade, Automated operation and maintenance

![logo](https://github.com/xincao9/jswitcher/blob/master/logo.png)

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
    <artifactId>jswitcher-spring-boot-starter</artifactId>
    <version>1.1</version>
</dependency>
```

**_ServiceApplication_**

```
/**
 * Service
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
            switcherService.register(KEY, Boolean.TRUE, "recording ServiceApplication log", QoS.API);
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

**_jswitcher-ui_**

```
wget https://search.maven.org/remotecontent?filepath=com/github/xincao9/jswitcher-ui/1.1/jswitcher-ui-1.1.jar
java -jar jswitcher-ui-1.1.jar
http://localhost:8080/keys.html
```

![keys](https://github.com/xincao9/jswitcher/blob/master/keys.png)
