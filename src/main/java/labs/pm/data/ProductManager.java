package labs.pm.data;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProductManager {

//    private Product product;
//    private Review[] reviews = new Review[5];

    private static final Logger logger =
            Logger.getLogger(ProductManager.class.getName());

    private Map<Product, List<Review>> products = new HashMap<>();

    private static Map<String, ResourceFormatter> formatters =
            Map.of("en-GB", new ResourceFormatter(Locale.UK),
                    "en-US", new ResourceFormatter(Locale.US),
                    "fr-FR", new ResourceFormatter(Locale.FRANCE),
                    "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
                    "zh-CN", new ResourceFormatter(Locale.CHINA)
            );

    private ResourceFormatter formatter;

    private ResourceBundle config = ResourceBundle.getBundle("config");

    private MessageFormat reviewFormat = new MessageFormat(config.getString("review.data.format"));

    private MessageFormat productFormat = new MessageFormat(config.getString("product.data.format"));

    private Path reportsFolder = Path.of(config.getString("reports.folder"));

    private Path dataFolder = Path.of(config.getString("data.folder"));

    private Path tempFolder = Path.of(config.getString("temp.folder"));

    public void changeLocale(String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-GB"));
    }

    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    public ProductManager(String languageTag) {
        changeLocale(languageTag);
    }

    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }


    public void printProductReport(Product product) throws IOException {
        List<Review> reviews = products.get(product);
        Path productFile =
                reportsFolder.resolve(
                        MessageFormat.format(config.getString("report.file"), product.getId())
                );

        try (PrintWriter out = new PrintWriter((
                new OutputStreamWriter(
                        Files.newOutputStream(productFile,
                                StandardOpenOption.CREATE), StandardCharsets.UTF_8
                )
        ))) {

            out.append(formatter.formatProduct(product)).append(System.lineSeparator());


            if (reviews.isEmpty()) {
                out.append(formatter.getText("no.reviews")).append(System.lineSeparator());
            } else {
                out.append(reviews.stream()
                        .map(r -> formatter.formatReview(r) + System.lineSeparator())
                        .collect(Collectors.joining()));
            }

        }
//        for (Review review : reviews) {
//            if (review == null) break;
//            txt.append(formatter.formatReview(review));
//            txt.append("\n");
//        }
//        if (reviews.isEmpty()) {
//            txt.append(formatter.getText("no.reviews"));
//            txt.append("\n");
//        }


    }

    public void printProductReport(int id) {
        try {
            printProductReport(findProduct(id));
        } catch (ProductManagerException ex) {
            logger.log(Level.INFO, ex.getMessage());
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error printing product report " + ex.getMessage(), ex);
        }
    }

    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
//        List<Product> productList = new ArrayList<>(products.keySet());
//        productList.sort(sorter);
        StringBuilder txt = new StringBuilder();
        products.keySet()
                .stream()
                .sorted(sorter)
                .filter(filter)
                .forEach(p -> txt.append(formatter.formatProduct(p) + '\n'));
//        for (Product product : productList) {
//            txt.append(formatter.formatProduct(product));
//            txt.append("\n");
//        }
        System.out.println(txt);

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

        product = product.applyRating(
                Rateable.convert(
                        (int) Math.round(
                                reviews.stream()
                                        .mapToInt(r -> r.getRating().ordinal())
                                        .average()
                                        .orElse(0))));

//        int sum = 0;
//
//        for (Review review : reviews) {
//            sum += review.getRating().ordinal();
//        }
//
//        product = product.applyRating(Rateable.convert(Math.round((float) sum / reviews.size())));

        Collections.sort(reviews);

        products.put(product, reviews);

        return product;
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        try {
            return reviewProduct(findProduct(id), rating, comments);
        } catch (ProductManagerException ex) {
            logger.log(Level.INFO, ex.getMessage());
            return null;
        }
    }

    public Product findProduct(int id) throws ProductManagerException {

        return products.keySet().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(
                        () -> new ProductManagerException("Product with id " + id + " not found")
                );
//        Product result = null;
//        for (Product product : products.keySet()) {
//            if (product.getId() == id) {
//                result = product;
//                break;
//            }
//        }
//        return result;
    }

    public Map<String, String> getDiscounts() {
        return products.keySet()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                product -> product.getRating().getStars(),
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(
                                                product -> product.getDiscount().doubleValue()
                                        ),
                                        discount -> formatter.moneyFormat.format(discount)

                                )
                        )
                );
    }

    /**
     * To create a review base from a string of text.
     *
     * @param text String text
     */
    public void parseReview(String text) throws ProductManagerException {

        try {
            Object[] values = reviewFormat.parse(text);
            reviewProduct(Integer.parseInt((String) values[0]),
                    Rateable.convert(Integer.parseInt((String) values[1])),
                    (String) values[2]);
        } catch (ParseException | NumberFormatException ex) {
//            Logger.getLogger(ProductManager.class.getName())
//                    .log(Level.SEVERE, null, ex);
            logger.log(Level.WARNING, "Error parsing review " + text + " " + ex.getMessage());
            throw new ProductManagerException("Unable ot parse review", ex);
        }
    }

    /**
     * This is to create a Product base from a String
     *
     * @param text String input
     */
    public void parseProduct(String text) {
        try {
            Object[] values = productFormat.parse(text);
            int id = Integer.parseInt((String) values[1]);
            String name = (String) values[2];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble((String) values[3]));
            Rating rating = Rateable.convert(Integer.parseInt((String) values[4]));
            switch ((String) values[0]) {
                case "D":
                    createProduct(id, name, price, rating);
                    break;
                case "F":
                    LocalDate bestBefore = LocalDate.parse((String) values[5]);
                    createProduct(id, name, price, rating, bestBefore);
            }

        } catch (ParseException |
                 NumberFormatException |
                 DateTimeParseException ex) {
            logger.log(Level.WARNING, "Error parsing product " + text + " " + ex.getMessage());
        }
    }


    private static class ResourceFormatter {
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);

        }

        private String formatProduct(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(product.getBestBefore()));
        }

        private String formatReview(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(), review.getComments());
        }

        private String getText(String key) {
            return resources.getString(key);
        }
    }

}
