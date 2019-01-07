package com.github.xincao9.jswitcher.server;

import com.github.xincao9.jsonrpc.JsonRPCServer;
import com.github.xincao9.jswitcher.api.service.SwitcherService;
import com.github.xincao9.jswitcher.server.dao.SwitcherDAO;
import com.github.xincao9.jswitcher.server.exception.SwitcherServerException;
import com.github.xincao9.jswitcher.server.method.switcher.CheckMethodImpl;
import com.github.xincao9.jswitcher.server.method.switcher.ListMethodImpl;
import com.github.xincao9.jswitcher.server.method.switcher.OffMethodImpl;
import com.github.xincao9.jswitcher.server.method.switcher.OnMethodImpl;
import com.github.xincao9.jswitcher.server.method.switcher.SetMethodImpl;
import com.github.xincao9.jswitcher.server.service.SwitcherServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class SwitcherServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwitcherServer.class);
    private static final String DEFAULT_CONFIG_FILE = "/switcher-server.properties";
    private JsonRPCServer jsonRPCServer;
    private Configure configure;
    private SwitcherServiceImpl switcherService;

    public SwitcherServer() {
        this(DEFAULT_CONFIG_FILE);
    }

    public SwitcherServer(String filename) {
        LOGGER.warn("switcher-server 正在启动！");
        configure = new Configure(filename);
        SwitcherDAO switcherDAO = new SwitcherDAO(configure);
        switcherService = new SwitcherServiceImpl(configure, switcherDAO);
        CheckMethodImpl checkMethodImpl = new CheckMethodImpl(switcherService);
        OnMethodImpl onMethodImpl = new OnMethodImpl(switcherService);
        OffMethodImpl offMethodImpl = new OffMethodImpl(switcherService);
        SetMethodImpl setMethodImpl = new SetMethodImpl(switcherService);
        ListMethodImpl listMethodImpl = new ListMethodImpl(switcherService);
        try {
            jsonRPCServer = JsonRPCServer.defaultJsonRPCServer(configure.getListenPort(), 1, 1);
            jsonRPCServer.register(checkMethodImpl);
            jsonRPCServer.register(onMethodImpl);
            jsonRPCServer.register(offMethodImpl);
            jsonRPCServer.register(setMethodImpl);
            jsonRPCServer.register(listMethodImpl);
            jsonRPCServer.start();
        } catch (Throwable e) {
            throw new SwitcherServerException("JsonRPCServer 异常", e);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                close();
            }
        });
    }

    public void close() {
        if (jsonRPCServer != null) {
            try {
                LOGGER.warn("switcher-server 正在关闭！");
                jsonRPCServer.shutdown();
                jsonRPCServer = null;
            } catch (Throwable e) {
                throw new SwitcherServerException("JsonRPCServer 异常", e);
            }
        }
    }

    public Configure getConfigure() {
        return configure;
    }

    public SwitcherService getSwitcherService() {
        return this.switcherService;
    }
}
