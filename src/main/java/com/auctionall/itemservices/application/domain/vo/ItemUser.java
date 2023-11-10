package com.auctionall.itemservices.application.domain.vo;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.result.view.RequestContext;

@Data
@NoArgsConstructor
public class ItemUser {
    private User user;
    private Item item;

    public ItemUser(User user){
        this.user =user;
    }
    public ItemUser(User user, Item item){
        this.user = user;
        this.item = item;
    }

}