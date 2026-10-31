package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.javaqa.AlreadyExistsException;
import ru.netology.javaqa.NotFoundException;
import ru.netology.javaqa.Product;
import ru.netology.javaqa.ShopRepository;

public class ShopRepositoryTest {

    @Test
    public void shouldRemoveExistingProduct() {
        ShopRepository repo = new ShopRepository();
        Product p1 = new Product(1, "Хлеб", 50);
        Product p2 = new Product(2, "Молоко", 80);
        Product p3 = new Product(3, "Сыр", 300);
        repo.add(p1);
        repo.add(p2);
        repo.add(p3);

        repo.remove(2);

        Product[] expected = {p1, p3};
        Assertions.assertArrayEquals(expected, repo.findAll());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenRemovingMissingProduct() {
        ShopRepository repo = new ShopRepository();
        repo.add(new Product(1, "Хлеб", 50));

        Assertions.assertThrows(NotFoundException.class, () -> repo.remove(100));
    }

    @Test
    public void shouldAddProduct() {
        ShopRepository repo = new ShopRepository();
        Product p1 = new Product(1, "Хлеб", 50);

        repo.add(p1);

        Assertions.assertArrayEquals(new Product[]{p1}, repo.findAll());
    }

    @Test
    public void shouldThrowAlreadyExistsExceptionWhenAddingDuplicateId() {
        ShopRepository repo = new ShopRepository();
        repo.add(new Product(1, "Хлеб", 50));

        Assertions.assertThrows(AlreadyExistsException.class,
                () -> repo.add(new Product(1, "Другой товар", 10)));
    }

    @Nested
    class ProductTest {

        @Test
        public void shouldGetAndSetFields() {
            Product product = new Product(1, "Хлеб", 50);

            product.setTitle("Молоко");
            product.setPrice(80);

            Assertions.assertEquals(1, product.getId());
            Assertions.assertEquals("Молоко", product.getTitle());
            Assertions.assertEquals(80, product.getPrice());
        }

        @Test
        public void shouldBeEqualWithSameFields() {
            Product a = new Product(1, "Хлеб", 50);
            Product b = new Product(1, "Хлеб", 50);

            Assertions.assertEquals(a, b);
            Assertions.assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        public void shouldNotBeEqualWithDifferentFields() {
            Product a = new Product(1, "Хлеб", 50);

            Assertions.assertNotEquals(new Product(2, "Хлеб", 50), a);
            Assertions.assertNotEquals(new Product(1, "Молоко", 50), a);
            Assertions.assertNotEquals(new Product(1, "Хлеб", 60), a);
            Assertions.assertFalse(a.equals(null));
            Assertions.assertFalse(a.equals("строка"));
            Assertions.assertEquals(a, a);
        }
    }
}
