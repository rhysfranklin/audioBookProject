package com.fdmgroup.AudioBookStoreProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.Entities.Account;
import com.fdmgroup.Entities.AudioBook;
import com.fdmgroup.Repositories.CatalogueRepo;

@Controller
public class CatalogueController {

	@Autowired
	CatalogueRepo catRepo;
	
	private AudioBook searchedBook;
	public List<AudioBook> basketBooks = new ArrayList<AudioBook>();

	// getters and setters for the arrayList
	public List<AudioBook> getBasketBooks() {
		return basketBooks;
	}

	public void setBasketBooks(List<AudioBook> basketBooks) {
		this.basketBooks = basketBooks;
	}


	// method for finding a book from the database
	public AudioBook bookSearch(String title) {

		// find the book in the database based on their title
		boolean searchBook = catRepo.existsById(title);
		// if book has been found return the details
		if (searchBook) {
			Optional<AudioBook> retrievedBook = catRepo.findById(title);
			
			AudioBook foundBook = retrievedBook.get();
			return foundBook;
		} else {
			return null;
		}
	}

	// method for calculating total price from the books in the basket
	public double totalPrice(List<AudioBook> bookList) {
		
		
		Optional<Double> od = bookList.stream().map( b -> b.getPrice()).reduce( (a, b) -> a+b);
		System.out.println("kevs results is:" + od.get());
		
		
		Iterator<AudioBook> listIterator = bookList.iterator();
		double basketTotal = 0.00;
		while (listIterator.hasNext()) {
			AudioBook audioBook = listIterator.next();
			double bookPrice = audioBook.getPrice();
			basketTotal += bookPrice;
			System.out.println(basketTotal);
		}
		return basketTotal;
	}

	@GetMapping("/search")
	public String searchCataloguePage(HttpSession session, Model model) {

		 //initialised database
//		AudioBook predGold = new AudioBook("Predators Gold", "Phillip pullman", "Stephen Fry", 5.00);
//		AudioBook mortalEng = new AudioBook("Mortal Engines", "Phillip pullman", "Stephen Fry", 6.00);
//		catRepo.save(predGold);
//		catRepo.save(mortalEng);
//		AudioBook darkPlain = new AudioBook("A Darkling Plain", "Phillip pullman", "Barnaby Edwards", 7.00);
//		catRepo.save(darkPlain);
//		AudioBook infDevices = new AudioBook("Infernal Devices", "Phillip Reeve", "Barnaby Edwards", 4.00);
//		catRepo.save(infDevices);
//		AudioBook darkTower = new AudioBook("The Dark Tower", "Stephen King", "George Guidall", 6.00);
//		catRepo.save(darkTower);
//		AudioBook martian = new AudioBook("The Martian", "Andy Weir", "R.C.Bray", 5.00);
//		catRepo.save(martian);

		// check whether user is logged in
		if (session.getAttribute("loggedinUser") == null || session.getAttribute("loggedinUser").equals("")) {

			// prompt user with message to log in
			String logReminder = "you must be logged in to view the catalogue";
			model.addAttribute("loginReminder", logReminder);
			return "login";
		} else {
			return "searchCatalogue";
		}
	}

	@GetMapping("/bookSearch")
	public String findBook(Model model, @RequestParam("title") String title) {

		AudioBook bookFound = bookSearch(title);
		if (bookFound != null) {

			// retrieve book details from the object
			String foundTitle = bookFound.getTitle();
			String foundAuthor = bookFound.getAuthor();
			String foundNarrator = bookFound.getNarrator();
			double foundPrice = bookFound.getPrice();

			// display the details to the screen
			model.addAttribute("foundTitle", foundTitle);
			model.addAttribute("foundAuthor", foundAuthor);
			model.addAttribute("foundNarrator", foundNarrator);
			model.addAttribute("foundPrice", foundPrice);

			// prepare book to add to basket
			searchedBook = bookFound;

			return "foundBook";
		} else {
			return "notFoundBook";
		}

	}

	@GetMapping("/basket")
	public String basketPage(HttpSession session, Model model) {

		// checks whether user is logged in
		if (session.getAttribute("loggedinUser") == null || session.getAttribute("loggedinUser").equals("")) {

			// prompts user to login
			String reminder = "you must be logged in to view your basket";
			model.addAttribute("loginReminder", reminder);
			return "login";
		} else {
			if (searchedBook != null) {
				basketBooks.add(searchedBook);
				session.setAttribute("booksInBasket", basketBooks);
				searchedBook = null;
			}
			return "basket";
		}
	}

	@GetMapping("/checkout")
	public String checkoutPage(HttpSession session, Model model) {
		
		//check if any books are in the basket, if not return basket page with message
		if (basketBooks.isEmpty()) {
			String basketReminder = "you have no items currently in your basket";
			model.addAttribute("emptyBasket", basketReminder);
			return "basket";
		}else{
			double basketPrice = totalPrice(basketBooks);
			String printBasket = "£" + basketPrice;
			model.addAttribute("total", printBasket);
			return "checkout";
		}

	}

	@GetMapping("/ConfirmPurchase")
	public String confirmation(Model model, HttpSession session, @RequestParam("conEmail") String conEmail,
			@RequestParam("conPassword") String conPassword, @RequestParam("conSecurityCode") String conSecurityCode) {

		Account sessAccount = (Account) session.getAttribute("loggedinUser");

		// retrieve session details
		String sessEmail = sessAccount.getEmail();
		String sessPassword = sessAccount.getPassword();
		String sessSecurityCode = sessAccount.getSecurityCode();

		// compare the form with the session details and check that the basket
		// contains books
		if (sessEmail.equals(conEmail) && sessPassword.equals(conPassword) && sessSecurityCode.equals(conSecurityCode)
				&& basketBooks.size() > 0) {
			return "receipt";

		} else {
			// failed confirmation so print error message and return to basket
			String conFail = "Empty basket or incorrect confirmation details";
			model.addAttribute("conIncorrect", conFail);
			return "basket";
		}

	}

	@GetMapping("/remove")
	public String removeBasket(HttpSession session, Model model) {
		//empties the basket after purchase
		session.removeAttribute("booksInBasket");
		basketBooks.removeAll(basketBooks);
		return "Home";
	}

	@GetMapping("newbasket")
	public String newBasket(HttpSession session, Model model) {
		//creates a new basket for the logged in user
		session.removeAttribute("booksInBasket");
		basketBooks.removeAll(basketBooks);
		return "searchCatalogue";
	}

	@RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
	public String handleDeleteBook(@RequestParam(name = "title") String title, HttpSession session) {
		//searches database for book with that title
		AudioBook book = bookSearch(title);
		basketBooks.remove(book); //remove from basket
		
		return "redirect:basket";
	}

}
