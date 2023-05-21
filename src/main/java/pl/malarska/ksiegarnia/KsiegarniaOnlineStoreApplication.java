package pl.malarska.ksiegarnia;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KsiegarniaOnlineStoreApplication {

    public static void main(String[] args) {
        //ConfigurableApplicationContext context =
        SpringApplication.run(KsiegarniaOnlineStoreApplication.class, args);
        //System.out.println(context);
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
