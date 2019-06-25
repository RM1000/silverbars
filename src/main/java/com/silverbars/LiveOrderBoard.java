package com.silverbars;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LiveOrderBoard {

    private final ConcurrentHashMap<String , List<Order>>  userToOrders =  new ConcurrentHashMap<>();


    public boolean register(Order order){
        List<Order> userOrders = userToOrders.computeIfAbsent(order.getUser(),t->new ArrayList<>());
        return userOrders.add(order);
    }

    public boolean cancel(Order order){

        List<Order> userOrders = userToOrders.getOrDefault(order.getUser(), Collections.emptyList());
        return userOrders.remove(order);
    }

    public List<Summary> getLiveOrderBoard(){
        Map<Type,TreeMap<BigDecimal,BigDecimal>> liveBoard = userToOrders.values().stream().flatMap(t->t.stream()).
                collect(Collectors.groupingBy(Order::getType,
                Collectors.groupingBy(Order::getPrice, TreeMap::new,
                        Collectors.reducing(BigDecimal.ZERO,Order::getQuantity,BigDecimal::add))));

        List<Summary> summaries = new ArrayList<>();
        summaries.addAll(getSummary(Type.SELL,liveBoard.getOrDefault(Type.SELL,new TreeMap<>()))); // ascending order for price
        summaries.addAll(getSummary(Type.BUY,liveBoard.getOrDefault(Type.BUY,new TreeMap<>()).descendingMap())); // descending order for price
        return summaries;
    }

    private List<Summary> getSummary(Type type, Map<BigDecimal,BigDecimal> priceToQuantity){
        return priceToQuantity.entrySet().stream().map(t->new Summary(t.getKey(),t.getValue(),type)).collect(Collectors.toList());
    }

}
