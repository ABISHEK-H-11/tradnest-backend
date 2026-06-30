package com.kodnest.tradeNest.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.serviceContract.AdminBusnessServiceContract;

@RestController
@RequestMapping("/api/admin/business")
@CrossOrigin(origins = "https://tradnest-frontend.vercel.app", allowCredentials = "true")
public class AdminBusinessController {

	private AdminBusnessServiceContract adminBusnessServiceContract;
	
	public AdminBusinessController(AdminBusnessServiceContract adminBusnessServiceContract) {
		super();
		this.adminBusnessServiceContract = adminBusnessServiceContract;
	}
	
	
	@GetMapping("/month")
	public ResponseEntity<?> getMonthlyBusiness(@RequestParam int month, @RequestParam int year){
		try {
			Map<String, Object> responce = adminBusnessServiceContract.getMonthlyBusiness(month, year);
			System.out.println(responce);
			return ResponseEntity.ok(responce);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("somthing went wrong");
		}
	}
	
	@GetMapping("/daily")
	public ResponseEntity<?> getDailyBusiness(@RequestParam String date) {
		try {
			Map<String, Object> responce = adminBusnessServiceContract.getDailyBusiness(date);
			return  ResponseEntity.ok(responce);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("somthing went wrong");
		}
	}
	
	@GetMapping("/year")
	public ResponseEntity<?> getYearlyBusiness(@RequestParam int year) {
		try {
			Map<String, Object> responce  = adminBusnessServiceContract.getYearlyBusiness(year);
			return ResponseEntity.ok(responce);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("somthing went wrong");
		}
	}
	@GetMapping("/overall")
	public ResponseEntity<?> getOverAllBusiness() {
		try {
			Map<String, Object> responce =  adminBusnessServiceContract.getOverAllBusiness();
			return ResponseEntity.ok(responce);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("somthing went wrong");
		}
	}
	
}
