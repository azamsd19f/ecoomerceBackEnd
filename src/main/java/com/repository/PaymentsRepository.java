package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.Payments;
import com.entity.User;
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	
	public Payments findByOrderId(String orderId);

	List<Payments> findAllByUser(User user);
	
	@Query(value = "select SUM(amount_paid) from payments where month(payments.transaction_date) = ?1 and year(payments.transaction_date)= ?2 ", nativeQuery = true)
	Double calculateTotalIncomeByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

}
