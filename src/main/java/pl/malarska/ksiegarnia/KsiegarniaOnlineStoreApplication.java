package pl.malarska.ksiegarnia;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.malarska.ksiegarnia.catalog.domain.CatalogRepository;
import pl.malarska.ksiegarnia.catalog.infrastructure.BestSellerCatalogRepository;
import pl.malarska.ksiegarnia.catalog.infrastructure.SchoolCatalogRepository;

import java.util.Random;

@SpringBootApplication
public class KsiegarniaOnlineStoreApplication {

    public static void main(String[] args) {
        //ConfigurableApplicationContext context =
        SpringApplication.run(KsiegarniaOnlineStoreApplication.class, args);
        //System.out.println(context);
    }

    @Bean
    String query(){
        System.out.println("Tworzę Bean typu String");
        return  "Pan";
    }

//    @Bean
//    CatalogRepository catalogRepository() {
//        Random random = new Random();
//        if (random.nextBoolean()) {
//            System.out.println("Wybieam szkolne lektury");
//            return new SchoolCatalogRepository();
//        } else {
//            System.out.println("Wybieram bestsellery");
//            return new BestSellerCatalogRepository();
//        }
//    }
}
