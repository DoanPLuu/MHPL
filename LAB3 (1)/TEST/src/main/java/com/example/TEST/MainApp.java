package com.example.TEST;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import org.hibernate.query.Query;

public class MainApp {
    public static void main(String[] args) {
        // Tạo SessionFactory từ Hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderItem.class)
                .buildSessionFactory();

        // Mở session
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Thêm dữ liệu mẫu
            Customer customer = new Customer();
            customer.setName("Dinh Ba Do");
            customer.setEmail("nguyenvana@gmail.com");
            customer.setPhone("0987654321");
            customer.setAddress("Dong Nai");
            session.save(customer);

            Product product1 = new Product();
            product1.setName("Laptop Dell");
            product1.setPrice(15000000);
            session.save(product1);

            Product product2 = new Product();
            product2.setName("Dien thoai iPhone");
            product2.setPrice(22000000);
            session.save(product2);

            Order order = new Order();
            order.setCustomer(customer);
            session.save(order);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOrder(order);
            orderItem1.setProduct(product1);
            orderItem1.setQuantity(1);
            session.save(orderItem1);

            OrderItem orderItem2 = new OrderItem();
            orderItem2.setOrder(order);
            orderItem2.setProduct(product2);
            orderItem2.setQuantity(2);
            session.save(orderItem2);

            transaction.commit();

            // Truy vấn 1: Lấy tất cả đơn hàng của một khách hàng cụ thể
            System.out.println("Danh sach don hang cua khach hang ID 1:");
            Query<Order> query1 = session.createQuery("FROM Order o WHERE o.customer.id = 1", Order.class);
            List<Order> orders = query1.list();
            for (Order o : orders) {
                System.out.println("Order ID: " + o.getId() + ", Ngay dat hang: " + o.getOrderDate());
            }

            // Truy vấn 2: Tính tổng giá trị của tất cả các đơn hàng
            System.out.println("Tong gia tri tung don hang:");
            Query<Object[]> query2 = session.createQuery(
                    "SELECT o.id, SUM(p.price * oi.quantity) FROM Order o JOIN o.orderItems oi JOIN oi.product p GROUP BY o.id",
                    Object[].class);
            List<Object[]> orderTotals = query2.list();
            for (Object[] row : orderTotals) {
                System.out.println("Order ID: " + row[0] + ", Tong gia tri: " + row[1]);
            }

            // Truy vấn 3: Tìm sản phẩm bán chạy nhất
            System.out.println("San pham ban chay nhat:");
            Query<Object[]> query3 = session.createQuery(
                    "SELECT p.name, SUM(oi.quantity) FROM OrderItem oi JOIN oi.product p GROUP BY p.id ORDER BY SUM(oi.quantity) DESC",
                    Object[].class);
            List<Object[]> bestSellingProducts = query3.setMaxResults(1).list();
            if (!bestSellingProducts.isEmpty()) {
                System.out.println("Ten san pham: " + bestSellingProducts.get(0)[0] + ", So luong da ban: "
                        + bestSellingProducts.get(0)[1]);
            }

            // Truy vấn 4: Phân trang danh sách đơn hàng
            System.out.println("Danh sach don hang (phan trang):");
            Query<Order> query4 = session.createQuery("FROM Order", Order.class);
            query4.setFirstResult(0);
            query4.setMaxResults(5);
            List<Order> paginatedOrders = query4.list();
            for (Order o : paginatedOrders) {
                System.out.println("Order ID: " + o.getId() + ", Ngay dat hang: " + o.getOrderDate());
            }

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
}
