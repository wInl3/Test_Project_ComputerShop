package models;

public class BuildPCView {

    private int buildPCID;
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

    private String imgMain;
    private String imgCPU;
    private String imgGPU;
    private String imgRAM;
    private String imgSSD;
    private String imgCase;
  
    private int mainBoardID;
    private int cpuID;
    private int gpuID;
    private int ramID;
    private int ssdID;
    private int caseID;

    private int mainWarrantyID;
    private int cpuWarrantyID;
    private int gpuWarrantyID;
    private int ramWarrantyID;
    private int ssdWarrantyID;
    private int caseWarrantyID;
    
private int mainWarranty; // tháng
private int cpuWarranty;
private int gpuWarranty;
private int ramWarranty;
private int ssdWarranty;
private int caseWarranty;

    public BuildPCView() {
    }

    public BuildPCView(int buildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase, int price, int status, int userID, String fullName, String role) {
        this.buildPCID = buildPCID;
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

    public BuildPCView(int buildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase, int price, int status, String imgMain, String imgCPU, String imgGPU, String imgRAM, String imgSSD, String imgCase) {
        this.buildPCID = buildPCID;
        this.mainBoard = mainBoard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.ssd = ssd;
        this.pcCase = pcCase;
        this.price = price;
        this.status = status;
        this.imgMain = imgMain;
        this.imgCPU = imgCPU;
        this.imgGPU = imgGPU;
        this.imgRAM = imgRAM;
        this.imgSSD = imgSSD;
        this.imgCase = imgCase;
    }

    public BuildPCView(int buildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase, int price, int status) {
        this.buildPCID = buildPCID;
        this.mainBoard = mainBoard;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.ssd = ssd;
        this.pcCase = pcCase;
        this.price = price;
        this.status = status;
    }
public BuildPCView(int buildPCID, String mainBoard, String cpu, String gpu, String ram, String ssd, String pcCase,
                   int price, int status, int userID, String fullName, String role,
                   int mainBoardID, int cpuID, int gpuID, int ramID, int ssdID, int caseID,
                   String imgMain, String imgCPU, String imgGPU, String imgRAM, String imgSSD, String imgCase,
                   int mainWarrantyID, int cpuWarrantyID, int gpuWarrantyID, int ramWarrantyID, int ssdWarrantyID, int caseWarrantyID) {

    this.buildPCID = buildPCID;
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

    this.mainBoardID = mainBoardID;
    this.cpuID = cpuID;
    this.gpuID = gpuID;
    this.ramID = ramID;
    this.ssdID = ssdID;
    this.caseID = caseID;

    this.imgMain = imgMain;
    this.imgCPU = imgCPU;
    this.imgGPU = imgGPU;
    this.imgRAM = imgRAM;
    this.imgSSD = imgSSD;
    this.imgCase = imgCase;

    this.mainWarrantyID = mainWarrantyID;
    this.cpuWarrantyID = cpuWarrantyID;
    this.gpuWarrantyID = gpuWarrantyID;
    this.ramWarrantyID = ramWarrantyID;
    this.ssdWarrantyID = ssdWarrantyID;
    this.caseWarrantyID = caseWarrantyID;
}

    public int getMainWarranty() {
        return mainWarranty;
    }

    public void setMainWarranty(int mainWarranty) {
        this.mainWarranty = mainWarranty;
    }

    public int getCpuWarranty() {
        return cpuWarranty;
    }

    public void setCpuWarranty(int cpuWarranty) {
        this.cpuWarranty = cpuWarranty;
    }

    public int getGpuWarranty() {
        return gpuWarranty;
    }

    public void setGpuWarranty(int gpuWarranty) {
        this.gpuWarranty = gpuWarranty;
    }

    public int getRamWarranty() {
        return ramWarranty;
    }

    public void setRamWarranty(int ramWarranty) {
        this.ramWarranty = ramWarranty;
    }

    public int getSsdWarranty() {
        return ssdWarranty;
    }

    public void setSsdWarranty(int ssdWarranty) {
        this.ssdWarranty = ssdWarranty;
    }

    public int getCaseWarranty() {
        return caseWarranty;
    }

    public void setCaseWarranty(int caseWarranty) {
        this.caseWarranty = caseWarranty;
    }

    public int getMainWarrantyID() {
        return mainWarrantyID;
    }

    public void setMainWarrantyID(int mainWarrantyID) {
        this.mainWarrantyID = mainWarrantyID;
    }

    public int getCpuWarrantyID() {
        return cpuWarrantyID;
    }

    public void setCpuWarrantyID(int cpuWarrantyID) {
        this.cpuWarrantyID = cpuWarrantyID;
    }

    public int getGpuWarrantyID() {
        return gpuWarrantyID;
    }

    public void setGpuWarrantyID(int gpuWarrantyID) {
        this.gpuWarrantyID = gpuWarrantyID;
    }

    public int getRamWarrantyID() {
        return ramWarrantyID;
    }

    public void setRamWarrantyID(int ramWarrantyID) {
        this.ramWarrantyID = ramWarrantyID;
    }

    public int getSsdWarrantyID() {
        return ssdWarrantyID;
    }

    public void setSsdWarrantyID(int ssdWarrantyID) {
        this.ssdWarrantyID = ssdWarrantyID;
    }

    public int getCaseWarrantyID() {
        return caseWarrantyID;
    }

    public void setCaseWarrantyID(int caseWarrantyID) {
        this.caseWarrantyID = caseWarrantyID;
    }

    public int getMainBoardID() {
        return mainBoardID;
    }

    public void setMainBoardID(int mainBoardID) {
        this.mainBoardID = mainBoardID;
    }

    public int getCpuID() {
        return cpuID;
    }

    public void setCpuID(int cpuID) {
        this.cpuID = cpuID;
    }

    public int getGpuID() {
        return gpuID;
    }

    public void setGpuID(int gpuID) {
        this.gpuID = gpuID;
    }

    public int getRamID() {
        return ramID;
    }

    public void setRamID(int ramID) {
        this.ramID = ramID;
    }

    public int getSsdID() {
        return ssdID;
    }

    public void setSsdID(int ssdID) {
        this.ssdID = ssdID;
    }

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public int getBuildPCID() {
        return buildPCID;
    }

    public void setBuildPCID(int buildPCID) {
        this.buildPCID = buildPCID;
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
}
