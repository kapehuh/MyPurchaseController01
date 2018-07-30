package example.st.ru.mypurchasecontroller01.model;

public class Item {

        private String item_price;
        private String item_shop;

        public Item(String price, String shop) {

            this.item_price = price;
            this.item_shop = shop;
        }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_shop() {
        return item_shop;
    }

    public void setItem_shop(String item_shop) {
        this.item_shop = item_shop;
    }
}
