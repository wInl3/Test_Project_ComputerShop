/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author PC
 */
public class CartBuildPC {

    private int cartBuildPCID;
    private String mainBoard;
    private String cpu;
    private String gpu;
    private String ram;
    private String ssd;
    private String pcCase;
    private int price;
    private int status;
    private int userID;
    private String fullName;
    private String role;

    // use for Customer - image URLs
    private String imgMain;
    private String imgCPU;
    private String imgGPU;
    private String imgRAM;
    private String imgSSD;
    private String imgCase;

    public CartBuildPC() {
    }

    public CartBuildPC(int cartBuildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase, int price, int status, int userID, String fullName, String role) {
        this.cartBuildPCID = cartBuildPCID;
        this.mainBoard = mainBoard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.ssd = ssd;
        this.pcCase = pcCase;
        this.price = price;
        this.status = status;
        this.userID = userID;
        this.fullName = fullName;
        this.role = role;
    }

    public CartBuildPC(int cartBuildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase, int price, int status, int userID, String fullName, String role, String imgMain, String imgCPU, String imgGPU, String imgRAM, String imgSSD, String imgCase) {
        this.cartBuildPCID = cartBuildPCID;
        this.mainBoard = mainBoard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.ssd = ssd;
        this.pcCase = pcCase;
        this.price = price;
        this.status = status;
        this.userID = userID;
        this.fullName = fullName;
        this.role = role;
        this.imgMain = imgMain;
        this.imgCPU = imgCPU;
        this.imgGPU = imgGPU;
        this.imgRAM = imgRAM;
        this.imgSSD = imgSSD;
        this.imgCase = imgCase;
    }

    public int getCartBuildPCID() {
        return cartBuildPCID;
    }

    public void setCartBuildPCID(int cartBuildPCID) {
        this.cartBuildPCID = cartBuildPCID;
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getPcCase() {
        return pcCase;
    }

    public void setPcCase(String pcCase) {
        this.pcCase = pcCase;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImgMain() {
        return imgMain;
    }

    public void setImgMain(String imgMain) {
        this.imgMain = imgMain;
    }

    public String getImgCPU() {
        return imgCPU;
    }

    public void setImgCPU(String imgCPU) {
        this.imgCPU = imgCPU;
    }

    public String getImgGPU() {
        return imgGPU;
    }

    public void setImgGPU(String imgGPU) {
        this.imgGPU = imgGPU;
    }

    public String getImgRAM() {
        return imgRAM;
    }

    public void setImgRAM(String imgRAM) {
        this.imgRAM = imgRAM;
    }

    public String getImgSSD() {
        return imgSSD;
    }

    public void setImgSSD(String imgSSD) {
        this.imgSSD = imgSSD;
    }

    public String getImgCase() {
        return imgCase;
    }

    public void setImgCase(String imgCase) {
        this.imgCase = imgCase;
    }
    
}

   