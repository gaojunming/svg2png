package cn.newobj;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //启动spring程序
        SpringApplication.run(App.class, args);
    }

    /**
     * 基于JBrowserDriver方式，依赖webkit实现
     */
    /*public final static JBrowserDriver driver = new JBrowserDriver();
    public static void main(String[] args) {
        //加载转换页面
        driver.get(ClassLoader.getSystemResource("svg_to_png.html").toString());
        //启动spring程序
        SpringApplication.run(App.class, args);
        //退出：关闭browser
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                driver.quit();
            }
        }));
    }*/
}
