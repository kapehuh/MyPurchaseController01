package example.st.ru.mypurchasecontroller01.model;

public class Goods {

        private String itemName;
        private String itemPrice;
        private int imgResId;

        public Goods(String itemName, String itemPrice, int imgResId) {
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.imgResId = imgResId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public int getImgResId() {
            return imgResId;
        }

        public void setImgResId(int imgResId) {
            this.imgResId = imgResId;
        }
}
