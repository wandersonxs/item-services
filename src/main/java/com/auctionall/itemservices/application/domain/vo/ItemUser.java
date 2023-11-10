package com.auctionall.itemservices.application.domain.vo;

import com.auctionall.itemservices.application.domain.Item;
import com.auctionall.itemservices.application.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.result.view.RequestContext;

//public record ItemUser(Item itemm, User user) {
//}

@Data
@NoArgsConstructor
public class ItemUser {
    private User user;
    private Item item;

    public ItemUser(User user){

    }

    public ItemUser(User user, Item item){

    }

//        public static void setUser(ItemUser itemUser){
//        TransactionRequestDto dto = new TransactionRequestDto();
//        dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
//        dto.setAmount(requestContext.getProductDto().getPrice());
//        requestContext.setTransactionRequestDto(dto);
//    }


}