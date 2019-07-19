package com.fdmgroup.AudioBookStoreProject;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.Entities.Account;
import com.fdmgroup.Repositories.AccountRepo;

@Controller
@SessionAttributes({ "loggedinUser", "booksInBasket" })
public class AccountController {

	@Autowired
	AccountRepo accountRepo;

	@GetMapping("/homepage")
	public String homePage(Model model) {
		return "Home";
	}

	@GetMapping("/createAccount")
	public String newAccount() {
		return "createAccount";
	}

	@GetMapping("/loginpage")
	public String retrieveLogin() {
		return "login";
	}

	@GetMapping("/Success")
	public String accountSuccess() {
		return "createAccountSucess";
	}

	@GetMapping("/Failure")
	public String accountFailure() {
		return "createAccountFailure";
	}

	@GetMapping("/FindAccount")
	public String findAccount(HttpSession session, Model model, @RequestParam("email") String email,
			@RequestParam("password") String password) {

		// check whether the email is in the database
		Boolean findUser = accountRepo.existsById(email);

		// if account with that email does exist search database for account
		if (findUser == true) {

			// find account based on email
			Optional<Account> account = accountRepo.findById(email);
			Account foundAccount = account.get();

			// retrieve password from account
			String foundPassword = foundAccount.getPassword();

			// if the two object strings are the same then return account
			// details and search catalogue
			if (foundPassword.equals(password)) {
				String welcome = "welcome " + email;
				session.setAttribute("loggedinUser", foundAccount);
				model.addAttribute("welcomeUser", welcome);
				return "redirect:newbasket";
			}
		}

		return "accountNotFound";

	}

	@PostMapping("/CreateAccount")
	public String createAccount(Model model, @ModelAttribute Account acc, @RequestParam("confirmPassword") String confirmPassword) {

		// find details on the account given
		String email = acc.getEmail();
		String password = acc.getPassword();
		
		// check whether the email is in the database
		Boolean exist = accountRepo.existsById(email);

		// if both passwords match create account
		if (exist == false && password.equals(confirmPassword)) {
			accountRepo.save(acc);
			return "createAccountSuccess";
		}

		else {
			return "createAccountFailure";
		}

	}

	@GetMapping("/accountDetails")
	public String accountDetails(Model model, HttpSession session) {

		if (session.getAttribute("loggedinUser") == null || session.getAttribute("loggedinUser").equals("")) {

			// prompt user with message to log in
			String logReminder = "you must be logged in to view account details";
			model.addAttribute("loginReminder", logReminder);
			return "login";
		} else {
			return "accountDetails";
		}
	}

	@GetMapping("/updateCard")
	public String updateCard() {
		return "updateCard";
	}

	@PostMapping("updateAccountCard")
	public String updateAccountCard(HttpSession session, Model model, @RequestParam("updateName") String upName,
			@RequestParam("updateNumber") int upNumber, @RequestParam("updateSecurityCode") String upSecurityCode,
			@RequestParam("updateExpiryDate") String upExpiryDate) {

		Account sessAccount = (Account) session.getAttribute("loggedinUser");

		// retrieve session details
		sessAccount.setCardName(upName);
		sessAccount.setCardNumber(upNumber);
		sessAccount.setExpiryDate(upExpiryDate);
		sessAccount.setSecurityCode(upSecurityCode);

		accountRepo.save(sessAccount);
		session.setAttribute("loggedinUser", sessAccount);

		return "accountDetails";
	}

	@GetMapping("/updateAddress")
	public String updateAddress() {
		return "updateAddress";
	}

	@PostMapping("updateAccountAddress")
	public String updateAccountCard(HttpSession session, Model model, @RequestParam("updateAddress") String upAddress,
			@RequestParam("updatePostCode") String upPostcode) {

		Account sessionAccount = (Account) session.getAttribute("loggedinUser");

		// retrieve session details
		sessionAccount.setFirstLineAddress(upAddress);
		sessionAccount.setPostcode(upPostcode);

		accountRepo.save(sessionAccount);
		session.setAttribute("loggedinUser", sessionAccount);

		return "accountDetails";
	}

}
