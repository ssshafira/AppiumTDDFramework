# AppiumTDDFramework
Android mobile app automation using Appium and Java

Packages :
1. com.qa :
   
   BaseTest : contains basic operations such as :
   - field decorator
   - calling screen recorder in @BeforeMethod and @AfterMethod
   - loading config.properties, setting capabilities, initialize Android driver in @BeforeTest
   - quitting driver in @AfterTest
   - starting Appium server programmatically in @BeforeSuite
   - stopping Appium server programmatically in @AfterSuite
   - wait, click, clear, send keys, get text, close app, launch app
2. com.qa.listeners : do screenshot on test failure
3. com.qa.pages : contains elements and operations on that specific page
4. com.qa.tests : loading json data and implementing test cases
5. com.qa.utils : defining wait time, parseStringXML method, get date and time, logging

Apk Name : Android-MyDemoAppRN.1.3.0.build-244

How to run the test inside IDE :
1. Right click on testng.xml
2. Run As > TestNG Suite

How to run the test using Maven :
1. cmd > mvn test -DsuiteXmlFile=testng
