package com.service.impls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;

import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.configs.Keywords;
import com.dto.OrderStatus;
import com.dto.PaymentDto;
import com.entity.Orders;
import com.entity.Payments;
import com.entity.User;
import com.exception.ResourceNotFound;
import com.razorpay.RazorpayClient;
import com.repository.OrdersRepo;
import com.repository.PaymentsRepository;
import com.repository.UserRepository;
import com.service.PaymentsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentsRepository paymentRepository;
	
	@Autowired
	private OrdersRepo ordersRepo;

	@Autowired
	private ModelMapper mapper;

	private static final String KEY = "k";
	private static final String SECRET_KEY = "sk";
	private static final String CURRENCY = "INR";

	@Override
	public String InitiateTransaction(Integer userId, Double amount) {
		// amount,currency,key,secret key
		try {
			User findUser = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFound(Keywords.userNotFount));
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", CURRENCY);
			// key="amount",value="amount"
			// key="currency",value="INR"

			RazorpayClient razorpayClient = new RazorpayClient(KEY, SECRET_KEY);
			Order order = razorpayClient.orders.create(jsonObject);
			System.out.println("order is:" + order);
			// TransactionResponse prepareReceipt = paymentResponse(order);
			// Saving order to db.
			Payments myOrders = new Payments();
			log.info("----------------------------------------");
			// log.info(order.get("amount"));
			System.out.println("saving order start ----------------");
			String orderAmount = order.get("amount") + " ";
			double myAmount = Double.parseDouble(orderAmount);
			String amountPaid = order.get("amount_paid") + " ";
			double myAmountPaid = Double.parseDouble(amountPaid);
			String orderDue = order.get("amount_due") + " ";
			double myAmountDue = Double.parseDouble(orderDue);

			// myOrders.setAmountDue(order.get("amount")+" ");
			myOrders.setAmount(myAmount);
			myOrders.setAmountPaid(myAmountPaid);
			myOrders.setAmountDue(myAmountDue);

			myOrders.setCreatedAt(LocalDateTime.now());
			myOrders.setUser(findUser);
			myOrders.setOrderId(order.get("id"));
			myOrders.setStatus(("created"));
			// myOrders.setReceipt(order.get("receipt"));

			this.paymentRepository.save(myOrders);
			System.out.println("saving order end -----------------");
			// TransactionResponse prepareReceipt = paymentResponse(order);
			return order.toString();

		} catch (Exception e) {
			throw new ResourceNotFound(Keywords.Order_Creation_failed);
		}
	}

	@Override
	public String updateOrder(String orderId, Map<String, String> data) {
		System.out.println("data of map -----------------");
		// String o = data.get("id").toString();
		// .out.println(o);
		// System.out.println(data.get("order_id").toString());
		Payments myOrder = this.paymentRepository.findByOrderId(orderId);

		System.out.println(myOrder);

		myOrder.setPaymentId(data.get("payment_id").toString());
		myOrder.setStatus(data.get("status").toString());
		myOrder.setCreatedAt(LocalDateTime.now());
		this.paymentRepository.save(myOrder);
		// return ResponseEntity.ok(Map.of("msg","updated"));
		return Keywords.paymentSuccessfull;
	}

	@Override
	public List<PaymentDto> findTransactionByUser(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User Not Found"));

		List<Payments> payments = paymentRepository.findAllByUser(user);

		List<PaymentDto> paymentDtoList = payments.stream().map(payment -> {
			PaymentDto paymentDto = new PaymentDto();
			// Map payment fields to paymentDto fields here
			paymentDto.setMyId(payment.getMyId());
			paymentDto.setAmount(payment.getAmount());
			paymentDto.setCreatedAt(payment.getCreatedAt());
			paymentDto.setOrderId(payment.getOrderId());
			paymentDto.setStatus(payment.getStatus());
			paymentDto.setAttempt(payment.getAttempts());
			paymentDto.setAmountDue(payment.getAmountDue());
			paymentDto.setAmountPaid(payment.getAmountPaid());
			paymentDto.setPaymentId(payment.getPaymentId());
			payment.setReceipt(payment.getReceipt());
			payment.setCurrency(payment.getCurrency());

			return paymentDto;
		}).collect(Collectors.toList());
		return paymentDtoList;
	}

	@Override
	public List<PaymentDto> getDataByMonths() {
		List<Payments> listPayment = paymentRepository.findAll();

		List<PaymentDto> postDtos = listPayment.stream().map((post) -> this.mapper.map(post, PaymentDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public Map<Object, Map<Object, List<PaymentDto>>> getMonthlyData() {
		// Logic to retrieve the original data
		List<PaymentDto> originalData = getDataByMonths();

		// Group the data by year and then by month
		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();

		Map<Object, Map<Object, List<PaymentDto>>> groupedData = originalData.stream()
				.filter(data -> data.getTransactionDate().getYear() == currentYear)
				.collect(Collectors.groupingBy(data -> data.getTransactionDate().getYear(),
						Collectors.groupingBy(data -> data.getTransactionDate().getMonthValue())));

		return groupedData;
	}

	@Override
	public Map<String, Double> getMonthwiseIncomeSummary() {
		// Get the current date
		LocalDate currentDate = LocalDate.now();
		 // Extract the current year from the current date
		int currentYear = currentDate.getYear();
		Map<String, Double> summary = new LinkedHashMap<>();

		for (int month = 1; month <= 12; month++) {
			Double totalIncome = paymentRepository.calculateTotalIncomeByMonthAndYear(month, currentYear);
			// Put the month name (derived from the month number) and its corresponding total income into the summary map
			summary.put((String.valueOf(month)), totalIncome);
		}

		return summary;
	}

	private String getMonthName(int month) {
		return YearMonth.of(LocalDate.now().getYear(), month).getMonth().name();
	}
	
	@Override
	public OrderStatus getOrderDataCount() {
	List<Orders> totalOrder = ordersRepo.findAll();
	List<Orders> pendingCount = ordersRepo.findByOrderStatus("pending");
	int totalOrderFinal = totalOrder.size();
	int pendingCountFinal = pendingCount.size();
	OrderStatus o = new OrderStatus();
	o.setPending(pendingCountFinal);
	o.setTotalReceivedOrder(totalOrderFinal);
	o.setDelivered(totalOrderFinal-pendingCountFinal);
	return o;
	}
}
