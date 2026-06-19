package com.birberber.dto;

import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.appointment.AppointmentStatus;

import java.time.format.DateTimeFormatter;

public class AppointmentSummaryDto {

    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    private Long id;
    private String storeName;
    private String serviceName;
    private String startLabel;
    private String endLabel;
    private String status;
    private String statusLabel;
    private double amount;
    private String transactionId;

    public static AppointmentSummaryDto from(Appointment appointment) {
        AppointmentSummaryDto dto = new AppointmentSummaryDto();
        dto.id = appointment.getId();
        dto.storeName = appointment.getStore().getName();
        dto.serviceName = appointment.getService().getName();
        dto.startLabel = appointment.getStartTime().format(DISPLAY);
        dto.endLabel = appointment.getEndTime().format(TIME);
        dto.status = appointment.getStatus().name();
        dto.statusLabel = toStatusLabel(appointment.getStatus());
        if (appointment.getPayment() != null) {
            dto.amount = appointment.getPayment().getAmount();
            dto.transactionId = appointment.getPayment().getTransactionId();
        }
        return dto;
    }

    private static String toStatusLabel(AppointmentStatus status) {
        return switch (status) {
            case CONFIRMED -> "Onaylandı";
            case PENDING_PAYMENT -> "Ödeme Bekliyor";
            case CANCELLED -> "İptal";
        };
    }

    public Long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public String getEndLabel() {
        return endLabel;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
