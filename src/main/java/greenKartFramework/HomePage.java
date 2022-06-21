package greenKartFramework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class HomePage {
    protected WebDriver driver;
    WebDriverWait wait;
    List<WebElement> listOfItems;
    List<WebElement> listOfCartTopRightInfo;

    //Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By
            productItemsList = By.xpath("//h4[@class = 'product-name']"),
            partChild_button = By.xpath("//child::button"),
            partChild_input = By.xpath("//child::input"),
            partChild_p = By.xpath("//child::p"),
            partChild_h4 = By.xpath("//child::h4"),

            cartIcon = By.xpath("//a[@class = 'cart-icon']"),
            cartTopRightItems = By.xpath("//div[@class='cart-info']//tbody//tr"),
            modalContainer = By.xpath("//div[@class = 'cart-preview active']"),
            modalContentText= By.xpath(".//p[@class = 'product-name']"),
            modalContentQty = By.xpath("//p[@class = 'quantity']"),
            modalContentPrice = By.xpath("//p[@class = 'product-price']"),
            modalContentAmount = By.xpath("//p[@class = 'amount']"),
            proceedToCheckoutBtn = By.xpath("//button[text() = 'PROCEED TO CHECKOUT']");



//    private final By productItemsList = By.xpath("//h4[@class = 'product-name']");
//    private final By partChild_button = By.xpath("//child::button");
//    private final By partChild_input = By.xpath("//child::input");
//    private final By partChild_p = By.xpath("//child::p");
//    private final By partChild_h4 = By.xpath("//child::h4");


//    private final By cartIcon = By.xpath("//a[@class = 'cart-icon']");
//    private final By cartTopRightItems = By.xpath("//div[@class='cart-info']//tbody//tr");
//    private final By modalContainer = By.xpath("//div[@class = 'cart-preview active']");
//    private final By modalContentText= By.xpath(".//p[@class = 'product-name']");
//    private final By modalContentQty = By.xpath("//p[@class = 'quantity']");
//    private final By modalContentPrice = By.xpath("//p[@class = 'product-price']");
//    private final By modalContentAmount = By.xpath("//p[@class = 'amount']");
//    private final By proceedToCheckoutBtn = By.xpath("//button[text() = 'PROCEED TO CHECKOUT']");

    public int generateRandomNumber() {
        listOfItems = driver.findElements(productItemsList);
        return (int) Math.round(Math.random() * listOfItems.size());
    }
    //Add Item Into Cart
    public List<String> addItemIntoCart(int index) {
        String price = "";
        String qty = "";
        String text = "";
         for (int i = 0; i < listOfItems.size(); i++) {
            listOfItems = driver.findElements(productItemsList);
            if (index == i) {
                WebElement addBtn = listOfItems.get(i).findElement(By.xpath("" +  productItemsList +"[" + i + "]"+"//child::button"));
                WebElement getQty = listOfItems.get(i).findElement(By.xpath("" + productItemsList + "[" + i + "] "+ partChild_input +""));
                WebElement getPrice = listOfItems.get(i).findElement(By.xpath("" + productItemsList + "[" + i + "]"+partChild_p + ""));
                WebElement getText = listOfItems.get(i).findElement(By.xpath("" + productItemsList + "[" + i + "]"+partChild_h4 +""));
                price = getPrice.getText();
                qty = getQty.getAttribute("value");
                text = getText.getText();
                addBtn.click();
            }
        }
        return Arrays.asList(qty, price, text);
    }
    //Verify Items and Price are shown as expected in the cart info (top right)
    public List<String> getCartTopRightInfo(){
        String qty = "";
        String price = "";
        listOfCartTopRightInfo = driver.findElements(cartTopRightItems);
        for (int i = 0; i < listOfCartTopRightInfo.size(); i++) {
            switch(i) {
                case 0:
                   WebElement wQty = listOfCartTopRightInfo.get(i).findElement(By.xpath("//div[@class='cart-info']//tbody//tr[" + (i+1) + "]//strong"));
                   //WebElement wQty = listOfCartTopRightInfo.get(i).findElement(By.xpath(cartTopRightItems + "[" + (i+1) + "]//strong"));
                    qty = wQty.getText();
                    break;
                case 1:
                    WebElement wPrice = listOfCartTopRightInfo.get(i).findElement(By.xpath("//div[@class='cart-info']//tbody//tr[" + (i+1)+ "]//strong"));
                    price =  wPrice.getText();
                    break;
            }
        }
        return Arrays.asList(qty,price);
    }
    //Click Cart Icon and verify modal Content Elements
    public void getCartIcon(){
        WebElement getCartIcon = driver.findElement(cartIcon);
        getCartIcon.click();
    }
    public WebElement getModalContainer(){
        return driver.findElement(modalContainer);
    }
    //Get container images list
    public boolean getContainerImages() {
        boolean isBrokenImage = false;

        try {
            List<WebElement> image_list = getModalContainer().findElements(By.tagName("img"));
            for (WebElement img : image_list) {
                if (img != null) {
                    if (img.getAttribute("naturalWidth").equals("0")) {
                        isBrokenImage = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return isBrokenImage;
    }
    public WebElement getModalContentText(){
        return getModalContainer().findElement(modalContentText);
    }
    public WebElement getModalContentQty(){
        return getModalContainer().findElement(modalContentQty);
    }
    public WebElement getModalContentPrice(){
        return getModalContainer().findElement(modalContentPrice);
    }
    public WebElement getModalContentAmount(){
        return getModalContainer().findElement(modalContentAmount);
    }
    //Click on "Proceed to checkout" button
    public void  clickOnProceedToCheckoutBtn() {
        WebElement getProceedToCheckoutBtn = getModalContainer().findElement(proceedToCheckoutBtn);
        getProceedToCheckoutBtn.click();
    }
    public String getOrderPageUrl() {
        clickOnProceedToCheckoutBtn();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
        wait.until(ExpectedConditions.urlContains("cart"));
        return driver.getCurrentUrl();
    }
}


