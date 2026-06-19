package com.birberber.services.store;

import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.appointment.AppointmentStatus;
import com.birberber.domain.product.Product;
import com.birberber.domain.store.Store;
import com.birberber.domain.store.WorkingHour;
import com.birberber.dto.ServiceDto;
import com.birberber.dto.TimeSlotDto;
import com.birberber.repositories.AppointmentRepository;
import com.birberber.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final List<AppointmentStatus> BLOCKING_STATUSES = Arrays.asList(
            AppointmentStatus.PENDING_PAYMENT,
            AppointmentStatus.CONFIRMED
    );

    private final ProductRepository productRepository;
    private final AppointmentRepository appointmentRepository;

    public AvailabilityService(ProductRepository productRepository, AppointmentRepository appointmentRepository) {
        this.productRepository = productRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<ServiceDto> getServices(Store store) {
        return productRepository.findByStore(store).stream()
                .map(this::toServiceDto)
                .collect(Collectors.toList());
    }

    public List<TimeSlotDto> getSlots(Store store, LocalDate date, int durationMinutes) {
        WorkingHour workingHour = findWorkingHour(store, date.getDayOfWeek());
        if (workingHour == null) {
            return List.of();
        }

        LocalTime open = LocalTime.parse(workingHour.getOpeningHour());
        LocalTime close = LocalTime.parse(workingHour.getClosingHour());
        List<Appointment> existing = appointmentRepository
                .findByStoreAndStartTimeLessThanAndEndTimeGreaterThanAndStatusIn(
                        store,
                        date.atTime(close),
                        date.atTime(open),
                        BLOCKING_STATUSES
                );

        List<TimeSlotDto> slots = new ArrayList<>();
        LocalTime slotStart = open;
        while (!slotStart.plusMinutes(durationMinutes).isAfter(close)) {
            LocalDateTime startDateTime = date.atTime(slotStart);
            LocalDateTime endDateTime = startDateTime.plusMinutes(durationMinutes);
            boolean available = isAvailable(startDateTime, endDateTime, existing);
            slots.add(new TimeSlotDto(
                    startDateTime.format(TIME_FORMAT),
                    endDateTime.format(TIME_FORMAT),
                    available
            ));
            slotStart = slotStart.plusMinutes(durationMinutes);
        }
        return slots;
    }

    public boolean isSlotAvailable(Store store, LocalDateTime start, LocalDateTime end) {
        List<Appointment> existing = appointmentRepository
                .findByStoreAndStartTimeLessThanAndEndTimeGreaterThanAndStatusIn(
                        store,
                        end,
                        start,
                        BLOCKING_STATUSES
                );
        return isAvailable(start, end, existing);
    }

    private boolean isAvailable(LocalDateTime start, LocalDateTime end, List<Appointment> existing) {
        if (start.isBefore(LocalDateTime.now())) {
            return false;
        }
        return existing.stream().noneMatch(appointment -> overlaps(start, end, appointment));
    }

    private boolean overlaps(LocalDateTime start, LocalDateTime end, Appointment appointment) {
        return start.isBefore(appointment.getEndTime()) && end.isAfter(appointment.getStartTime());
    }

    private WorkingHour findWorkingHour(Store store, DayOfWeek dayOfWeek) {
        if (store.getWorkingHours() == null) {
            return null;
        }
        return store.getWorkingHours().stream()
                .filter(hour -> dayOfWeek.name().equalsIgnoreCase(hour.getDay()))
                .findFirst()
                .orElse(null);
    }

    private ServiceDto toServiceDto(Product product) {
        ServiceDto dto = new ServiceDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDurationMinutes(product.getDurationMinutes());
        if (product.getPrice() != null) {
            dto.setPrice(product.getPrice().getValue());
            if (product.getPrice().getCurrency() != null) {
                dto.setCurrency(product.getPrice().getCurrency().getIsocode());
            }
        }
        return dto;
    }
}
