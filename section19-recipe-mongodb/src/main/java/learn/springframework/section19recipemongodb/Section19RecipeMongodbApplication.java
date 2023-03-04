package learn.springframework.section19recipemongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Section19RecipeMongodbApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Section19RecipeMongodbApplication.class, args);

        System.out.println("+++++++++++++Started++++++++++++++++");
        for(String beanName : ctx.getBeanDefinitionNames()) {
            String name = ctx.getBean(beanName).getClass().getName();
            if(name.startsWith("learn.springframework.section19recipemongodb")) {
                System.out.println(name);
            }
        }
    }

}
