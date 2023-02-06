package com.eminyilmazz.loanbrokerapp.repository;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByTcknAndBirthDate(@NonNull Long tckn, @NonNull LocalDate birthDate);
}
