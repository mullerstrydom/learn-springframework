package learn.springframework.section7recipe;

import learn.springframework.section7recipe.domain.Category;
import learn.springframework.section7recipe.domain.UnitOfMeasure;
import learn.springframework.section7recipe.repositories.CategoryRepository;
import learn.springframework.section7recipe.repositories.UnitOfMeasureRepository;
import org.aspectj.weaver.Iterators;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import java.util.stream.StreamSupport;

@SpringBootApplication
public class Section7RecipeApplication {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Section7RecipeApplication.class, args);

        for(String beanName : ctx.getBeanDefinitionNames()) {
            String className = ctx.getBean(beanName).getClass().getName();
            if(className.startsWith("learn")) {
                System.out.println(beanName);
            }

        }

    }


}
