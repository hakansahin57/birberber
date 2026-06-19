package com.birberber.services.appointment;

import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.appointment.AppointmentStatus;
import com.birberber.domain.payment.Payment;
import com.birberber.domain.payment.PaymentStatus;
import com.birberber.domain.product.Product;
import com.birberber.domain.store.Store;
import com.birberber.domain.user.User;
import com.birberber.repositories.AppointmentRepository;
import com.birberber.repositories.PaymentRepository;
import com.birberber.repositories.ProductRepository;
import com.birberber.repositories.UserRepository;
import com.birberber.services.store.AvailabilityService;
import com.birberber.services.store.NearbyStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NearbyStoreService nearbyStoreService;
    private final AvailabilityService availabilityService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PaymentRepository paymentRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            NearbyStoreService nearbyStoreService,
            AvailabilityService availabilityService) {
        this.appointmentRepository = appointmentRepository;
        this.paymentRepository = paymentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.nearbyStoreService = nearbyStoreService;
        this.availabilityService = availabilityService;
    }

    public List<Appointment> getAppointmentsForUser(String email) {
        return appointmentRepository.findByCustomerEmailOrderByStartTimeDesc(email);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error.appointment.not.found"));
    }

    @Transactional
    public Appointment createPendingAppointment(
            String customerEmail,
            Long storeId,
            Long serviceId,
            String startTime) {
        User customer = userRepository.findByEmail(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("error.user.not.found");
        }

        Store store = nearbyStoreService.findById(storeId);
        Product service = productRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("error.service.not.found"));

        LocalDateTime start = LocalDateTime.parse(startTime, DATE_TIME_FORMAT);
        LocalDateTime end = start.plusMinutes(service.getDurationMinutes());

        if (!availabilityService.isSlotAvailable(store, start, end)) {
            throw new IllegalStateException("error.slot.unavailable");
        }

        Appointment appointment = new Appointment();
        appointment.setName(service.getName() + " - " + store.getName());
        appointment.setCustomer(customer);
        appointment.setStore(store);
        appointment.setService(service);
        appointment.setStartTime(start);
        appointment.setEndTime(end);
        appointment.setStatus(AppointmentStatus.PENDING_PAYMENT);

        Payment payment = new Payment();
        payment.setName("Payment for " + service.getName());
        payment.setAmount(service.getPrice() != null ? service.getPrice().getValue() : 0);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAppointment(appointment);
        appointment.setPayment(payment);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment completePayment(Long appointmentId, String cardNumber) {
        Appointment appointment = findById(appointmentId);
        if (appointment.getStatus() != AppointmentStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("error.appointment.not.pending");
        }

        Payment payment = appointment.getPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId("TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        if (cardNumber != null && cardNumber.length() >= 4) {
            payment.setCardLastFour(cardNumber.substring(cardNumber.length() - 4));
        }
        paymentRepository.save(payment);

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }
}
