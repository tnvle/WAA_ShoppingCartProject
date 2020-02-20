package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {


    @Query("select o from Order o where o.user.id = :userId")
    List<Order> getAllOrdersByUser(Long userId);

}
