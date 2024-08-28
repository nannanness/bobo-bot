package org.cherrygirl;


import cn.hutool.extra.spring.SpringUtil;
import org.cherrygirl.core.*;
import org.cherrygirl.chinesechess.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * @author nannanness
 */
@SpringBootApplication
public class Main implements ApplicationRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Main.class)
                // 指定非 web 模式
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        System.out.println("====== main启动 =====");
        Tool.initPath("E:\\IdeaProjects\\cherrygirl\\resouces" + "\\chessImage");
        ChessControl control =  new ChessControl("木质");
        control.start();
        control.move("炮二平五");
        control.move("马2进3");
//        control.retract();
    }
}