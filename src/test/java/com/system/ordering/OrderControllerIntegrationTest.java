package com.system.ordering;

import com.system.ordering.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllOrders() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/orders",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetOrderById() {
        Order employee = restTemplate.getForObject(getRootUrl() + "/orders/1", Order.class);
        assertNotNull(employee);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order("Cola", "Jacek Nowak", LocalDate.of(2020, 4, 6));

        ResponseEntity<Order> postResponse = restTemplate.postForEntity(getRootUrl() + "/orders", order, Order.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateOrder() {
        int id = 1;
        Order order = restTemplate.getForObject(getRootUrl() + "/orders/" + id, Order.class);
        order.setName("Woda");
        order.setCustomer("Pawel Woda");

        restTemplate.put(getRootUrl() + "/orders/" + id, order);

        Order updatedOrder = restTemplate.getForObject(getRootUrl() + "/orders/" + id, Order.class);
        assertNotNull(updatedOrder);
    }

    @Test
    public void testIfExceptionIsThrown() {
        int notExistedId = 1234;
        assertThrows(RestClientException.class,
                () -> restTemplate.getForObject(getRootUrl() + "/orders/" + notExistedId, Order.class));
    }
}
