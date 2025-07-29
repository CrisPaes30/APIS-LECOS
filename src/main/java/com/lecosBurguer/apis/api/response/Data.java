package com.lecosBurguer.apis.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public class Data {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
