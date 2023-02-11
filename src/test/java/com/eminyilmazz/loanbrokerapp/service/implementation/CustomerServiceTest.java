package com.eminyilmazz.loanbrokerapp.service.implementation;

import com.eminyilmazz.loanbrokerapp.exception.CustomerNotFoundException;
import com.eminyilmazz.loanbrokerapp.exception.DuplicateTcknException;
import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.CustomerDto;
import com.eminyilmazz.loanbrokerapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.eminyilmazz.loanbrokerapp.model.mapper.CustomerMapper.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        // Given
        List<Customer> expectedCustomers = Arrays.asList(new Customer(), new Customer(), new Customer());

        // When
        when(customerRepository.findAll()).thenReturn(expectedCustomers);
        List<Customer> result = customerService.getAll();

        // Then
        verify(customerRepository, times(1)).findAll();
        assertEquals(expectedCustomers, result);
    }

    @Test
    void testGetByTckn_whenCustomerFound_shouldReturnCustomer() {
        //given
        Long tckn = 12345678910L;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setTckn(tckn);
        //when
        when(customerRepository.findById(tckn)).thenReturn(Optional.of(expectedCustomer));
        Customer actualCustomer = customerService.getByTckn(tckn);
        //then
        assertEquals(expectedCustomer, actualCustomer);
        verify(customerRepository, times(1)).findById(tckn);
    }

    @Test
    void testGetByTckn_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        //given
        Long tckn = 12345678910L;
        //when
        Mockito.when(customerRepository.findById(tckn)).thenReturn(Optional.empty());
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getByTckn(tckn);
        });
        assertEquals("Customer tckn: " + tckn + " not found!", exception.getMessage());
        verify(customerRepository, times(1)).findById(tckn);
    }

    @Test
    void getByTcknAndBirthDate_WhenCustomerFound_ShouldReturnCustomer() {
        //given
        LocalDate birthDate = LocalDate.of(1999, 1, 1);
        Customer customer = new Customer(12345678910L, birthDate, "Foo", "Bar", "1234567890", "dummy.test@loanbroker.com", 3000.0);
        //when
        when(customerRepository.findByTcknAndBirthDate(12345678910L, birthDate))
                .thenReturn(Optional.of(customer));
        //then
        Customer result = customerService.getByTcknAndBirthDate(12345678910L, birthDate);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).findByTcknAndBirthDate(anyLong(), any());
    }

    @Test
    void getByTcknAndBirthDate_WhenCustomerNotFound_ShouldThrowException() {
        //given
        LocalDate birthDate = LocalDate.of(1999, 1, 1);
        //when
        when(customerRepository.findByTcknAndBirthDate(anyLong(), any(LocalDate.class)))
                .thenReturn(Optional.empty());
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getByTcknAndBirthDate(12345678910L, birthDate);
        });
        String expectedMessage = "Customer tckn: " + 12345678910L + " and birth date" + birthDate + " not found!";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(exception.getClass(), CustomerNotFoundException.class);
        verify(customerRepository, times(1)).findByTcknAndBirthDate(anyLong(), any());
    }

    @Test
    void addCustomer_thenCustomerShouldBeSaved() {
        //given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setTckn(12345678910L);
        customerDto.setBirthDate("1999-01-01");
        customerDto.setFirstName("Foo");
        customerDto.setLastName("Bar");
        customerDto.setPhoneNumber("1234567890");
        customerDto.setEmailAddress("foo@bar.com");
        customerDto.setMonthlySalary(2000.0);
        Customer customer = toEntity(customerDto);
        //when
        when(customerRepository.existsById(customerDto.getTckn())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //then
        Customer result = customerService.addCustomer(customerDto);
        verify(customerRepository, times(1)).existsById(customerDto.getTckn());
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals(customer, result);
    }

    @Test
    void addCustomer_withDuplicateTckn_thenDuplicateTcknExceptionShouldBeThrown() {
        //given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setTckn(12345678910L);
        //when
        when(customerRepository.existsById(customerDto.getTckn())).thenReturn(true);
        //then
        Exception exception = assertThrows(DuplicateTcknException.class,
                () -> customerService.addCustomer(customerDto));
        verify(customerRepository, times(1)).existsById(customerDto.getTckn());
        verify(customerRepository, times(0)).save(any());
        assertNotNull(exception);
        assertEquals(DuplicateTcknException.class, exception.getClass());
        assertEquals("Provided TCKN already exists.\nCannot accept duplicate TCKN.\n", exception.getMessage());
    }

    @Test
    void updateCustomer_WhenCustomerExists_ReturnSavedCustomer() throws CustomerNotFoundException {
        //given
        CustomerDto customerDto = new CustomerDto(12345678910L, "1999-01-01", "Foo", "Bar", "1234567890", "foo@bar.com", 5000.0);
        Customer expectedCustomer = new Customer(12345678910L, LocalDate.of(1999, 1, 1), "Foo", "Bar", "1234567890", "foo@bar.com", 5000.0);
        //when
        when(customerRepository.existsById(customerDto.getTckn())).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);
        //then
        Customer updatedCustomer = customerService.updateCustomer(customerDto);
        assertNotNull(updatedCustomer);
        assertEquals(expectedCustomer, updatedCustomer);
        verify(customerRepository, times(1)).existsById(customerDto.getTckn());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerDoesNotExist_ThrowCustomerNotFoundException() {
        //given
        CustomerDto customerDto = new CustomerDto(12345678910L, "1999-01-01", "Foo", "Bar", "1234567890", "foo@bar.com", 5000.0);
        //when
        when(customerRepository.existsById(customerDto.getTckn())).thenReturn(false);
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(customerDto));
        assertEquals(CustomerNotFoundException.class, exception.getClass());
        assertEquals("Customer tckn: " + customerDto.getTckn() + " not found!", exception.getMessage());
        verify(customerRepository, times(1)).existsById(customerDto.getTckn());
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_WhenExists_ShouldReturnTrue() {
        //given
        Long tckn = 12345678910L;
        //when
        doNothing().when(customerRepository).deleteById(tckn);
        when(customerRepository.existsById(tckn)).thenReturn(true);
        //then
        boolean result = customerService.deleteCustomer(tckn);
        verify(customerRepository, times(1)).existsById(tckn);
        verify(customerRepository, times(1)).deleteById(tckn);
        assertTrue(result);
    }

    @Test
    void deleteCustomer_WhenNotExists_ShouldThrowCustomerNotFoundException() {
        //given
        Long tckn = 12345678910L;
        //when
        when(customerRepository.existsById(tckn)).thenReturn(false);
        //then
        Exception exception = assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(tckn));
        assertEquals(CustomerNotFoundException.class, exception.getClass());
        assertEquals("Delete operation is not successful. The customer does not exist.", exception.getMessage());
        verify(customerRepository, times(1)).existsById(tckn);
        verify(customerRepository, times(0)).deleteById(tckn);
    }
}