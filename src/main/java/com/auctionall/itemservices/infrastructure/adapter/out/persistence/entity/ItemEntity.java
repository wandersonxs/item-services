package com.auctionall.itemservices.infrastructure.adapter.out.persistence.entity;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import com.auctionall.itemservices.application.domain.shared.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "item")
public class ItemEntity {

    @Id
    private Integer id;
    private Integer userId;
    private String name;
    private BigDecimal estimatedValue;
    private String status;


    public Item toDomain() {
        return new Item(
                id,
                new User(userId, null, null, null),
                name,
                estimatedValue,
                Status.valueOf(status)
        );
    }

    public static ItemEntity fromDomain(Item item) {
        var entity = new ItemEntity();
        entity.setId(item.id());
        entity.setUserId(item.user().id());
        entity.setName(item.name());
        entity.setEstimatedValue(item.estimatedValue());
        entity.setStatus(item.status().name());
        return entity;
    }

}
