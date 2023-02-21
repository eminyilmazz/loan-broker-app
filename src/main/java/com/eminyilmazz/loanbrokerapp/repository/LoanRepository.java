package com.eminyilmazz.loanbrokerapp.repository;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("select l from Loan l where l.id = :id and l.customer.tckn = :tckn and l.customer.birthDate = :birthDate")
    @NonNull
    Loan findByIdAndCustomer_TcknAndCustomer_BirthDate(@Param("id") @NonNull Long id, @Param("tckn") @NonNull Long tckn,
                                                       @Param("birthDate") @NonNull LocalDate birthDate);

    @Query("select (count(l) > 0) from Loan l where l.id = :id and l.customer.tckn = :tckn and l.customer.birthDate = :birthDate")
    boolean existsByIdAndCustomer_TcknAndCustomer_BirthDate(@Param("id") @NonNull Long id, @Param("tckn") @NonNull Long tckn,
                                                            @Param("birthDate") @NonNull LocalDate birthDate);

    @Query("select l from Loan l where l.customer = :customer and l.approvalStatus = :approved")
    List<Loan> findByCustomerAndApprovalStatus(@Param("customer") @NonNull Customer customer,
                                               @Param(value = "approved") @NonNull boolean approvalStatus);

    @Query("select l from Loan l where l.customer = :customer")
    List<Loan> findByCustomer(@Param("customer") @NonNull Customer customer);
}
