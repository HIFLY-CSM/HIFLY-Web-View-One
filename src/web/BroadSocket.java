package web;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint는 클라이언트에서 서버로 접속 할 주소로 지정합니다.
@ServerEndpoint("/broadcasting")
public class BroadSocket {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    
    
  //@OnMessage에서는 클라이언트로 부터 메시지가 도착했을때 처리입니다.
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
        synchronized(clients) {
            for(Session client : clients) {
                if(!client.equals(session)) {
                    client.getBasicRemote().sendText(message);
                }
            }
        }
    }
    
    //@OnOpen은 클라이언트에서 서버로 접속할 때의 처리입니다.
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session);
        clients.add(session);
    }
    
    //@OnClose는 접속이 끊겼을때 처리입니다.
    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }
}
 




//AIzaSyA1YDH8TKTtlCY3SzCHcPI80bhLFeymhwM