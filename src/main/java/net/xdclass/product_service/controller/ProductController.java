package net.xdclass.product_service.controller;

import net.xdclass.product_service.domain.Product;
import net.xdclass.product_service.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {


    @Autowired
    HttpServletRequest request;
//    @Autowired
//    HttpServletResponse response;

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("list")
    public Object list(){
        return productService.listProduct();
    }


    /**
     * 根据id查找商品详情
     * @param id
     * @return
     */
    @RequestMapping("find")
    public Object findById(int id, HttpServletResponse response){
        System.out.println("receive commit...");

        //模拟超时
//        try {
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        String requestId = request.getHeader("request-id");
        System.out.println("request-id :" + requestId);
        response.setHeader("request-id", requestId);

        Product product = productService.findById(id);
        Product result = new Product();
        BeanUtils.copyProperties(product,result);
        result.setName( result.getName() + " data from port="+port );
        System.out.println(result.toString());
        return result;
    }

    @PostMapping("save")
    public Object saveProduct(@RequestBody Product product, HttpServletResponse response){
        String requestId = request.getHeader("request-id");
        System.out.println("request-id by post:" + requestId);
        response.setHeader("request-id-response", requestId);
        productService.saveProduct(product);
        throw new RuntimeException("错误啦！");
//        return product;
    }

}
