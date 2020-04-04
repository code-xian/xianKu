package zzx.jxc.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zzx.jxc.config.PushMessage;
import zzx.jxc.config.service.SocketIoService;

@RestController
@RequestMapping("/socket")
public class SocketController {

    @Autowired
    private SocketIoService socketIoService;

    @GetMapping("/send")
    @CrossOrigin(origins = "*")
    public String sendMsg(@RequestParam("content") String content, @RequestParam("loginUser") String loginUser) {
        System.out.println("好是是");
        socketIoService.pushMessageToUser(new PushMessage(loginUser, "", content));
        return "OK";
    }

    @GetMapping("/sendBroadcast")
    @CrossOrigin(origins = "*")
    public String sendBroadcast() {
        System.out.println("fffffff");
        socketIoService.sendBroadcast();
        return "OK";
    }
}
