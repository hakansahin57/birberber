package com.birberber.repositories;

import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.appointment.AppointmentStatus;
import com.birberber.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStoreAndStartTimeLessThanAndEndTimeGreaterThanAndStatusIn(
            Store store,
            LocalDateTime end,
            LocalDateTime start,
            List<AppointmentStatus> statuses
    );

    List<Appointment> findByCustomerEmailOrderByStartTimeDesc(String email);
}
