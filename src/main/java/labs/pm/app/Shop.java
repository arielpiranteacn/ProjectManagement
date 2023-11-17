package labs.pm.app;

import labs.pm.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * {@code Shop} class represents an application that manages Products
 *
 * @author oracle
 * @version 4.0
 */
public class Shop {

    public static void main(String[] args) throws ProductManagerException {
//        ProductManager pm = new ProductManager(Locale.CHINA);
        ProductManager pm = new ProductManager(Locale.UK);
//        pm.printProductReport(101);
//        pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
//        pm.parseProduct("D,101,Tea,1.99,0,2019-09-19"); // date would not be used.
//        pm.parseProduct("D,101,Tea,1.99,0"); // has intended errors
//        pm.parseReview("101,4,Nice hot cup of tea");
//        pm.parseReview("101,2,Rather weak tea");
//        pm.parseReview("101,4,Fine tea");
//        pm.parseReview("101,4,Good tea");
//        pm.parseReview("101,5,Perfect tea");
//        pm.parseReview("101,3,Just add some lemon");
//        pm.parseReview("101,4,Nice hot cup of tea");


//        pm.printProductReport(42); //erroneous non existent id

//        pm.reviewProduct(101, Rating.TWO_STAR, "Rather weak tea");
//        pm.reviewProduct(101, Rating.FOUR_STAR, "Fine tea");
//        pm.reviewProduct(101, Rating.FOUR_STAR, "Good tea");
//        pm.reviewProduct(101, Rating.FIVE_STAR, "Perfect tea");
//        pm.reviewProduct(101, Rating.THREE_STAR, "Just add some lemon");
//        pm.reviewProduct(42, Rating.FOUR_STAR, "Nice hot cup of tea.");

//        pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
//        pm.parseProduct("D,102,Coffee,1.99,0,2019-09-19"); //
//        pm.parseReview("102,3,Coffee was ok");
//        pm.parseReview("102,1,Where is the milk?!");
//        pm.parseReview("102,5,It's perfect with ten spoons of sugar!");
//        pm.printProductReport(102);

//        pm.reviewProduct(102, Rating.THREE_STAR, "Coffee was ok");
//        pm.reviewProduct(102, Rating.ONE_STAR, "Where is the milk?!");
//        pm.reviewProduct(102, Rating.FIVE_STAR, "It's perfect with ten spoons of sugar!");
//        pm.printProductReport(102);
//
//        pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.NOT_RATED,
//                LocalDate.now().plusDays(2));
//        pm.parseProduct("F,103,Cake,1.99,0,2019-09-49"); //erroneous date
//        pm.parseProduct("F,103,Cake,1.99,0," + LocalDate.now().plusDays(2)); //erroneous date
//        pm.parseReview("103,5,Very nice cake");
//        pm.parseReview("103,4,It is good, but I've expected more chocolate");
//        pm.parseReview("103,5,This cake is perfect!");
//        pm.printProductReport(103);

//        pm.reviewProduct(103, Rating.FIVE_STAR, "Very nice cake");
//        pm.reviewProduct(103, Rating.FOUR_STAR,
//                "It is good, but I've expected more chocolate");
//        pm.reviewProduct(103, Rating.FIVE_STAR, "This cake is perfect!");
//        pm.printProductReport(103);

//        pm.createProduct(104, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED,
//                LocalDate.now());
//        pm.parseProduct("F,104,Cookie,2.99,0," + LocalDate.now()); //
//        pm.parseReview("104,3,Just another cookie");
//        pm.parseReview("104,3,Ok");
//        pm.printProductReport(104);

//        pm.reviewProduct(104, Rating.THREE_STAR, "Just another cookie");
//        pm.reviewProduct(104, Rating.THREE_STAR, "Ok");

//        pm.createProduct(105, "Hot Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED);
//        pm.parseProduct("D,105,Cookie,2.50,0," + LocalDate.now()); //
//        pm.parseReview("105,4,Tasty!");
//        pm.parseReview("105,4,Not bad at all");
//        pm.printProductReport(105);
//        pm.reviewProduct(105, Rating.FOUR_STAR, "Tasty!");
//        pm.reviewProduct(105, Rating.FOUR_STAR, "Not bad at all");


//        pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.5), Rating.NOT_RATED,
//                LocalDate.now().plusDays(3));
//        pm.parseProduct("D,106,Chocolate,2.50,0," + LocalDate.now().plusDays(3)); //

//        pm.parseReview("106,2,Too sweet");
//        pm.parseReview("106,3,Better than cookie");
//        pm.parseReview("106,2,Too bitter");
//        pm.parseReview("106,1,I don't get it!");
//        pm.printProductReport(106);

//        pm.reviewProduct(106, Rating.TWO_STAR, "Too sweet");
//        pm.reviewProduct(106, Rating.THREE_STAR, "Better than cookie");
//        pm.reviewProduct(106, Rating.TWO_STAR, "Too bitter");
//        pm.reviewProduct(106, Rating.ONE_STAR, "I don't get it!");
//        pm.printProductReport(106);

        //non existent id
//        pm.printProductReport(42);
//        System.out.println("--------------");
//        //Filter to filter products that has a Price less than 2
//        Predicate<Product> filter = p -> p.getPrice().floatValue() < 2;
//        pm.printProducts(filter, (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal());
//
//        pm.printProducts(filter, (p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
//
//        Comparator<Product> ratingSorter = (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
//        Comparator<Product> priceSorter = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
//
//        pm.printProducts(filter, ratingSorter.thenComparing(priceSorter));
//
//        pm.printProducts(filter, ratingSorter.thenComparing(priceSorter).reversed());
//
//        pm.getDiscounts().forEach((rating, discount) -> System.out.println(rating + "\t" + discount));
//        Product p2 = pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STAR);
//        Product pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
//        Product pm.createProduct(105, "Cookie", BigDecimal.valueOf(3.99), Rating.TWO_STAR, LocalDate.now());
//        Product p3.applyRating(Rating.THREE_STAR);
//        Product p8 = p4.applyRating(Rating.FIVE_STAR);
//        Product p9 = p1.applyRating(Rating.TWO_STAR);
//
//        System.out.println(p1);
//        System.out.println(p2);
//        System.out.println(p3);
//        System.out.println(p4);
//        System.out.println(p5);
//        System.out.println(p8);
//        System.out.println(p9);
//
//        Product pm.createProduct(104, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STAR);
//        Product p7 = pm.createProduct(104, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STAR, LocalDate.now().plusDays(2));
//
//        System.out.println(p6.equals(p7));
//        System.out.println(p3.getBestBefore());
//        System.out.println(p1.getBestBefore());

        pm.printProductReport(101);
        pm.createProduct(164, "Kombucha", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
        pm.reviewProduct(164, Rating.TWO_STAR, "Looks like tea but is it?");
        pm.reviewProduct(164, Rating.FOUR_STAR, "Fine tea");
        pm.reviewProduct(164, Rating.FOUR_STAR, "This is not tea");
        pm.reviewProduct(164, Rating.FIVE_STAR, "Perfect!");

        pm.printProductReport(164);
        pm.printProducts(p -> p.getPrice().floatValue() < 2,
                (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal());
        pm.getDiscounts().forEach(
                (rating, discount) -> System.out.println(rating + "\t" + discount)
        );

    }


}
