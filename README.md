# mvn-sotatek-lanpt
- This is maven selenium project
- Build automation testing framework
- This is Pre-interview at SotaTek
- Using design pattern: Page Object Model, Singleton pattern, factory pattern
- Using TestNG framework
- Support run on multiple browsers: chrome, firefox, coc coc, IE 
  > To run on multiple browsers, please uncomment in mvn-sotatek-lanpt/src/test/resources/runAmazonEbayTestcase.xml
- Logging by log4j, reportng, Extent report
- Business:
  > Open eBay page, change currency from VND to $, get all item of current page and add to list
  
  > Verify result search: if all item of current page include one of key in keyword search => return true 
  > Then: Open Amazon page, get all item of current page and add to other list
  > verify result search: if all item of current page include one of key in keyword search => return true 
  
  > Add 2 list which has price not null => sort list and print to log file and console
  > Print all item that has null price
  
  > Handle name of search item to find other element. Name has special character or not.
  > Handle price of search item to compare and sort list
  
- ToDo: 
  > Improve perfomance of project
  
  > Handle more special case 
  
- Run:
Run by testNG plugin in IDE (eclipse/intellij)

  
