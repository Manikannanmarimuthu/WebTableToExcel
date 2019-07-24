package com.qa.webtable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadWebTable {

	public WebDriver driver;

	@Test
	public void readWriteWebTable() throws InterruptedException, IOException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.w3schools.com/html/html_tables.asp");
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		WebElement element = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("// *[@id=\"customers\"]")));
		List<WebElement> headerWebTable = driver.findElements(By.xpath("// *[@id=\"customers\"]/tbody/tr[1]/th"));
		List<WebElement> rowCount = driver.findElements(By.xpath("// *[@id=\"customers\"]/tbody/tr"));
		Xls_Reader reader = new Xls_Reader("./exceldata/WebTable.xlsx");
		reader.addSheet("W3SchoolsData5");
		for (WebElement header : headerWebTable) {
			String headerText = header.getText();
			reader.addColumn("W3SchoolsData5", headerText);
		}

		for (int i = 2; i <= rowCount.size(); i++) {
			reader.setCellData("W3SchoolsData5", "Company", i,
					driver.findElement(By.xpath(Constants.breCmnyXpath + i + Constants.AfterCmnyXpath)).getText());
			reader.setCellData("W3SchoolsData5", "Contact", i,
					driver.findElement(By.xpath(Constants.breContXpath + i + Constants.AfterContXpath)).getText());
			reader.setCellData("W3SchoolsData5", "Country", i,
					driver.findElement(By.xpath(Constants.breCounXpath + i + Constants.AfterCounXpath)).getText());
		}
	}
}