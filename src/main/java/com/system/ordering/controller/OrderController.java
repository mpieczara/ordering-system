package com.system.ordering.controller;

import com.system.ordering.assembler.OrderModelAssembler;
import com.system.ordering.dto.OrderDto;
import com.system.ordering.model.Order;
import com.system.ordering.service.OrderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler orderModelAssembler;

    public OrderController(OrderService orderService, OrderModelAssembler orderModelAssembler) {
        this.orderService = orderService;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> retrieveAllOrders() {

        List<EntityModel<Order>> orders = orderService.findAll().stream()
                .map(orderModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).retrieveAllOrders()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Order>> createOrder(@RequestBody OrderDto order) {
        EntityModel<Order> entityModel = orderModelAssembler.toModel(orderService.save(order));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);

        return orderModelAssembler.toModel(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Order>> updateOrder(@RequestBody Order newOrder, @PathVariable Long id) {

        Order updatedOrder = orderService.update(id, newOrder);

        EntityModel<Order> entityModel = orderModelAssembler.toModel(updatedOrder);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
