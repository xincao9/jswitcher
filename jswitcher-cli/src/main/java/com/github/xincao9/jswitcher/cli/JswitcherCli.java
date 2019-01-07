/*
 * Copyright 2019 xingyunzhi.
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
package com.github.xincao9.jswitcher.cli;

import com.github.xincao9.jsonrpc.JsonRPCClient;
import com.github.xincao9.jsonrpc.Request;
import java.util.ArrayList;
import java.util.List;
import org.jline.reader.Completer;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 *
 * @author xincao9@gmail.com
 */
public class JswitcherCli {

    public static void main(String... args) throws Throwable {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        Completer completer = new ArgumentCompleter(new StringsCompleter("connect", "check", "on", "off", "set", "list"), NullCompleter.INSTANCE);
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .history(new DefaultHistory())
                .build();
        String prompt = "cmd> ";
        JsonRPCClient jsonRPCClient = JsonRPCClient.defaultJsonRPCClient();
        jsonRPCClient.start();
        String host = "127.0.0.1";
        short port = 12306;
        while (true) {
            try {
                String line = lineReader.readLine(prompt);
                String[] ss = line.split("\\s");
                List<Object> params = new ArrayList();
                if (ss == null || ss.length == 0) {
                } else if (ss.length == 1 && "list".equalsIgnoreCase(ss[0])) {
                    Request request = createRequest(host, port, ss[0], null);
                    jsonRPCClient.invoke(request);
                } else if (ss.length == 2 && ("check".equalsIgnoreCase(ss[0]) || "on".equalsIgnoreCase(ss[0]) || "off".equalsIgnoreCase(ss[0]))) {
                    params.add(ss[1]);
                    Request request = createRequest(host, port, ss[0], params);
                    jsonRPCClient.invoke(request);
                } else if (ss.length == 3 && ("set".equalsIgnoreCase(ss[0]) || "connect".equalsIgnoreCase(ss[0]))) {
                    if ("connect".equalsIgnoreCase(ss[0])) {
                        host = ss[1];
                        port = Short.valueOf(ss[2]);
                    } else {
                        params.add(ss[1]);
                        params.add(Boolean.valueOf(ss[2]));
                        Request request = createRequest(host, port, "set", params);
                        jsonRPCClient.invoke(request);
                    }
                }
            } catch (UserInterruptException e) {
            } catch (EndOfFileException e) {
                jsonRPCClient.shutdown();
                return;
            }
        }
    }

    public static Request createRequest(String host, short port, String method, List<Object> params) throws Throwable {
        Request request = Request.createRequest(Boolean.TRUE, method, params);
        request.setHost(host);
        request.setPort(port);
        return request;
    }
}
