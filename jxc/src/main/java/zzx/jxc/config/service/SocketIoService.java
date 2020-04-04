package zzx.jxc.config.service;

import zzx.jxc.config.PushMessage;
import zzx.jxc.config.entity.LoginUser;
import zzx.jxc.config.entity.PushStockMsg;

public interface SocketIoService {
    /**
     * 推送的事件
     */
    String PUSH_EVENT = "push_event";

    /**
     * 启动服务
     *
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 停止服务
     */
    void stop();

    /**
     * 推送信息
     *
     * @param pushMessage
     */
    void pushMessageToUser(PushMessage pushMessage);
    /**
     * 推送信息
     *
     * @param pushStockMsg
     */
    void pushStockMessage(PushStockMsg pushStockMsg);
    /**
     * 获取登陆人信息
     *
     * @param loginUser
     */
    public void pullLoginUser(LoginUser loginUser);

    void sendBroadcast();
}
