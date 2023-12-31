package labs.pm.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class ProductManager {

//    private Product product;
//    private Review[] reviews = new Review[5];

    private Map<Product, List<Review>> products = new HashMap<>();


    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormat;
    private NumberFormat moneyFormat;

    public ProductManager(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("resources", locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
        moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    public void printProductReport(Product product) {
        List<Review> reviews = products.get(product);
        StringBuilder txt = new StringBuilder();
        txt.append(MessageFormat.format(
                resources.getString("product"),
                product.getName(),
                moneyFormat.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormat.format(product.getBestBefore())));
        txt.append("\n");

        for (Review review : reviews) {
            if (review == null) break;
            txt.append(MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments()));
            txt.append("\n");
        }
        if (reviews.isEmpty()) {
            txt.append(resources.getString("no.reviews"));
            txt.append("\n");
        }

        System.out.println(txt);
    }

    public void printProductReport(int id){
        printProductReport(findProduct(id));
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;

    }

    public Product reviewProduct(Product product, Rating rating, String comments) {

        List<Review> reviews = products.get(product);

        products.remove(product, reviews);
        //you need to remove the entire entry from the map. this entry will be added back to
        // the map after
        // you will add a review and apply new rating to the product object
        reviews.add(new Review(rating, comments));

        int sum = 0;

        for (Review review : reviews) {
            sum += review.getRating().ordinal();
        }

        product = product.applyRating(Rateable.convert(Math.round((float) sum / reviews.size())));

        Collections.sort(reviews);

        products.put(product, reviews);

        return product;
    }

    public Product reviewProduct(int id, Rating rating, String comments){
        return reviewProduct(findProduct(id), rating, comments);
    }

    public Product findProduct(int id) {
        Product result = null;
        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        return result;
    }

}
