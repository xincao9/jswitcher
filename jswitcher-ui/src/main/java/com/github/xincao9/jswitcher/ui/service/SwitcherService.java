package com.github.xincao9.jswitcher.ui.service;

import com.github.xincao9.jsonrpc.core.DiscoveryService;
import com.github.xincao9.jsonrpc.core.protocol.Endpoint;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author xincao9@gmail.com
 */
@Service
public class SwitcherService {

    @Autowired
    private DiscoveryService discoveryService;

    public List<Endpoint> query (String service) {
        return discoveryService.query(service);
    }
}
