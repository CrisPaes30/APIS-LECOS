package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Response {
    private Data data;

    // Getters e setters
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Getter
    @Setter
    public static class Data {
        private List<Item> items;

        // Getters e setters
        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Item {
        private String itemId;
        private MessageData data;
        private List<ErrorDetail> error;

        // Getters e setters
        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public MessageData getData() {
            return data;
        }

        public void setData(MessageData data) {
            this.data = data;
        }

        public List<ErrorDetail> getError() {
            return error;
        }

        public void setError(List<ErrorDetail> error) {
            this.error = error;
        }
    }

    public static class MessageData {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ErrorDetail {
        private String code;
        private String message;
        private String action;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}

