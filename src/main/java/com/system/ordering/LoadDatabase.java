package com.system.ordering;

import com.system.ordering.model.Order;
import com.system.ordering.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(OrderRepository repository) {

        return args -> {
            repository.save(new Order("Ananas", "Jan Nowak", LocalDate.of(2020, 5, 6)));
            repository.save(new Order("Banan", "Krzysztof Nowak", LocalDate.of(2020, 7, 30)));
        };
    }
}
