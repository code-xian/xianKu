package zzx.jxc.config.service.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzx.jxc.config.PushMessage;
import zzx.jxc.config.entity.LoginUser;
import zzx.jxc.config.entity.PushStockMsg;
import zzx.jxc.config.service.SocketIoService;
import zzx.jxc.foodInfo.dao.FoodInfoDao;
import zzx.jxc.foodStock.Dao.FoodStockDao;
import zzx.jxc.foodStock.entity.FoodStock;
import zzx.jxc.stock.dao.StockDao;
import zzx.jxc.stock.entity.Stock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author linzf
 * @since 2019-06-13
 * 类描述：socket的实现类
 */
@Service(value = "socketIOService")
public class SocketIoServiceImpl implements SocketIoService {
    /**
     * 用来存已连接的客户端
     */
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    /**
     * socketIo的对象
     */
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private FoodStockDao foodStockDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private FoodInfoDao foodInfoDao;

    /**
     * 功能描述：当前的service被初始化的时候执行以下的方法
     * @throws Exception
     */
    @PostConstruct
    private void autoStartUp() throws Exception {
        start();
    }

    /**
     * 功能描述：当我们的系统停止的时候关闭我们的socketIo
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception {
        stop();
    }

    /**
     * 启动服务
     *
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            /**
             * 此处实现我们的socket的连接的用户的逻辑，此处我前端传的是loginUser这个参数，大家可以根据自己的情况来定义入参
             */
            String loginUser = this.getParamsByClient(client).get("loginUser").get(0);
            System.out.println("成功连接");
            clientMap.put(loginUser, client);

            System.out.println(clientMap);

            List<Stock> stockList = stockDao.findAll();
            List<String> stockIdList = stockList.stream().map(Stock::getStockId).collect(Collectors.toList());
            List<FoodStock> allByStockIdIn = foodStockDao.findAllByStockIdIn(stockIdList);
            for (FoodStock foodStock : allByStockIdIn) {
                if (foodStock.getStock() <= 10) {
                    System.out.println(foodStock.getStockId() + "仓库的" + foodStock.getFoodId() + "食品低于警戒线,请及时采购补充");
                    client.sendEvent("stock",stockDao.findStockByStockId(foodStock.getStockId()).getStockName()+"("+foodStock.getStockId() + ")仓库的" + foodInfoDao.findFoodInfoByFoodId(foodStock.getFoodId()).getFoodName()+"("+foodStock.getFoodId() + ")食品低于警戒线,请及时采购补充.");
                }
            }

        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String loginUser = getParamsByClient(client).get("loginUser").get(0);
            if (loginUser != null && !"".equals(loginUser)) {
                clientMap.remove(loginUser);
                client.disconnect();
                System.out.println(loginUser + "下线了");
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener(PUSH_EVENT, Object.class, (client, data, ackSender) -> {
            // TODO do something
            System.out.println("aaaaaaaaaaaaaa");
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener("stock", Object.class, (client, data, ackSender) -> {
            System.out.println("触发了stock监听器");
        });



        socketIOServer.start();

    }
    /**
     * 停止服务
     */
    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    /**
     * 推送信息
     *
     * @param pushMessage
     */
    @Override
    public void pushMessageToUser(PushMessage pushMessage) {
        SocketIOClient socketIOClient = clientMap.get(pushMessage.getLoginUser());
        socketIOClient.sendEvent(PUSH_EVENT, pushMessage);
    }

    @Override
    public void pushStockMessage(PushStockMsg pushStockMsg) {
//        SocketIOClient socketIOClient = clientMap.get(pushStockMsg.getLoginUser());
//        socketIOClient.disconnect();
    }

    @Override
    public void pullLoginUser(LoginUser loginUser) {
        SocketIOClient socketIOClient = clientMap.get(loginUser.getLoginUser());
        socketIOClient.disconnect();
    }

    @Override
    public void sendBroadcast() {
        int i = 1;
        for (SocketIOClient client : clientMap.values()) {
            if (client.isChannelOpen()) {
                System.out.println(i+":"+client);
                i++;
//                client.sendEvent("Broadcast", "当前时间", System.currentTimeMillis());
            }
        }

    }
    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     *
     * @param client
     * @return
     */
    private Map<String, List<String>> getParamsByClient(SocketIOClient client) {
        // 从请求的连接中拿出参数
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        System.out.println(client);
        System.out.println(params);
        return params;
    }

}
