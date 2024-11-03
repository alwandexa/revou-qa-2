package steps.web;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SortingSteps {

    @When("I select the {string} sorting option")
    public void i_select_the_sorting_option(String sortingOption) {
        WebElement sortDropdown = DriverManager.getDriver().findElement(By.className("product_sort_container"));
        sortDropdown.click();
        sortDropdown.findElement(By.xpath("//option[. = '" + sortingOption + "']")).click();
    }

    @Then("the products should be displayed in ascending order of price")
    public void the_products_should_be_displayed_in_ascending_order_of_price() {
        List<WebElement> prices = DriverManager.getDriver().findElements(By.className("inventory_item_price"));
        List<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        Assert.assertTrue(isSortedAscending(priceList), "Products are not sorted in ascending order of price.");
    }

    @Then("the products should be displayed in descending order of price")
    public void the_products_should_be_displayed_in_descending_order_of_price() {
        List<WebElement> prices = DriverManager.getDriver().findElements(By.className("inventory_item_price"));
        List<Double> priceList = new ArrayList<>();
        for (WebElement price : prices) {
            priceList.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        Assert.assertTrue(isSortedDescending(priceList), "Products are not sorted in descending order of price.");
    }

    @Then("the products should be displayed in alphabetical order")
    public void the_products_should_be_displayed_in_alphabetical_order() {
        List<WebElement> productNames = DriverManager.getDriver().findElements(By.className("inventory_item_name"));
        List<String> nameList = new ArrayList<>();
        for (WebElement name : productNames) {
            nameList.add(name.getText());
        }
        Assert.assertTrue(isSortedAscending(nameList), "Products are not sorted in alphabetical order.");
    }

    @Then("the products should be displayed in reverse alphabetical order")
    public void the_products_should_be_displayed_in_reverse_alphabetical_order() {
        List<WebElement> productNames = DriverManager.getDriver().findElements(By.className("inventory_item_name"));
        List<String> nameList = new ArrayList<>();
        for (WebElement name : productNames) {
            nameList.add(name.getText());
        }
        Assert.assertTrue(isSortedDescending(nameList), "Products are not sorted in reverse alphabetical order.");
    }

    private <T extends Comparable<T>> boolean isSortedAscending(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private <T extends Comparable<T>> boolean isSortedDescending(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) < 0) {
                return false;
            }
        }
        return true;
    }
}
