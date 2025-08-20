
package models;

public class CartItem {
    private int cartItemID;
    private int userID;
    private Categories Category ;
    private WarrantyDetails warranty;
    private int quantity;
    private int Status;

    public CartItem() {
    }

    public CartItem(int cartItemID, int userID, Categories Category, WarrantyDetails warranty, int quantity, int Status) {
        this.cartItemID = cartItemID;
        this.userID = userID;
        this.Category = Category;
        this.warranty = warranty;
        this.quantity = quantity;
        this.Status = Status;
    }

    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Categories getCategory() {
        return Category;
    }

    public void setCategory(Categories Category) {
        this.Category = Category;
    }

    public WarrantyDetails getWarranty() {
        return warranty;
    }

    public void setWarranty(WarrantyDetails warranty) {
        this.warranty = warranty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
}
