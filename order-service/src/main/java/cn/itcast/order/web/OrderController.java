package cn.itcast.order.web;

import cn.itcast.feign.client.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @Autowired
   UserClient userClient;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        //1.查询订单
        Order order = orderService.queryOrderById(orderId);
        //2.利用feign发起http请求，查询用户
        User user = userClient.findById(order.getUserId());
        order.setUser(user);
        return order;
    }

//   @Autowired
//   private RestTemplate restTemplate;
//
//    @GetMapping("{orderId}")
//    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
//        //1.查询订单
//        Order order = orderService.queryOrderById(orderId);
//        //2.利用restTemplate发起http请求，查询用户
//        String url = "http://userservice/user/" + order.getUserId();
//        User user = restTemplate.getForObject(url, User.class);
//        order.setUser(user);
//        return order;
//    }
}
